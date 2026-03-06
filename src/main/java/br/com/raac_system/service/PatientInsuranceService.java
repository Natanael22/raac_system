package br.com.raac_system.service;

import br.com.raac_system.dto.request.PatientInsuranceRequest;
import br.com.raac_system.dto.response.PatientInsuranceResponse;
import br.com.raac_system.entity.Patient;
import br.com.raac_system.entity.PatientInsurance;
import br.com.raac_system.exception.BusinessException;
import br.com.raac_system.mapper.PatientInsuranceMapper;
import br.com.raac_system.repository.PatientInsuranceRepository;
import br.com.raac_system.repository.PatientRepository;
import br.com.raac_system.security.TenantContext;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PatientInsuranceService {

    private final PatientInsuranceRepository patientInsuranceRepository;
    private final PatientRepository patientRepository;
    private final PatientInsuranceMapper mapper;

    public PatientInsuranceService(PatientInsuranceRepository patientInsuranceRepository,
            PatientRepository patientRepository,
            PatientInsuranceMapper mapper) {
        this.patientInsuranceRepository = patientInsuranceRepository;
        this.patientRepository = patientRepository;
        this.mapper = mapper;
    }

    public Page<PatientInsuranceResponse> listInsurances(UUID patientId, Pageable pageable) {
        return patientInsuranceRepository
                .findAllByPatientIdAndTenantIdAndDeletedAtIsNull(patientId, getTenantId(), pageable)
                .map(mapper::toDto);
    }

    @Transactional
    public PatientInsuranceResponse createInsurance(UUID patientId, PatientInsuranceRequest request) {
        Patient patient = patientRepository.findByIdAndTenantIdAndDeletedAtIsNull(patientId, getTenantId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        PatientInsurance insurance = mapper.toEntity(request);
        insurance.setPatient(patient);
        insurance.setTenant(patient.getTenant());
        if (insurance.getStatus() == null) {
            insurance.setStatus(br.com.raac_system.entity.enums.EntityStatus.ACTIVE);
        }

        insurance = patientInsuranceRepository.save(insurance);
        return mapper.toDto(insurance);
    }

    @Transactional
    public PatientInsuranceResponse updateInsurance(UUID patientId, UUID id, PatientInsuranceRequest request) {
        PatientInsurance insurance = patientInsuranceRepository
                .findByIdAndPatientIdAndTenantIdAndDeletedAtIsNull(id, patientId, getTenantId())
                .orElseThrow(() -> new EntityNotFoundException("Patient Insurance not found"));

        mapper.updateEntityFromDto(request, insurance);

        if (request.getStatus() != null) {
            insurance.setStatus(request.getStatus());
        }

        insurance = patientInsuranceRepository.save(insurance);
        return mapper.toDto(insurance);
    }

    private UUID getTenantId() {
        String tenantIdStr = TenantContext.getTenantId();
        if (tenantIdStr == null || tenantIdStr.isBlank()) {
            throw new BusinessException("Tenant ID is missing in context");
        }
        return UUID.fromString(tenantIdStr);
    }
}
