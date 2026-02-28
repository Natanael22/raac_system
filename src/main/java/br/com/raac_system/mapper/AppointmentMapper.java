package br.com.raac_system.mapper;

import br.com.raac_system.dto.response.AppointmentResponse;
import br.com.raac_system.entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "patient.name", target = "patientName")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "doctor.name", target = "doctorName")
    AppointmentResponse toDto(Appointment appointment);
}
