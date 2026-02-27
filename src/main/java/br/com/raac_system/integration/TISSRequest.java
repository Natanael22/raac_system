package br.com.raac_system.integration;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@Builder
public class TISSRequest {
    private UUID patientId;
    private UUID doctorId;
    private UUID insuranceConfigId;
    private String procedureCode;
    private String cid;
}
