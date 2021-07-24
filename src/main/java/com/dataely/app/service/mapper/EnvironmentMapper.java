package com.dataely.app.service.mapper;

import com.dataely.app.domain.*;
import com.dataely.app.service.dto.EnvironmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Environment} and its DTO {@link EnvironmentDTO}.
 */
@Mapper(componentModel = "spring", uses = { ContactMapper.class, ApplicationMapper.class })
public interface EnvironmentMapper extends EntityMapper<EnvironmentDTO, Environment> {
    @Mapping(target = "contact", source = "contact", qualifiedByName = "name")
    @Mapping(target = "application", source = "application", qualifiedByName = "name")
    EnvironmentDTO toDto(Environment s);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    EnvironmentDTO toDtoName(Environment environment);
}
