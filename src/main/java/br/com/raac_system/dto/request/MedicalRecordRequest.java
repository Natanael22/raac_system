package br.com.raac_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalRecordRequest {

    private String diagnosisJson;

    @NotBlank
    private String doctorId;

    @NotBlank
    private String patientId;

    private String chiefComplaint;
    private String physicalExam;
    private String plan;
    private String notes;
}
