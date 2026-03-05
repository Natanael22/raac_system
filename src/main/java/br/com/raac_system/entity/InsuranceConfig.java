package br.com.raac_system.entity;

import java.time.LocalDateTime;

import br.com.raac_system.entity.enums.EntityStatus;
import br.com.raac_system.entity.enums.InsuranceEnvironment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "insurance_configs")
public class InsuranceConfig extends BaseEntity {

    @Column(nullable = false)
    private String operatorName;

    @Column(nullable = false, length = 50)
    private String ansCode;

    private String wsUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InsuranceEnvironment environment;

    @Column(columnDefinition = "TEXT")
    private String credentialsEncrypted;

    @Column(length = 20)
    private String tissVersion;

    private String certPath;

    @Column(columnDefinition = "TEXT")
    private String certPasswordEncrypted;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EntityStatus status = EntityStatus.ACTIVE;

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
