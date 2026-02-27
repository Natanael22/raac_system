package br.com.raac_system.repository;

import br.com.raac_system.entity.BillingBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BillingBatchRepository extends JpaRepository<BillingBatch, UUID> {
}
