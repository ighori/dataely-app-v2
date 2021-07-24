package com.dataely.app.service.mapper;

import com.dataely.app.domain.*;
import com.dataely.app.service.dto.TableRelationshipDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TableRelationship} and its DTO {@link TableRelationshipDTO}.
 */
@Mapper(componentModel = "spring", uses = { TablesDefinitionMapper.class })
public interface TableRelationshipMapper extends EntityMapper<TableRelationshipDTO, TableRelationship> {
    @Mapping(target = "tablesDefinition", source = "tablesDefinition", qualifiedByName = "tableName")
    TableRelationshipDTO toDto(TableRelationship s);
}
