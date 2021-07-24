package com.dataely.app.service.impl;

import com.dataely.app.domain.DsSchema;
import com.dataely.app.repository.DsSchemaRepository;
import com.dataely.app.service.DsSchemaService;
import com.dataely.app.service.dto.DsSchemaDTO;
import com.dataely.app.service.mapper.DsSchemaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DsSchema}.
 */
@Service
@Transactional
public class DsSchemaServiceImpl implements DsSchemaService {

    private final Logger log = LoggerFactory.getLogger(DsSchemaServiceImpl.class);

    private final DsSchemaRepository dsSchemaRepository;

    private final DsSchemaMapper dsSchemaMapper;

    public DsSchemaServiceImpl(DsSchemaRepository dsSchemaRepository, DsSchemaMapper dsSchemaMapper) {
        this.dsSchemaRepository = dsSchemaRepository;
        this.dsSchemaMapper = dsSchemaMapper;
    }

    @Override
    public DsSchemaDTO save(DsSchemaDTO dsSchemaDTO) {
        log.debug("Request to save DsSchema : {}", dsSchemaDTO);
        DsSchema dsSchema = dsSchemaMapper.toEntity(dsSchemaDTO);
        dsSchema = dsSchemaRepository.save(dsSchema);
        return dsSchemaMapper.toDto(dsSchema);
    }

    @Override
    public Optional<DsSchemaDTO> partialUpdate(DsSchemaDTO dsSchemaDTO) {
        log.debug("Request to partially update DsSchema : {}", dsSchemaDTO);

        return dsSchemaRepository
            .findById(dsSchemaDTO.getId())
            .map(
                existingDsSchema -> {
                    dsSchemaMapper.partialUpdate(existingDsSchema, dsSchemaDTO);

                    return existingDsSchema;
                }
            )
            .map(dsSchemaRepository::save)
            .map(dsSchemaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DsSchemaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DsSchemas");
        return dsSchemaRepository.findAll(pageable).map(dsSchemaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DsSchemaDTO> findOne(Long id) {
        log.debug("Request to get DsSchema : {}", id);
        return dsSchemaRepository.findById(id).map(dsSchemaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DsSchema : {}", id);
        dsSchemaRepository.deleteById(id);
    }
}
