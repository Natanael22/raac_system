package br.com.raac_system.repository;

import br.com.raac_system.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    Page<Appointment> findAllByTenantIdAndDeletedAtIsNull(UUID tenantId, Pageable pageable);
}
