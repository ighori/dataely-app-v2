package com.dataely.app.service.mapper;

import com.dataely.app.domain.*;
import com.dataely.app.service.dto.TableColumnDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TableColumn} and its DTO {@link TableColumnDTO}.
 */
@Mapper(componentModel = "spring", uses = { TablesDefinitionMapper.class })
public interface TableColumnMapper extends EntityMapper<TableColumnDTO, TableColumn> {
    @Mapping(target = "tablesDefinition", source = "tablesDefinition", qualifiedByName = "tableName")
    TableColumnDTO toDto(TableColumn s);
}
