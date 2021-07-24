package com.dataely.app.service.mapper;

import com.dataely.app.domain.*;
import com.dataely.app.service.dto.BusinessUnitDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BusinessUnit} and its DTO {@link BusinessUnitDTO}.
 */
@Mapper(componentModel = "spring", uses = { OrganizationMapper.class })
public interface BusinessUnitMapper extends EntityMapper<BusinessUnitDTO, BusinessUnit> {
    @Mapping(target = "organization", source = "organization", qualifiedByName = "name")
    BusinessUnitDTO toDto(BusinessUnit s);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    BusinessUnitDTO toDtoName(BusinessUnit businessUnit);
}
