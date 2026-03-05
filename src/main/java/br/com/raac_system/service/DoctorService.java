package br.com.raac_system.service;

import br.com.raac_system.dto.request.DoctorRequest;
import br.com.raac_system.dto.response.DoctorResponse;
import br.com.raac_system.entity.Doctor;
import br.com.raac_system.entity.Tenant;
import br.com.raac_system.exception.BusinessException;
import br.com.raac_system.mapper.DoctorMapper;
import br.com.raac_system.repository.DoctorRepository;
import br.com.raac_system.security.TenantContext;
import br.com.raac_system.util.CryptoUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @Value("${raac.encryption.key}")
    private String encryptionKey;

    public DoctorService(DoctorRepository doctorRepository, DoctorMapper doctorMapper) {
        this.doctorRepository = doctorRepository;
        this.doctorMapper = doctorMapper;
    }

    public Page<DoctorResponse> listDoctors(Pageable pageable) {
        return doctorRepository.findAllByTenantIdAndDeletedAtIsNull(getTenantId(), pageable)
                .map(doctorMapper::toDto);
    }

    @Transactional
    public DoctorResponse createDoctor(DoctorRequest request) {
        Doctor doctor = doctorMapper.toEntity(request);

        Tenant tenantRef = new Tenant();
        tenantRef.setId(getTenantId());
        doctor.setTenant(tenantRef);

        if (request.getBaAccount() != null && request.getBaAgency() != null) {
            String combinedBankData = String.format("%s|%s|%s", request.getBaBankCode(), request.getBaAgency(),
                    request.getBaAccount());
            doctor.setBankAccountEncrypted(CryptoUtil.encrypt(combinedBankData, encryptionKey));
        }

        doctor = doctorRepository.save(doctor);
        return doctorMapper.toDto(doctor);
    }

    @Transactional
    public DoctorResponse updateDoctor(UUID id, DoctorRequest request) {
        Doctor doctor = doctorRepository.findByIdAndTenantIdAndDeletedAtIsNull(id, getTenantId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        doctorMapper.updateEntityFromDto(request, doctor);

        if (request.getBaAccount() != null && request.getBaAgency() != null) {
            String combinedBankData = String.format("%s|%s|%s", request.getBaBankCode(), request.getBaAgency(),
                    request.getBaAccount());
            doctor.setBankAccountEncrypted(CryptoUtil.encrypt(combinedBankData, encryptionKey));
        }

        /*
         * if (request.getStatus() != null) {
         * doctor.setStatus(request.getStatus());
         * }
         */

        doctor = doctorRepository.save(doctor);
        return doctorMapper.toDto(doctor);
    }

    public DoctorResponse getDoctor(UUID id) {
        Doctor doctor = doctorRepository.findByIdAndTenantIdAndDeletedAtIsNull(id, getTenantId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        return doctorMapper.toDto(doctor);
    }

    @Transactional
    public void deleteDoctor(UUID id) {
        Doctor doctor = doctorRepository.findByIdAndTenantIdAndDeletedAtIsNull(id, getTenantId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
        doctor.setDeletedAt(LocalDateTime.now());
        doctorRepository.save(doctor);
    }

    private UUID getTenantId() {
        String tenantIdStr = TenantContext.getTenantId();
        if (tenantIdStr == null || tenantIdStr.isBlank()) {
            throw new BusinessException("Tenant ID is missing in context");
        }
        return UUID.fromString(tenantIdStr);
    }
}
