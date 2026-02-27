package br.com.raac_system.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BatchRequest {
    @NotNull
    private UUID insuranceConfigId;

    @NotNull
    private Integer month;

    @NotNull
    private Integer year;
}
