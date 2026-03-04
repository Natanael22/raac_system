package br.com.raac_system.entity;

import br.com.raac_system.entity.enums.BatchStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "billing_batches")
public class BillingBatch extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_config_id", nullable = false)
    private InsuranceConfig insuranceConfig;

    @Column(nullable = false)
    private Integer competenceMonth;

    @Column(nullable = false)
    private Integer competenceYear;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "billing_batch_guides", joinColumns = @JoinColumn(name = "batch_id"), inverseJoinColumns = @JoinColumn(name = "guide_id"))
    private List<BillingGuide> guides = new ArrayList<>();

    @Column(precision = 12, scale = 2)
    private BigDecimal totalValue;

    @Column(columnDefinition = "TEXT")
    private String xmlBatch;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private BatchStatus status;

    private LocalDateTime submittedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
