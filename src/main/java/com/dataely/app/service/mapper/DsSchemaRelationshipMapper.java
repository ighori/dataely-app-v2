package com.dataely.app.service.mapper;

import com.dataely.app.domain.*;
import com.dataely.app.service.dto.DsSchemaRelationshipDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DsSchemaRelationship} and its DTO {@link DsSchemaRelationshipDTO}.
 */
@Mapper(componentModel = "spring", uses = { DsSchemaMapper.class })
public interface DsSchemaRelationshipMapper extends EntityMapper<DsSchemaRelationshipDTO, DsSchemaRelationship> {
    @Mapping(target = "dsSchema", source = "dsSchema", qualifiedByName = "name")
    DsSchemaRelationshipDTO toDto(DsSchemaRelationship s);
}
