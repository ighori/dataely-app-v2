package com.dataely.app.service.mapper;

import com.dataely.app.domain.*;
import com.dataely.app.service.dto.AnalyzerResultDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnalyzerResult} and its DTO {@link AnalyzerResultDTO}.
 */
@Mapper(componentModel = "spring", uses = { DataSourceMapper.class, FileSourceMapper.class })
public interface AnalyzerResultMapper extends EntityMapper<AnalyzerResultDTO, AnalyzerResult> {
    @Mapping(target = "dataSource", source = "dataSource", qualifiedByName = "name")
    @Mapping(target = "fileSource", source = "fileSource", qualifiedByName = "name")
    AnalyzerResultDTO toDto(AnalyzerResult s);
}
