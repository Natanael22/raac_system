package br.com.raac_system.repository;

import br.com.raac_system.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Page<Patient> findAllByTenantIdAndDeletedAtIsNull(UUID tenantId, Pageable pageable);

    Optional<Patient> findByIdAndTenantIdAndDeletedAtIsNull(UUID id, UUID tenantId);
}
