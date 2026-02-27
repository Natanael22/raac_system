package br.com.raac_system.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ValidationResponse {
    private boolean valid;
    private List<String> errors;
}
