package com.dataely.app.service.mapper;

import com.dataely.app.domain.*;
import com.dataely.app.service.dto.DataSourceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DataSource} and its DTO {@link DataSourceDTO}.
 */
@Mapper(componentModel = "spring", uses = { EnvironmentMapper.class })
public interface DataSourceMapper extends EntityMapper<DataSourceDTO, DataSource> {
    @Mapping(target = "environment", source = "environment", qualifiedByName = "name")
    DataSourceDTO toDto(DataSource s);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    DataSourceDTO toDtoName(DataSource dataSource);
}
