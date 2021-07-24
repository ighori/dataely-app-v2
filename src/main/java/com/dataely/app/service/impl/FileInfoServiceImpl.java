package com.dataely.app.service.impl;

import com.dataely.app.domain.FileInfo;
import com.dataely.app.repository.FileInfoRepository;
import com.dataely.app.service.FileInfoService;
import com.dataely.app.service.dto.FileInfoDTO;
import com.dataely.app.service.mapper.FileInfoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FileInfo}.
 */
@Service
@Transactional
public class FileInfoServiceImpl implements FileInfoService {

    private final Logger log = LoggerFactory.getLogger(FileInfoServiceImpl.class);

    private final FileInfoRepository fileInfoRepository;

    private final FileInfoMapper fileInfoMapper;

    public FileInfoServiceImpl(FileInfoRepository fileInfoRepository, FileInfoMapper fileInfoMapper) {
        this.fileInfoRepository = fileInfoRepository;
        this.fileInfoMapper = fileInfoMapper;
    }

    @Override
    public FileInfoDTO save(FileInfoDTO fileInfoDTO) {
        log.debug("Request to save FileInfo : {}", fileInfoDTO);
        FileInfo fileInfo = fileInfoMapper.toEntity(fileInfoDTO);
        fileInfo = fileInfoRepository.save(fileInfo);
        return fileInfoMapper.toDto(fileInfo);
    }

    @Override
    public Optional<FileInfoDTO> partialUpdate(FileInfoDTO fileInfoDTO) {
        log.debug("Request to partially update FileInfo : {}", fileInfoDTO);

        return fileInfoRepository
            .findById(fileInfoDTO.getId())
            .map(
                existingFileInfo -> {
                    fileInfoMapper.partialUpdate(existingFileInfo, fileInfoDTO);

                    return existingFileInfo;
                }
            )
            .map(fileInfoRepository::save)
            .map(fileInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FileInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FileInfos");
        return fileInfoRepository.findAll(pageable).map(fileInfoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FileInfoDTO> findOne(Long id) {
        log.debug("Request to get FileInfo : {}", id);
        return fileInfoRepository.findById(id).map(fileInfoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FileInfo : {}", id);
        fileInfoRepository.deleteById(id);
    }
}
