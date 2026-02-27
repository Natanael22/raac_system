package br.com.raac_system.integration;

import br.com.raac_system.entity.enums.AuthorizationStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BradescoTISSAdapter implements TISSPort {

    // Hypothetical specific implementation for Bradesco using JAXB

    @Override
    public AuthorizationResult requestAuthorization(TISSRequest request) {
        return AuthorizationResult.builder()
                .status(AuthorizationStatus.APROVADO)
                .authNumber("BRAD-" + UUID.randomUUID().toString().substring(0, 8))
                .protocol(UUID.randomUUID().toString())
                .build();
    }

    @Override
    public EligibilityResult checkEligibility(String cardNumber, UUID insuranceConfigId) {
        return EligibilityResult.builder().eligible(true).message("Bradesco Active").build();
    }

    @Override
    public AuthorizationResult getAuthorizationStatus(String protocol, UUID insuranceConfigId) {
        return AuthorizationResult.builder()
                .status(AuthorizationStatus.APROVADO)
                .authNumber("BRAD-" + UUID.randomUUID().toString().substring(0, 8))
                .protocol(protocol)
                .build();
    }
}
