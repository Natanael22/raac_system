package br.com.raac_system.controller;

import br.com.raac_system.dto.request.AppointmentRequest;
import br.com.raac_system.dto.response.AppointmentResponse;
import br.com.raac_system.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE', 'MEDICO')")
    public ResponseEntity<Page<AppointmentResponse>> listAppointments(Pageable pageable) {
        return ResponseEntity.ok(appointmentService.listAppointments(pageable));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE')")
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody AppointmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.createAppointment(request));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'ATENDENTE', 'MEDICO')")
    public ResponseEntity<AppointmentResponse> updateStatus(@PathVariable UUID id,
            @RequestBody Map<String, String> payload) {
        String newStatus = payload.get("status");
        return ResponseEntity.ok(appointmentService.patchStatus(id, newStatus));
    }
}
