package br.com.raac_system.repository;

import br.com.raac_system.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {
    Page<Doctor> findAllByTenantIdAndDeletedAtIsNull(UUID tenantId, Pageable pageable);

    Optional<Doctor> findByIdAndTenantIdAndDeletedAtIsNull(UUID id, UUID tenantId);
}
