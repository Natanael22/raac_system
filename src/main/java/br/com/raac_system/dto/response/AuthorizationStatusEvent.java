package br.com.raac_system.dto.response;

import br.com.raac_system.entity.enums.AuthorizationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthorizationStatusEvent {
    private AuthorizationStatus status;
    private String authNumber;
}
