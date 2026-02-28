package br.com.raac_system.mapper;

import br.com.raac_system.dto.request.PatientRequest;
import br.com.raac_system.dto.response.PatientResponse;
import br.com.raac_system.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientResponse toDto(Patient patient);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "address", ignore = true) // Handled in service due to embedding
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "maritalStatus", ignore = true)
    @Mapping(target = "allergies", ignore = true)
    @Mapping(target = "bloodType", ignore = true)
    Patient toEntity(PatientRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "gender", ignore = true)
    @Mapping(target = "maritalStatus", ignore = true)
    @Mapping(target = "allergies", ignore = true)
    @Mapping(target = "bloodType", ignore = true)
    void updateEntityFromDto(PatientRequest request, @MappingTarget Patient patient);
}
