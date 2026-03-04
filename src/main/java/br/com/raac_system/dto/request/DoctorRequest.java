package br.com.raac_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "CRM is mandatory")
    private String crm;

    @NotBlank(message = "UF CRM is mandatory")
    private String ufCrm;

    private String specialty;

    // Encrypted bank data
    private String baBankCode;
    private String baAgency;
    private String baAccount;
}
