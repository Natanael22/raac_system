package br.com.raac_system.dto.request;

import br.com.raac_system.entity.enums.AppointmentOrigin;
import br.com.raac_system.entity.enums.AppointmentType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AppointmentRequest {

    @NotNull
    private UUID patientId;

    @NotNull
    private UUID doctorId;

    private UUID insuranceId;

    @NotNull
    private AppointmentType type;

    @NotNull
    @FutureOrPresent
    private LocalDateTime startDt;

    @NotNull
    @FutureOrPresent
    private LocalDateTime endDt;

    private String room;
    private AppointmentOrigin origin;
    private String notes;
}
