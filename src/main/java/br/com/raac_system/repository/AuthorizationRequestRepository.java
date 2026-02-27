package br.com.raac_system.repository;

import br.com.raac_system.entity.AuthorizationRequest;
import br.com.raac_system.entity.enums.AuthorizationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AuthorizationRequestRepository extends JpaRepository<AuthorizationRequest, UUID> {
    List<AuthorizationRequest> findByStatus(AuthorizationStatus status);
}
