package br.com.raac_system.service;

import br.com.raac_system.dto.request.AppointmentRequest;
import br.com.raac_system.dto.response.AppointmentResponse;
import br.com.raac_system.entity.Appointment;
import br.com.raac_system.entity.Doctor;
import br.com.raac_system.entity.Patient;
import br.com.raac_system.entity.Tenant;
import br.com.raac_system.entity.enums.AppointmentStatus;
import br.com.raac_system.exception.BusinessException;
import br.com.raac_system.repository.AppointmentRepository;
import br.com.raac_system.repository.PatientRepository;
import br.com.raac_system.security.TenantContext;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    public Page<AppointmentResponse> listAppointments(Pageable pageable) {
        return appointmentRepository.findAllByTenantIdAndDeletedAtIsNull(getTenantId(), pageable)
                .map(AppointmentResponse::fromEntity);
    }

    @Transactional
    public AppointmentResponse createAppointment(AppointmentRequest request) {
        Patient patient = patientRepository.findByIdAndTenantIdAndDeletedAtIsNull(request.getPatientId(), getTenantId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found"));

        Appointment appointment = new Appointment();
        Tenant tenantRef = new Tenant();
        tenantRef.setId(getTenantId());
        appointment.setTenant(tenantRef);
        appointment.setPatient(patient);

        Doctor doctorRef = new Doctor();
        doctorRef.setId(request.getDoctorId());
        appointment.setDoctor(doctorRef);

        appointment.setType(request.getType());
        appointment.setStartDt(request.getStartDt());
        appointment.setEndDt(request.getEndDt());
        appointment.setStatus(AppointmentStatus.AGENDADO);
        appointment.setRoom(request.getRoom());
        appointment.setOrigin(request.getOrigin());
        appointment.setNotes(request.getNotes());

        appointment = appointmentRepository.save(appointment);
        return AppointmentResponse.fromEntity(appointment);
    }

    @Transactional
    public AppointmentResponse patchStatus(UUID id, String newStatus) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        try {
            appointment.setStatus(AppointmentStatus.valueOf(newStatus.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new BusinessException("Invalid appointment status: " + newStatus);
        }

        appointment = appointmentRepository.save(appointment);
        return AppointmentResponse.fromEntity(appointment);
    }

    private UUID getTenantId() {
        String tenantIdStr = TenantContext.getTenantId();
        if (tenantIdStr == null)
            throw new BusinessException("Tenant ID is missing");
        return UUID.fromString(tenantIdStr);
    }
}
