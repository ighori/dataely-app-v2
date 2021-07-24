package com.dataely.app.service.mapper;

import com.dataely.app.domain.*;
import com.dataely.app.service.dto.DsSchemaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DsSchema} and its DTO {@link DsSchemaDTO}.
 */
@Mapper(componentModel = "spring", uses = { DataSourceMapper.class })
public interface DsSchemaMapper extends EntityMapper<DsSchemaDTO, DsSchema> {
    @Mapping(target = "dataSource", source = "dataSource", qualifiedByName = "name")
    DsSchemaDTO toDto(DsSchema s);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    DsSchemaDTO toDtoName(DsSchema dsSchema);
}
