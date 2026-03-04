package br.com.raac_system.mapper;

import br.com.raac_system.dto.request.DoctorRequest;
import br.com.raac_system.dto.response.DoctorResponse;
import br.com.raac_system.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorResponse toDto(Doctor doctor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "bankAccountEncrypted", ignore = true) // Handled contextually
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "rqe", ignore = true)
    @Mapping(target = "signatureUrl", ignore = true)
    Doctor toEntity(DoctorRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "bankAccountEncrypted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "rqe", ignore = true)
    @Mapping(target = "signatureUrl", ignore = true)
    void updateEntityFromDto(DoctorRequest request, @MappingTarget Doctor doctor);
}
