package com.dataely.app.service.impl;

import com.dataely.app.domain.FileField;
import com.dataely.app.repository.FileFieldRepository;
import com.dataely.app.service.FileFieldService;
import com.dataely.app.service.dto.FileFieldDTO;
import com.dataely.app.service.mapper.FileFieldMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FileField}.
 */
@Service
@Transactional
public class FileFieldServiceImpl implements FileFieldService {

    private final Logger log = LoggerFactory.getLogger(FileFieldServiceImpl.class);

    private final FileFieldRepository fileFieldRepository;

    private final FileFieldMapper fileFieldMapper;

    public FileFieldServiceImpl(FileFieldRepository fileFieldRepository, FileFieldMapper fileFieldMapper) {
        this.fileFieldRepository = fileFieldRepository;
        this.fileFieldMapper = fileFieldMapper;
    }

    @Override
    public FileFieldDTO save(FileFieldDTO fileFieldDTO) {
        log.debug("Request to save FileField : {}", fileFieldDTO);
        FileField fileField = fileFieldMapper.toEntity(fileFieldDTO);
        fileField = fileFieldRepository.save(fileField);
        return fileFieldMapper.toDto(fileField);
    }

    @Override
    public Optional<FileFieldDTO> partialUpdate(FileFieldDTO fileFieldDTO) {
        log.debug("Request to partially update FileField : {}", fileFieldDTO);

        return fileFieldRepository
            .findById(fileFieldDTO.getId())
            .map(
                existingFileField -> {
                    fileFieldMapper.partialUpdate(existingFileField, fileFieldDTO);

                    return existingFileField;
                }
            )
            .map(fileFieldRepository::save)
            .map(fileFieldMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FileFieldDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FileFields");
        return fileFieldRepository.findAll(pageable).map(fileFieldMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FileFieldDTO> findOne(Long id) {
        log.debug("Request to get FileField : {}", id);
        return fileFieldRepository.findById(id).map(fileFieldMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FileField : {}", id);
        fileFieldRepository.deleteById(id);
    }
}
