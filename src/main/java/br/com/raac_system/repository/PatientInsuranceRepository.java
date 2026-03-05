package br.com.raac_system.repository;

import br.com.raac_system.entity.PatientInsurance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientInsuranceRepository extends JpaRepository<PatientInsurance, UUID> {
    Page<PatientInsurance> findAllByPatientIdAndTenantIdAndDeletedAtIsNull(UUID patientId, UUID tenantId,
            Pageable pageable);

    Optional<PatientInsurance> findByIdAndPatientIdAndTenantIdAndDeletedAtIsNull(UUID id, UUID patientId,
            UUID tenantId);
}
