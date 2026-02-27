package br.com.raac_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "patient_insurances")
public class PatientInsurance extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(nullable = false)
    private String operatorName;

    @Column(nullable = false, length = 100)
    private String cardNumber;

    @Column(length = 100)
    private String plan;

    private LocalDate validity;

    @Column(length = 50)
    private String accommodation;

    @Column(nullable = false, length = 50)
    private String status;
}
