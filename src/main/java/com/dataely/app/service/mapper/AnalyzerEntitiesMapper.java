package com.dataely.app.service.mapper;

import com.dataely.app.domain.*;
import com.dataely.app.service.dto.AnalyzerEntitiesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnalyzerEntities} and its DTO {@link AnalyzerEntitiesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnalyzerEntitiesMapper extends EntityMapper<AnalyzerEntitiesDTO, AnalyzerEntities> {}
