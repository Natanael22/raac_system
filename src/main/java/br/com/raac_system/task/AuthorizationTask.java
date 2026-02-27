package br.com.raac_system.task;

import br.com.raac_system.dto.response.AuthorizationStatusEvent;
import br.com.raac_system.entity.AuthorizationRequest;
import br.com.raac_system.entity.enums.AuthorizationStatus;
import br.com.raac_system.integration.AuthorizationResult;
import br.com.raac_system.integration.TISSAdapterFactory;
import br.com.raac_system.integration.TISSPort;
import br.com.raac_system.integration.TISSRequest;
import br.com.raac_system.repository.AuthorizationRequestRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class AuthorizationTask {

    private final AuthorizationRequestRepository authRepository;
    private final TISSAdapterFactory tissAdapterFactory;
    private final SimpMessagingTemplate messagingTemplate;

    public AuthorizationTask(AuthorizationRequestRepository authRepository,
            TISSAdapterFactory tissAdapterFactory,
            SimpMessagingTemplate messagingTemplate) {
        this.authRepository = authRepository;
        this.tissAdapterFactory = tissAdapterFactory;
        this.messagingTemplate = messagingTemplate;
    }

    @Async
    public void sendAuthorization(UUID authRequestId) {
        authRepository.findById(authRequestId).ifPresent(authReq -> {
            try {
                TISSPort adapter = tissAdapterFactory.getAdapter(authReq.getInsuranceConfig());
                TISSRequest request = TISSRequest.builder()
                        .patientId(authReq.getPatient().getId())
                        .doctorId(authReq.getDoctor().getId())
                        .insuranceConfigId(authReq.getInsuranceConfig().getId())
                        .procedureCode(authReq.getProcedureCode())
                        .cid(authReq.getCid())
                        .build();

                AuthorizationResult result = adapter.requestAuthorization(request);

                authReq.setStatus(result.getStatus());
                authReq.setAuthNumber(result.getAuthNumber());
                authReq.setProtocol(result.getProtocol());
                authReq.setRejectionReason(result.getRejectionReason());
                authReq.setRespondedAt(LocalDateTime.now());
                authRepository.save(authReq);

                messagingTemplate.convertAndSend(
                        "/topic/authorization/" + authReq.getId(),
                        new AuthorizationStatusEvent(authReq.getStatus(), authReq.getAuthNumber()));
            } catch (Exception e) {
                // Log and keep Pending or fail
                authReq.setStatus(AuthorizationStatus.PENDENTE);
                authReq.setRejectionReason(e.getMessage());
                authRepository.save(authReq);
            }
        });
    }

    @Scheduled(fixedDelay = 300000) // 5 minutes
    public void pollPendingAuthorizations() {
        List<AuthorizationRequest> pendingRequests = authRepository.findByStatus(AuthorizationStatus.PENDENTE);
        for (AuthorizationRequest authReq : pendingRequests) {
            try {
                TISSPort adapter = tissAdapterFactory.getAdapter(authReq.getInsuranceConfig());
                AuthorizationResult result = adapter.getAuthorizationStatus(authReq.getProtocol(),
                        authReq.getInsuranceConfig().getId());

                if (result.getStatus() != AuthorizationStatus.PENDENTE) {
                    authReq.setStatus(result.getStatus());
                    authReq.setAuthNumber(result.getAuthNumber());
                    authReq.setRejectionReason(result.getRejectionReason());
                    authReq.setRespondedAt(LocalDateTime.now());
                    authRepository.save(authReq);

                    messagingTemplate.convertAndSend(
                            "/topic/authorization/" + authReq.getId(),
                            new AuthorizationStatusEvent(authReq.getStatus(), authReq.getAuthNumber()));
                }
            } catch (Exception e) {
                // Log and continue polling later
            }
        }
    }
}
