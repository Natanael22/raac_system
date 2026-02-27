package br.com.raac_system.integration;

import br.com.raac_system.entity.enums.AuthorizationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthorizationResult {
    private AuthorizationStatus status;
    private String authNumber;
    private String protocol;
    private String rejectionReason;
}
