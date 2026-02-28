package br.com.raac_system.controller;

import br.com.raac_system.dto.request.InsuranceConfigRequest;
import br.com.raac_system.dto.response.InsuranceConfigResponse;
import br.com.raac_system.service.InsuranceConfigService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/insurance-configs")
public class InsuranceConfigController {

    private final InsuranceConfigService service;

    public InsuranceConfigController(InsuranceConfigService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<InsuranceConfigResponse>> listAll(Pageable pageable) {
        return ResponseEntity.ok(service.listConfigs(pageable));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InsuranceConfigResponse> create(@Valid @RequestBody InsuranceConfigRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createConfig(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InsuranceConfigResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getConfig(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteConfig(id);
        return ResponseEntity.noContent().build();
    }
}
