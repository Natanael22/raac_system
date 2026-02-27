package br.com.raac_system.integration;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EligibilityResult {
    private boolean eligible;
    private String message;
}
