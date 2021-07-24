package com.dataely.app.service.mapper;

import com.dataely.app.domain.*;
import com.dataely.app.service.dto.FileConfigDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FileConfig} and its DTO {@link FileConfigDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FileConfigMapper extends EntityMapper<FileConfigDTO, FileConfig> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    FileConfigDTO toDtoName(FileConfig fileConfig);
}
