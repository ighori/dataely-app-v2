package com.dataely.app.service.mapper;

import com.dataely.app.domain.*;
import com.dataely.app.service.dto.AnalyzerAdHocRecognizersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnalyzerAdHocRecognizers} and its DTO {@link AnalyzerAdHocRecognizersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnalyzerAdHocRecognizersMapper extends EntityMapper<AnalyzerAdHocRecognizersDTO, AnalyzerAdHocRecognizers> {}
