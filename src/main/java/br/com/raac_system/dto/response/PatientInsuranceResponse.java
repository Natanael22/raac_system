package br.com.raac_system.dto.response;

import br.com.raac_system.entity.enums.EntityStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
public class PatientInsuranceResponse {
    private UUID id;
    private UUID patientId;
    private String operatorName;
    private String cardNumber;
    private String plan;
    private LocalDate validity;
    private String accommodation;
    private EntityStatus status;
}
