package com.dataely.app.service.impl;

import com.dataely.app.domain.FileConfig;
import com.dataely.app.repository.FileConfigRepository;
import com.dataely.app.service.FileConfigService;
import com.dataely.app.service.dto.FileConfigDTO;
import com.dataely.app.service.mapper.FileConfigMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FileConfig}.
 */
@Service
@Transactional
public class FileConfigServiceImpl implements FileConfigService {

    private final Logger log = LoggerFactory.getLogger(FileConfigServiceImpl.class);

    private final FileConfigRepository fileConfigRepository;

    private final FileConfigMapper fileConfigMapper;

    public FileConfigServiceImpl(FileConfigRepository fileConfigRepository, FileConfigMapper fileConfigMapper) {
        this.fileConfigRepository = fileConfigRepository;
        this.fileConfigMapper = fileConfigMapper;
    }

    @Override
    public FileConfigDTO save(FileConfigDTO fileConfigDTO) {
        log.debug("Request to save FileConfig : {}", fileConfigDTO);
        FileConfig fileConfig = fileConfigMapper.toEntity(fileConfigDTO);
        fileConfig = fileConfigRepository.save(fileConfig);
        return fileConfigMapper.toDto(fileConfig);
    }

    @Override
    public Optional<FileConfigDTO> partialUpdate(FileConfigDTO fileConfigDTO) {
        log.debug("Request to partially update FileConfig : {}", fileConfigDTO);

        return fileConfigRepository
            .findById(fileConfigDTO.getId())
            .map(
                existingFileConfig -> {
                    fileConfigMapper.partialUpdate(existingFileConfig, fileConfigDTO);

                    return existingFileConfig;
                }
            )
            .map(fileConfigRepository::save)
            .map(fileConfigMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FileConfigDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FileConfigs");
        return fileConfigRepository.findAll(pageable).map(fileConfigMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FileConfigDTO> findOne(Long id) {
        log.debug("Request to get FileConfig : {}", id);
        return fileConfigRepository.findById(id).map(fileConfigMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FileConfig : {}", id);
        fileConfigRepository.deleteById(id);
    }
}
