package br.com.raac_system.entity;

import java.time.LocalDateTime;

import br.com.raac_system.entity.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "doctors")
public class Doctor extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 50)
    private String crm;

    @Column(nullable = false, length = 2)
    private String ufCrm;

    @Column(length = 50)
    private String rqe;

    @Column(length = 100)
    private String specialty;

    private String signatureUrl;

    @Column(columnDefinition = "TEXT")
    private String bankAccountEncrypted;

    @Column(columnDefinition = "TEXT")
    private String bankCode;

    @Column(columnDefinition = "TEXT")
    private String bankAgency;

    @Column(columnDefinition = "TEXT")
    private String bankAccount;

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
