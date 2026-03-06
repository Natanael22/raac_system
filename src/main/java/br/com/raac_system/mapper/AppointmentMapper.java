package br.com.raac_system.mapper;

import org.mapstruct.Mapper;

/**
 * AppointmentMapper — placeholder.
 * Appointments are mapped via AppointmentResponse.fromEntity() in
 * AppointmentService.
 */
@Mapper(componentModel = "spring")
public interface AppointmentMapper {
    // No methods needed — mapping is done manually in AppointmentService via
    // AppointmentResponse.fromEntity()
}
