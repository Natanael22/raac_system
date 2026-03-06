package br.com.raac_system.service;

import br.com.raac_system.dto.request.PatientRequest;
import br.com.raac_system.dto.response.PatientResponse;
import br.com.raac_system.entity.Address;
import br.com.raac_system.entity.Patient;
import br.com.raac_system.entity.Tenant;
import br.com.raac_system.exception.BusinessException;
import br.com.raac_system.repository.PatientRepository;
import br.com.raac_system.security.TenantContext;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public Page<PatientResponse> listPatients(Pageable pageable) {
        UUID tenantId = getTenantId();
        return patientRepository.findAllByTenantIdAndDeletedAtIsNull(tenantId, pageable)
                .map(PatientResponse::fromEntity);
    }

    @Transactional
    public PatientResponse createPatient(PatientRequest request) {
        Patient patient = new Patient();
        // Since MVP, setting tenant explicitly via reference
        Tenant tenantRef = new Tenant();
        tenantRef.setId(getTenantId());
        patient.setTenant(tenantRef);

        patient.setName(request.getName());
        patient.setCpf(request.getCpf());
        patient.setCns(request.getCns());
        patient.setBirthDate(request.getBirthDate());
        patient.setSex(request.getSex());
        patient.setPhone(request.getPhone());
        patient.setEmail(request.getEmail());
        patient.setProntuarioNumber(request.getProntuarioNumber());
        patient.setStatus(request.getStatus() != null
                ? request.getStatus()
                : br.com.raac_system.entity.enums.EntityStatus.ACTIVE);

        Address address = new Address();
        address.setAddressStreet(request.getAddressStreet());
        address.setAddressNumber(request.getAddressNumber());
        address.setAddressCity(request.getAddressCity());
        address.setAddressState(request.getAddressState());
        patient.setAddress(address);

        patient = patientRepository.save(patient);
        return PatientResponse.fromEntity(patient);
    }

    @Transactional
    public PatientResponse updatePatient(UUID id, PatientRequest request) {
        Patient patient = patientRepository.findByIdAndTenantIdAndDeletedAtIsNull(id, getTenantId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        patient.setName(request.getName());
        patient.setCpf(request.getCpf());
        patient.setCns(request.getCns());
        patient.setBirthDate(request.getBirthDate());
        patient.setSex(request.getSex());
        patient.setPhone(request.getPhone());
        patient.setEmail(request.getEmail());
        patient.setProntuarioNumber(request.getProntuarioNumber());

        if (patient.getAddress() == null) {
            patient.setAddress(new Address());
        }
        patient.getAddress().setAddressStreet(request.getAddressStreet());
        patient.getAddress().setAddressNumber(request.getAddressNumber());
        patient.getAddress().setAddressCity(request.getAddressCity());
        patient.getAddress().setAddressState(request.getAddressState());

        if (request.getStatus() != null) {
            patient.setStatus(request.getStatus());
        }

        patient = patientRepository.save(patient);
        return PatientResponse.fromEntity(patient);
    }

    public PatientResponse getPatient(UUID id) {
        Patient patient = patientRepository.findByIdAndTenantIdAndDeletedAtIsNull(id, getTenantId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
        return PatientResponse.fromEntity(patient);
    }

    @Transactional
    public void deletePatient(UUID id) {
        Patient patient = patientRepository.findByIdAndTenantIdAndDeletedAtIsNull(id, getTenantId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));
        patient.setDeletedAt(LocalDateTime.now());
        patientRepository.save(patient); // soft delete
    }

    private UUID getTenantId() {
        String tenantIdStr = TenantContext.getTenantId();
        if (tenantIdStr == null) {
            throw new BusinessException("Tenant ID is missing in context");
        }
        return UUID.fromString(tenantIdStr);
    }
}
