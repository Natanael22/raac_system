package br.com.raac_system.mapper;

import br.com.raac_system.dto.response.LoginResponse.UserDto;
import br.com.raac_system.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "tenant.id", target = "tenantId")
    UserDto toDto(User user);
}
