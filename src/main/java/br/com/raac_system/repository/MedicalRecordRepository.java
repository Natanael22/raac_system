package br.com.raac_system.repository;

import br.com.raac_system.entity.MedicalRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, UUID> {
    Page<MedicalRecord> findAllByTenantIdAndDeletedAtIsNull(UUID tenantId, Pageable pageable);

    Optional<MedicalRecord> findByIdAndTenantIdAndDeletedAtIsNull(UUID id, UUID tenantId);

    Page<MedicalRecord> findAllByPatientIdAndTenantIdAndDeletedAtIsNull(UUID patientId, UUID tenantId,
            Pageable pageable);
}
