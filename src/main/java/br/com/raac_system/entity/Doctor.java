package br.com.raac_system.entity;

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
}
