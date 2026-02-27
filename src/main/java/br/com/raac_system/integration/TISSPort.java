package br.com.raac_system.integration;

import java.util.UUID;

public interface TISSPort {
    AuthorizationResult requestAuthorization(TISSRequest request);

    EligibilityResult checkEligibility(String cardNumber, UUID insuranceConfigId);

    AuthorizationResult getAuthorizationStatus(String protocol, UUID insuranceConfigId);
}
