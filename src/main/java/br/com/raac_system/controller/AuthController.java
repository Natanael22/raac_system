package br.com.raac_system.controller;

import br.com.raac_system.dto.request.LoginRequest;
import br.com.raac_system.dto.response.LoginResponse;
import br.com.raac_system.security.CustomUserDetails;
import br.com.raac_system.security.TenantContext;
import br.com.raac_system.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request,
            @RequestHeader(value = "X-Tenant-ID", required = false) String headerTenantId) {
        if (request.getTenantId() != null) {
            TenantContext.setTenantId(request.getTenantId().toString());
        } else if (headerTenantId != null && !headerTenantId.isBlank()) {
            TenantContext.setTenantId(headerTenantId);
        }

        LoginResponse response = authService.authenticate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails != null) {
            authService.logout(userDetails.getId().toString());
        }
        return ResponseEntity.noContent().build();
    }
}
