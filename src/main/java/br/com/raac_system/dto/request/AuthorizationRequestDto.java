package br.com.raac_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AuthorizationRequestDto {
    @NotNull
    private UUID patientId;
    @NotNull
    private UUID doctorId;
    @NotNull
    private UUID insuranceConfigId;
    @NotBlank
    private String procedureCode;
    private String cid;
}
