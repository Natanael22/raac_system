package br.com.raac_system.entity;

import br.com.raac_system.entity.enums.AppointmentOrigin;
import br.com.raac_system.entity.enums.AppointmentStatus;
import br.com.raac_system.entity.enums.AppointmentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "appointments")
public class Appointment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_id")
    private PatientInsurance insurance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private AppointmentType type;

    @Column(nullable = false)
    private LocalDateTime startDt;

    @Column(nullable = false)
    private LocalDateTime endDt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private AppointmentStatus status;

    @Column(length = 50)
    private String room;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private AppointmentOrigin origin;

    @Column(columnDefinition = "TEXT")
    private String notes;
}
