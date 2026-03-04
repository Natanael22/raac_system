package br.com.raac_system.entity;

import br.com.raac_system.entity.enums.Sex;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "patients", uniqueConstraints = {
        @UniqueConstraint(name = "unique_prontuario_per_tenant", columnNames = { "tenant_id", "prontuario_number" })
})
public class Patient extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(length = 14)
    private String cpf;

    @Column(length = 50)
    private String cns;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Sex sex;

    @Column(length = 50)
    private String gender;

    @Column(length = 50)
    private String maritalStatus;

    @Column(length = 20)
    private String phone;

    private String email;

    @Embedded
    private Address address;

    @Column(columnDefinition = "TEXT")
    private String allergies;

    @Column(length = 5)
    private String bloodType;

    @Column(name = "prontuario_number", length = 50)
    private String prontuarioNumber;

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
