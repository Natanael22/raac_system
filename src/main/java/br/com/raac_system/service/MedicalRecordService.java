package br.com.raac_system.service;

import br.com.raac_system.dto.request.MedicalRecordRequest;
import br.com.raac_system.dto.response.MedicalRecordResponse;
import br.com.raac_system.entity.Doctor;
import br.com.raac_system.entity.MedicalRecord;
import br.com.raac_system.entity.Patient;
import br.com.raac_system.entity.Tenant;
import br.com.raac_system.exception.BusinessException;
import br.com.raac_system.mapper.MedicalRecordMapper;
import br.com.raac_system.repository.MedicalRecordRepository;
import br.com.raac_system.security.TenantContext;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordMapper medicalRecordMapper;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository,
            MedicalRecordMapper medicalRecordMapper) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.medicalRecordMapper = medicalRecordMapper;
    }

    public Page<MedicalRecordResponse> listRecords(Pageable pageable) {
        return medicalRecordRepository.findAllByTenantIdAndDeletedAtIsNull(getTenantId(), pageable)
                .map(medicalRecordMapper::toDto);
    }

    public Page<MedicalRecordResponse> listRecordsByPatient(UUID patientId, Pageable pageable) {
        return medicalRecordRepository
                .findAllByPatientIdAndTenantIdAndDeletedAtIsNull(patientId, getTenantId(), pageable)
                .map(medicalRecordMapper::toDto);
    }

    @Transactional
    public MedicalRecordResponse createRecord(MedicalRecordRequest request) {
        MedicalRecord record = medicalRecordMapper.toEntity(request);

        Tenant tenantRef = new Tenant();
        tenantRef.setId(getTenantId());
        record.setTenant(tenantRef);

        Doctor doctorRef = new Doctor();
        doctorRef.setId(UUID.fromString(request.getDoctorId()));
        record.setDoctor(doctorRef);

        Patient patientRef = new Patient();
        patientRef.setId(UUID.fromString(request.getPatientId()));
        record.setPatient(patientRef);

        // Since medical records might not have 'notes' explicitly in model based on MVP
        // mapping,
        // we map the JSON diagnosis if needed.

        record = medicalRecordRepository.save(record);
        return medicalRecordMapper.toDto(record);
    }

    public MedicalRecordResponse getRecord(UUID id) {
        MedicalRecord record = medicalRecordRepository.findByIdAndTenantIdAndDeletedAtIsNull(id, getTenantId())
                .orElseThrow(() -> new EntityNotFoundException("Medical Record not found"));
        return medicalRecordMapper.toDto(record);
    }

    @Transactional
    public void deleteRecord(UUID id) {
        MedicalRecord record = medicalRecordRepository.findByIdAndTenantIdAndDeletedAtIsNull(id, getTenantId())
                .orElseThrow(() -> new EntityNotFoundException("Medical Record not found"));
        record.setDeletedAt(LocalDateTime.now());
        medicalRecordRepository.save(record);
    }

    private UUID getTenantId() {
        String tenantIdStr = TenantContext.getTenantId();
        if (tenantIdStr == null || tenantIdStr.isBlank()) {
            throw new BusinessException("Tenant ID is missing in context");
        }
        return UUID.fromString(tenantIdStr);
    }
}
