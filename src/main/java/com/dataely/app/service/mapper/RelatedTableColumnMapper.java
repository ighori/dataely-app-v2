package com.dataely.app.service.mapper;

import com.dataely.app.domain.*;
import com.dataely.app.service.dto.RelatedTableColumnDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RelatedTableColumn} and its DTO {@link RelatedTableColumnDTO}.
 */
@Mapper(componentModel = "spring", uses = { RelatedTableMapper.class })
public interface RelatedTableColumnMapper extends EntityMapper<RelatedTableColumnDTO, RelatedTableColumn> {
    @Mapping(target = "relatedTable", source = "relatedTable", qualifiedByName = "tableName")
    RelatedTableColumnDTO toDto(RelatedTableColumn s);
}
