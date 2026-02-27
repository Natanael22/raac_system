package br.com.raac_system.controller;

import br.com.raac_system.dto.request.BatchRequest;
import br.com.raac_system.dto.request.GuideRequest;
import br.com.raac_system.dto.response.ValidationResponse;
import br.com.raac_system.entity.BillingBatch;
import br.com.raac_system.entity.BillingGuide;
import br.com.raac_system.service.BillingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/billing")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping("/guides")
    public ResponseEntity<BillingGuide> createGuide(@Valid @RequestBody GuideRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billingService.createGuide(request));
    }

    @PostMapping("/batches")
    public ResponseEntity<BillingBatch> createBatch(@Valid @RequestBody BatchRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(billingService.createBatch(request));
    }

    @GetMapping("/batches/{id}/validate")
    public ResponseEntity<ValidationResponse> validateBatch(@PathVariable UUID id) {
        return ResponseEntity.ok(billingService.validateBatch(id));
    }

    @PostMapping("/batches/{id}/submit")
    public ResponseEntity<Void> submitBatch(@PathVariable UUID id) {
        billingService.submitBatch(id);
        return ResponseEntity.accepted().build();
    }
}
