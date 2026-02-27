package br.com.raac_system.dto.request;

import br.com.raac_system.entity.enums.GuideType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class GuideRequest {
    @NotNull
    private UUID appointmentId;

    private UUID authRequestId; // optional, depends on procedure

    @NotNull
    private GuideType guideType;

    private String guideNumber;
    private String proceduresJson;
    private BigDecimal totalValue;
}
