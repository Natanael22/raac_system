package br.com.raac_system.controller;

import br.com.raac_system.dto.request.AuthorizationRequestDto;
import br.com.raac_system.entity.AuthorizationRequest;
import br.com.raac_system.entity.enums.AuthorizationStatus;
import br.com.raac_system.service.AuthorizationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/authorizations")
public class AuthorizationController {

    private final AuthorizationService authService;

    public AuthorizationController(AuthorizationService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> requestAuth(@Valid @RequestBody AuthorizationRequestDto request) {
        AuthorizationRequest authReq = authService.createAuthorization(request);
        return ResponseEntity.accepted().body(Map.of(
                "id", authReq.getId(),
                "status", authReq.getStatus()));
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<Map<String, String>> getStatus(@PathVariable UUID id) {
        AuthorizationStatus status = authService.getStatus(id);
        return ResponseEntity.ok(Map.of("status", status.name()));
    }
}
