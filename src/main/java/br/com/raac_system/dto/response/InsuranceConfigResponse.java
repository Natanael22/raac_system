package br.com.raac_system.dto.response;

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
    private String environment;
    private String webserviceUrl;
    // We intentionally avoid returning credentials here
}
