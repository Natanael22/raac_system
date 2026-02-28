package br.com.raac_system.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class DoctorResponse {
    private UUID id;
    private String name;
    private String crm;
    private String specialty;
}
