package br.com.raac_system.mapper;

import br.com.raac_system.dto.request.PatientInsuranceRequest;
import br.com.raac_system.dto.response.PatientInsuranceResponse;
import br.com.raac_system.entity.PatientInsurance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PatientInsuranceMapper {

    @Mapping(source = "patient.id", target = "patientId")
    PatientInsuranceResponse toDto(PatientInsurance patientInsurance);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    PatientInsurance toEntity(PatientInsuranceRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    void updateEntityFromDto(PatientInsuranceRequest request, @MappingTarget PatientInsurance patientInsurance);
}
