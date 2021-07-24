package com.dataely.app.service.mapper;

import com.dataely.app.domain.*;
import com.dataely.app.service.dto.AnalyzerJobDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnalyzerJob} and its DTO {@link AnalyzerJobDTO}.
 */
@Mapper(componentModel = "spring", uses = { EnvironmentMapper.class, DataSourceMapper.class, FileSourceMapper.class, UserMapper.class })
public interface AnalyzerJobMapper extends EntityMapper<AnalyzerJobDTO, AnalyzerJob> {
    @Mapping(target = "environment", source = "environment", qualifiedByName = "name")
    @Mapping(target = "dataSource", source = "dataSource", qualifiedByName = "name")
    @Mapping(target = "fileSource", source = "fileSource", qualifiedByName = "name")
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
    AnalyzerJobDTO toDto(AnalyzerJob s);
}
