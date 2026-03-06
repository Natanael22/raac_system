package br.com.raac_system.dto.response;

import br.com.raac_system.entity.enums.EntityStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class DoctorResponse {
    private UUID id;
    private String name;
    private String crm;
    private String ufCrm;
    private String rqe;
    private String specialty;
    // Bank data might be needed by the user viewing their own profile
    private String bankCode;
    private String bankAgency;
    private String bankAccount;
    private EntityStatus status;
}
