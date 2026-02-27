package br.com.raac_system.dto.response;

import br.com.raac_system.entity.Appointment;
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
    private String type;
    private LocalDateTime startDt;
    private LocalDateTime endDt;
    private String status;
    private String room;

    public static AppointmentResponse fromEntity(Appointment appointment) {
        return builder()
                .id(appointment.getId())
                .patientId(appointment.getPatient().getId())
                .patientName(appointment.getPatient().getName())
                .doctorId(appointment.getDoctor().getId())
                .doctorName(appointment.getDoctor().getName())
                .type(appointment.getType().name())
                .startDt(appointment.getStartDt())
                .endDt(appointment.getEndDt())
                .status(appointment.getStatus().name())
                .room(appointment.getRoom())
                .build();
    }
}
