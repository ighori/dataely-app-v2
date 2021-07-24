package com.dataely.app.service.impl;

import com.dataely.app.domain.DataSource;
import com.dataely.app.repository.DataSourceRepository;
import com.dataely.app.service.DataSourceService;
import com.dataely.app.service.dto.DataSourceDTO;
import com.dataely.app.service.mapper.DataSourceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DataSource}.
 */
@Service
@Transactional
public class DataSourceServiceImpl implements DataSourceService {

    private final Logger log = LoggerFactory.getLogger(DataSourceServiceImpl.class);

    private final DataSourceRepository dataSourceRepository;

    private final DataSourceMapper dataSourceMapper;

    public DataSourceServiceImpl(DataSourceRepository dataSourceRepository, DataSourceMapper dataSourceMapper) {
        this.dataSourceRepository = dataSourceRepository;
        this.dataSourceMapper = dataSourceMapper;
    }

    @Override
    public DataSourceDTO save(DataSourceDTO dataSourceDTO) {
        log.debug("Request to save DataSource : {}", dataSourceDTO);
        DataSource dataSource = dataSourceMapper.toEntity(dataSourceDTO);
        dataSource = dataSourceRepository.save(dataSource);
        return dataSourceMapper.toDto(dataSource);
    }

    @Override
    public Optional<DataSourceDTO> partialUpdate(DataSourceDTO dataSourceDTO) {
        log.debug("Request to partially update DataSource : {}", dataSourceDTO);

        return dataSourceRepository
            .findById(dataSourceDTO.getId())
            .map(
                existingDataSource -> {
                    dataSourceMapper.partialUpdate(existingDataSource, dataSourceDTO);

                    return existingDataSource;
                }
            )
            .map(dataSourceRepository::save)
            .map(dataSourceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DataSourceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DataSources");
        return dataSourceRepository.findAll(pageable).map(dataSourceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DataSourceDTO> findOne(Long id) {
        log.debug("Request to get DataSource : {}", id);
        return dataSourceRepository.findById(id).map(dataSourceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DataSource : {}", id);
        dataSourceRepository.deleteById(id);
    }
}
