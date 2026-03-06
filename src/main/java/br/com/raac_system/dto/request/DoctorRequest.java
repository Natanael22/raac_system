package br.com.raac_system.dto.request;

import br.com.raac_system.entity.enums.EntityStatus;
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
    private String rqe;

    // Bank data (stored in plain columns on Doctor)
    private String bankCode;
    private String bankAgency;
    private String bankAccount;

    private EntityStatus status;
}
