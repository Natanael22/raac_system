package br.com.raac_system.dto.response;

import br.com.raac_system.entity.Patient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
public class PatientResponse {
    private UUID id;
    private String name;
    private String cpf;
    private String prontuarioNumber;
    private LocalDate birthDate;
    private String phone;

    public static PatientResponse fromEntity(Patient patient) {
        return builder()
                .id(patient.getId())
                .name(patient.getName())
                .cpf(patient.getCpf())
                .prontuarioNumber(patient.getProntuarioNumber())
                .birthDate(patient.getBirthDate())
                .phone(patient.getPhone())
                .build();
    }
}
