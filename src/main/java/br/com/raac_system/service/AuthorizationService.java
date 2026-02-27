package br.com.raac_system.service;

import br.com.raac_system.dto.request.AuthorizationRequestDto;
import br.com.raac_system.entity.AuthorizationRequest;
import br.com.raac_system.entity.Doctor;
import br.com.raac_system.entity.InsuranceConfig;
import br.com.raac_system.entity.Patient;
import br.com.raac_system.entity.Tenant;
import br.com.raac_system.entity.enums.AuthorizationStatus;
import br.com.raac_system.repository.AuthorizationRequestRepository;
import br.com.raac_system.security.TenantContext;
import br.com.raac_system.task.AuthorizationTask;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AuthorizationService {

    private final AuthorizationRequestRepository authRepo;
    private final AuthorizationTask authorizationTask;

    public AuthorizationService(AuthorizationRequestRepository authRepo, AuthorizationTask authorizationTask) {
        this.authRepo = authRepo;
        this.authorizationTask = authorizationTask;
    }

    @Transactional
    public AuthorizationRequest createAuthorization(AuthorizationRequestDto dto) {
        AuthorizationRequest req = new AuthorizationRequest();

        Tenant tenant = new Tenant();
        tenant.setId(UUID.fromString(TenantContext.getTenantId()));
        req.setTenant(tenant);

        Patient patient = new Patient();
        patient.setId(dto.getPatientId());
        req.setPatient(patient);

        Doctor doctor = new Doctor();
        doctor.setId(dto.getDoctorId());
        req.setDoctor(doctor);

        InsuranceConfig config = new InsuranceConfig();
        config.setId(dto.getInsuranceConfigId());
        req.setInsuranceConfig(config);

        req.setProcedureCode(dto.getProcedureCode());
        req.setCid(dto.getCid());
        req.setStatus(AuthorizationStatus.PENDENTE);

        req = authRepo.save(req);

        // Dispara task assíncrona
        authorizationTask.sendAuthorization(req.getId());

        return req;
    }

    public AuthorizationStatus getStatus(UUID id) {
        return authRepo.findById(id).map(AuthorizationRequest::getStatus)
                .orElseThrow(() -> new RuntimeException("Authorization not found"));
    }
}
