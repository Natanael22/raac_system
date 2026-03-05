package br.com.raac_system.dto.request;

import br.com.raac_system.entity.enums.EntityStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientInsuranceRequest {

    @NotBlank(message = "Operator Name is mandatory")
    private String operatorName;

    @NotBlank(message = "Card Number is mandatory")
    private String cardNumber;

    private String plan;
    private LocalDate validity;
    private String accommodation;

    private EntityStatus status;
}
