package com.dataely.app.service.mapper;

import com.dataely.app.domain.*;
import com.dataely.app.service.dto.TablesDefinitionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TablesDefinition} and its DTO {@link TablesDefinitionDTO}.
 */
@Mapper(componentModel = "spring", uses = { DsSchemaMapper.class })
public interface TablesDefinitionMapper extends EntityMapper<TablesDefinitionDTO, TablesDefinition> {
    @Mapping(target = "dsSchema", source = "dsSchema", qualifiedByName = "name")
    TablesDefinitionDTO toDto(TablesDefinition s);

    @Named("tableName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "tableName", source = "tableName")
    TablesDefinitionDTO toDtoTableName(TablesDefinition tablesDefinition);
}
