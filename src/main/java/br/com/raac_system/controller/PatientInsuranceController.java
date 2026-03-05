package br.com.raac_system.controller;

import br.com.raac_system.dto.request.PatientInsuranceRequest;
import br.com.raac_system.dto.response.PatientInsuranceResponse;
import br.com.raac_system.service.PatientInsuranceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/patients/{patientId}/insurances")
public class PatientInsuranceController {

    private final PatientInsuranceService service;

    public PatientInsuranceController(PatientInsuranceService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE', 'MEDICO')")
    public ResponseEntity<Page<PatientInsuranceResponse>> listInsurances(
            @PathVariable UUID patientId, Pageable pageable) {
        return ResponseEntity.ok(service.listInsurances(patientId, pageable));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE')")
    public ResponseEntity<PatientInsuranceResponse> createInsurance(
            @PathVariable UUID patientId,
            @Valid @RequestBody PatientInsuranceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createInsurance(patientId, request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE')")
    public ResponseEntity<PatientInsuranceResponse> updateInsurance(
            @PathVariable UUID patientId,
            @PathVariable UUID id,
            @Valid @RequestBody PatientInsuranceRequest request) {
        return ResponseEntity.ok(service.updateInsurance(patientId, id, request));
    }
}
