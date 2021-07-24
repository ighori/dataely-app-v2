package com.dataely.app.service.impl;

import com.dataely.app.domain.FileSource;
import com.dataely.app.repository.FileSourceRepository;
import com.dataely.app.service.FileSourceService;
import com.dataely.app.service.dto.FileSourceDTO;
import com.dataely.app.service.mapper.FileSourceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FileSource}.
 */
@Service
@Transactional
public class FileSourceServiceImpl implements FileSourceService {

    private final Logger log = LoggerFactory.getLogger(FileSourceServiceImpl.class);

    private final FileSourceRepository fileSourceRepository;

    private final FileSourceMapper fileSourceMapper;

    public FileSourceServiceImpl(FileSourceRepository fileSourceRepository, FileSourceMapper fileSourceMapper) {
        this.fileSourceRepository = fileSourceRepository;
        this.fileSourceMapper = fileSourceMapper;
    }

    @Override
    public FileSourceDTO save(FileSourceDTO fileSourceDTO) {
        log.debug("Request to save FileSource : {}", fileSourceDTO);
        FileSource fileSource = fileSourceMapper.toEntity(fileSourceDTO);
        fileSource = fileSourceRepository.save(fileSource);
        return fileSourceMapper.toDto(fileSource);
    }

    @Override
    public Optional<FileSourceDTO> partialUpdate(FileSourceDTO fileSourceDTO) {
        log.debug("Request to partially update FileSource : {}", fileSourceDTO);

        return fileSourceRepository
            .findById(fileSourceDTO.getId())
            .map(
                existingFileSource -> {
                    fileSourceMapper.partialUpdate(existingFileSource, fileSourceDTO);

                    return existingFileSource;
                }
            )
            .map(fileSourceRepository::save)
            .map(fileSourceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FileSourceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FileSources");
        return fileSourceRepository.findAll(pageable).map(fileSourceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FileSourceDTO> findOne(Long id) {
        log.debug("Request to get FileSource : {}", id);
        return fileSourceRepository.findById(id).map(fileSourceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FileSource : {}", id);
        fileSourceRepository.deleteById(id);
    }
}
