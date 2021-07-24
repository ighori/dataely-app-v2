package com.dataely.app.service.mapper;

import com.dataely.app.domain.*;
import com.dataely.app.service.dto.FileInfoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FileInfo} and its DTO {@link FileInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = { FileSourceMapper.class, FileConfigMapper.class })
public interface FileInfoMapper extends EntityMapper<FileInfoDTO, FileInfo> {
    @Mapping(target = "fileSource", source = "fileSource", qualifiedByName = "name")
    @Mapping(target = "fileConfig", source = "fileConfig", qualifiedByName = "name")
    FileInfoDTO toDto(FileInfo s);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    FileInfoDTO toDtoName(FileInfo fileInfo);
}
