package br.com.raac_system.entity;

import br.com.raac_system.entity.enums.Sex;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
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
}
