package com.dataely.app.service.mapper;

import com.dataely.app.domain.*;
import com.dataely.app.service.dto.AnalyzerRecognizersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnalyzerRecognizers} and its DTO {@link AnalyzerRecognizersDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnalyzerRecognizersMapper extends EntityMapper<AnalyzerRecognizersDTO, AnalyzerRecognizers> {}
