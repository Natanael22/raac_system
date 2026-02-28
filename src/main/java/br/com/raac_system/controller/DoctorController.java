package br.com.raac_system.controller;

import br.com.raac_system.dto.request.DoctorRequest;
import br.com.raac_system.dto.response.DoctorResponse;
import br.com.raac_system.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE')")
    public ResponseEntity<Page<DoctorResponse>> listDoctors(Pageable pageable) {
        return ResponseEntity.ok(doctorService.listDoctors(pageable));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DoctorResponse> createDoctor(@Valid @RequestBody DoctorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.createDoctor(request));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE')")
    public ResponseEntity<DoctorResponse> getDoctor(@PathVariable UUID id) {
        return ResponseEntity.ok(doctorService.getDoctor(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDoctor(@PathVariable UUID id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
}
