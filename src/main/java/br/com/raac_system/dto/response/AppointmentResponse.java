package br.com.raac_system.dto.response;

import br.com.raac_system.entity.Appointment;
import br.com.raac_system.entity.enums.AppointmentOrigin;
import br.com.raac_system.entity.enums.AppointmentStatus;
import br.com.raac_system.entity.enums.AppointmentType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class AppointmentResponse {
    private UUID id;
    private UUID patientId;
    private String patientName;
    private UUID doctorId;
    private String doctorName;
    private UUID insuranceId;
    private AppointmentType type;
    private LocalDateTime startDt;
    private LocalDateTime endDt;
    private AppointmentStatus status;
    private String room;
    private AppointmentOrigin origin;
    private String notes;

    public static AppointmentResponse fromEntity(Appointment appointment) {
        AppointmentResponseBuilder builder = builder()
                .id(appointment.getId())
                .patientId(appointment.getPatient().getId())
                .patientName(appointment.getPatient().getName())
                .doctorId(appointment.getDoctor().getId())
                .doctorName(appointment.getDoctor().getName())
                .type(appointment.getType())
                .startDt(appointment.getStartDt())
                .endDt(appointment.getEndDt())
                .status(appointment.getStatus())
                .room(appointment.getRoom())
                .origin(appointment.getOrigin())
                .notes(appointment.getNotes());

        if (appointment.getInsurance() != null) {
            builder.insuranceId(appointment.getInsurance().getId());
        }

        return builder.build();
    }
}
