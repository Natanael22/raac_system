package br.com.raac_system.dto.response;

import br.com.raac_system.entity.Patient;
import br.com.raac_system.entity.enums.Sex;
import br.com.raac_system.entity.enums.EntityStatus;
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
    private String cns;
    private String prontuarioNumber;
    private LocalDate birthDate;
    private Sex sex;
    private String maritalStatus;
    private String bloodType;
    private String phone;
    private String email;
    private String addressStreet;
    private String addressNumber;
    private String addressCity;
    private String addressState;
    private EntityStatus status;

    public static PatientResponse fromEntity(Patient patient) {
        PatientResponseBuilder builder = builder()
                .id(patient.getId())
                .name(patient.getName())
                .cpf(patient.getCpf())
                .cns(patient.getCns())
                .prontuarioNumber(patient.getProntuarioNumber())
                .birthDate(patient.getBirthDate())
                .sex(patient.getSex())
                .maritalStatus(patient.getMaritalStatus())
                .bloodType(patient.getBloodType())
                .phone(patient.getPhone())
                .email(patient.getEmail())
                .status(patient.getStatus());

        if (patient.getAddress() != null) {
            builder.addressStreet(patient.getAddress().getAddressStreet())
                    .addressNumber(patient.getAddress().getAddressNumber())
                    .addressCity(patient.getAddress().getAddressCity())
                    .addressState(patient.getAddress().getAddressState());
        }

        return builder.build();
    }
}
