package br.com.raac_system.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class MedicalRecordResponse {
    private UUID id;
    private String diagnosis;
    private String notes;
    private UUID doctorId;
    private String doctorName;
    private UUID patientId;
    private String patientName;
}
