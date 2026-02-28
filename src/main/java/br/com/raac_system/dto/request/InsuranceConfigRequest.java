package br.com.raac_system.dto.request;

import br.com.raac_system.entity.enums.InsuranceEnvironment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsuranceConfigRequest {

    @NotBlank
    private String operatorName;

    @NotBlank
    private String operatorAnscode;

    @NotNull
    private InsuranceEnvironment environment;

    private String webserviceUrl;

    // Will be encrypted
    private String credentials;

    // Will be encrypted
    private String certPassword;
}
