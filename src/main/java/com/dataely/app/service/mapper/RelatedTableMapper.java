package com.dataely.app.service.mapper;

import com.dataely.app.domain.*;
import com.dataely.app.service.dto.RelatedTableDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RelatedTable} and its DTO {@link RelatedTableDTO}.
 */
@Mapper(componentModel = "spring", uses = { TablesDefinitionMapper.class })
public interface RelatedTableMapper extends EntityMapper<RelatedTableDTO, RelatedTable> {
    @Mapping(target = "tablesDefinition", source = "tablesDefinition", qualifiedByName = "tableName")
    RelatedTableDTO toDto(RelatedTable s);

    @Named("tableName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "tableName", source = "tableName")
    RelatedTableDTO toDtoTableName(RelatedTable relatedTable);
}
