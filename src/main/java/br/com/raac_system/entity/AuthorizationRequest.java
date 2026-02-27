package br.com.raac_system.entity;

import br.com.raac_system.entity.enums.AuthorizationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "authorization_requests")
public class AuthorizationRequest extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_config_id", nullable = false)
    private InsuranceConfig insuranceConfig;

    @Column(nullable = false, length = 50)
    private String procedureCode;

    @Column(length = 10)
    private String cid;

    @Column(columnDefinition = "TEXT")
    private String xmlSent;

    @Column(columnDefinition = "TEXT")
    private String xmlResponse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private AuthorizationStatus status;

    @Column(length = 100)
    private String authNumber;

    @Column(length = 100)
    private String protocol;

    @Column(columnDefinition = "TEXT")
    private String rejectionReason;

    private LocalDateTime sentAt;
    private LocalDateTime respondedAt;
}
