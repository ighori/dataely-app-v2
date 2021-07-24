package com.dataely.app.service.mapper;

import com.dataely.app.domain.*;
import com.dataely.app.service.dto.FileFieldDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FileField} and its DTO {@link FileFieldDTO}.
 */
@Mapper(componentModel = "spring", uses = { FileInfoMapper.class })
public interface FileFieldMapper extends EntityMapper<FileFieldDTO, FileField> {
    @Mapping(target = "fileInfo", source = "fileInfo", qualifiedByName = "name")
    FileFieldDTO toDto(FileField s);
}
