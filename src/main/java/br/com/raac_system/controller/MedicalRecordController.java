package br.com.raac_system.controller;

import br.com.raac_system.dto.request.MedicalRecordRequest;
import br.com.raac_system.dto.response.MedicalRecordResponse;
import br.com.raac_system.service.MedicalRecordService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/medical-records")
public class MedicalRecordController {

    private final MedicalRecordService service;

    public MedicalRecordController(MedicalRecordService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO', 'ATENDENTE')")
    public ResponseEntity<Page<MedicalRecordResponse>> listAll(Pageable pageable) {
        return ResponseEntity.ok(service.listRecords(pageable));
    }

    @GetMapping("/patient/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO', 'ATENDENTE')")
    public ResponseEntity<Page<MedicalRecordResponse>> listByPatient(@PathVariable UUID patientId, Pageable pageable) {
        return ResponseEntity.ok(service.listRecordsByPatient(patientId, pageable));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    public ResponseEntity<MedicalRecordResponse> create(@Valid @RequestBody MedicalRecordRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createRecord(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    public ResponseEntity<MedicalRecordResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getRecord(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }
}
