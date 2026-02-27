package br.com.raac_system.dto.response;

import br.com.raac_system.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private UserDto user;

    @Getter
    @Setter
    @Builder
    public static class UserDto {
        private UUID id;
        private String name;
        private String email;
        private String role;
        private UUID tenantId;

        public static UserDto fromEntity(User user) {
            return builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .role(user.getRole().name())
                    .tenantId(user.getTenant() != null ? user.getTenant().getId() : null)
                    .build();
        }
    }
}
