package br.com.raac_system.dto.request;

import br.com.raac_system.entity.enums.Sex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;

    private String cpf;
    private String cns;
    private LocalDate birthDate;

    @NotNull(message = "Sex is mandatory")
    private Sex sex;

    private String phone;
    private String email;
    private String prontuarioNumber;

    // Address fields
    private String addressStreet;
    private String addressNumber;
    private String addressCity;
    private String addressState;
}
