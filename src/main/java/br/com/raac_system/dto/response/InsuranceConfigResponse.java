package br.com.raac_system.dto.response;

import br.com.raac_system.entity.enums.EntityStatus;
import br.com.raac_system.entity.enums.InsuranceEnvironment;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class InsuranceConfigResponse {
    private UUID id;
    private String operatorName;
    private String operatorAnscode;
    private InsuranceEnvironment environment;
    private String webserviceUrl;
    private EntityStatus status;
    // We intentionally avoid returning credentials here
}
