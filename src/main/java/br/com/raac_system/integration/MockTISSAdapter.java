package br.com.raac_system.integration;

import br.com.raac_system.entity.enums.AuthorizationStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MockTISSAdapter implements TISSPort {

    @Value("${tiss.mock.scenario:ALWAYS_APPROVE}")
    private String scenario;

    @Value("${tiss.mock.pending-seconds:10}")
    private int pendingSeconds;

    @Override
    public AuthorizationResult requestAuthorization(TISSRequest request) {
        if ("ALWAYS_DENY".equalsIgnoreCase(scenario)) {
            return AuthorizationResult.builder()
                    .status(AuthorizationStatus.NEGADO)
                    .rejectionReason("1001 - Beneficiario inativo")
                    .build();
        } else if ("PENDING".equalsIgnoreCase(scenario)) {
            return AuthorizationResult.builder()
                    .status(AuthorizationStatus.PENDENTE)
                    .protocol(UUID.randomUUID().toString())
                    .build();
        } else if ("TIMEOUT".equalsIgnoreCase(scenario)) {
            throw new RuntimeException("SocketTimeoutException - Timeout communicating with Mock TISS Operator");
        }

        // Default to ALWAYS_APPROVE
        return AuthorizationResult.builder()
                .status(AuthorizationStatus.APROVADO)
                .authNumber("AUTH-" + UUID.randomUUID().toString().substring(0, 8))
                .protocol(UUID.randomUUID().toString())
                .build();
    }

    @Override
    public EligibilityResult checkEligibility(String cardNumber, UUID insuranceConfigId) {
        if ("EXPIRED_CARD".equalsIgnoreCase(scenario)) {
            return EligibilityResult.builder().eligible(false).message("Card expired").build();
        }
        return EligibilityResult.builder().eligible(true).message("Eligible").build();
    }

    @Override
    public AuthorizationResult getAuthorizationStatus(String protocol, UUID insuranceConfigId) {
        return AuthorizationResult.builder()
                .status(AuthorizationStatus.APROVADO)
                .authNumber("AUTH-" + UUID.randomUUID().toString().substring(0, 8))
                .protocol(protocol)
                .build();
    }
}
