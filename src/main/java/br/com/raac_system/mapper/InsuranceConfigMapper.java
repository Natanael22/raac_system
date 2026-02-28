package br.com.raac_system.mapper;

import br.com.raac_system.dto.request.InsuranceConfigRequest;
import br.com.raac_system.dto.response.InsuranceConfigResponse;
import br.com.raac_system.entity.InsuranceConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InsuranceConfigMapper {

    @Mapping(source = "ansCode", target = "operatorAnscode")
    @Mapping(source = "wsUrl", target = "webserviceUrl")
    InsuranceConfigResponse toDto(InsuranceConfig config);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "credentialsEncrypted", ignore = true) // Handled contextually
    @Mapping(target = "certPasswordEncrypted", ignore = true) // Handled contextually
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(source = "operatorAnscode", target = "ansCode")
    @Mapping(source = "webserviceUrl", target = "wsUrl")
    @Mapping(target = "tissVersion", ignore = true)
    @Mapping(target = "certPath", ignore = true)
    InsuranceConfig toEntity(InsuranceConfigRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tenant", ignore = true)
    @Mapping(target = "credentialsEncrypted", ignore = true)
    @Mapping(target = "certPasswordEncrypted", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(source = "operatorAnscode", target = "ansCode")
    @Mapping(source = "webserviceUrl", target = "wsUrl")
    @Mapping(target = "tissVersion", ignore = true)
    @Mapping(target = "certPath", ignore = true)
    void updateEntityFromDto(InsuranceConfigRequest request, @MappingTarget InsuranceConfig config);
}
