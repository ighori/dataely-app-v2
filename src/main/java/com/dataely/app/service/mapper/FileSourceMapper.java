package com.dataely.app.service.mapper;

import com.dataely.app.domain.*;
import com.dataely.app.service.dto.FileSourceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FileSource} and its DTO {@link FileSourceDTO}.
 */
@Mapper(componentModel = "spring", uses = { EnvironmentMapper.class })
public interface FileSourceMapper extends EntityMapper<FileSourceDTO, FileSource> {
    @Mapping(target = "environment", source = "environment", qualifiedByName = "name")
    FileSourceDTO toDto(FileSource s);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    FileSourceDTO toDtoName(FileSource fileSource);
}
