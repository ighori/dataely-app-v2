package com.dataely.app.service.impl;

import com.dataely.app.domain.DsSchemaRelationship;
import com.dataely.app.repository.DsSchemaRelationshipRepository;
import com.dataely.app.service.DsSchemaRelationshipService;
import com.dataely.app.service.dto.DsSchemaRelationshipDTO;
import com.dataely.app.service.mapper.DsSchemaRelationshipMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DsSchemaRelationship}.
 */
@Service
@Transactional
public class DsSchemaRelationshipServiceImpl implements DsSchemaRelationshipService {

    private final Logger log = LoggerFactory.getLogger(DsSchemaRelationshipServiceImpl.class);

    private final DsSchemaRelationshipRepository dsSchemaRelationshipRepository;

    private final DsSchemaRelationshipMapper dsSchemaRelationshipMapper;

    public DsSchemaRelationshipServiceImpl(
        DsSchemaRelationshipRepository dsSchemaRelationshipRepository,
        DsSchemaRelationshipMapper dsSchemaRelationshipMapper
    ) {
        this.dsSchemaRelationshipRepository = dsSchemaRelationshipRepository;
        this.dsSchemaRelationshipMapper = dsSchemaRelationshipMapper;
    }

    @Override
    public DsSchemaRelationshipDTO save(DsSchemaRelationshipDTO dsSchemaRelationshipDTO) {
        log.debug("Request to save DsSchemaRelationship : {}", dsSchemaRelationshipDTO);
        DsSchemaRelationship dsSchemaRelationship = dsSchemaRelationshipMapper.toEntity(dsSchemaRelationshipDTO);
        dsSchemaRelationship = dsSchemaRelationshipRepository.save(dsSchemaRelationship);
        return dsSchemaRelationshipMapper.toDto(dsSchemaRelationship);
    }

    @Override
    public Optional<DsSchemaRelationshipDTO> partialUpdate(DsSchemaRelationshipDTO dsSchemaRelationshipDTO) {
        log.debug("Request to partially update DsSchemaRelationship : {}", dsSchemaRelationshipDTO);

        return dsSchemaRelationshipRepository
            .findById(dsSchemaRelationshipDTO.getId())
            .map(
                existingDsSchemaRelationship -> {
                    dsSchemaRelationshipMapper.partialUpdate(existingDsSchemaRelationship, dsSchemaRelationshipDTO);

                    return existingDsSchemaRelationship;
                }
            )
            .map(dsSchemaRelationshipRepository::save)
            .map(dsSchemaRelationshipMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DsSchemaRelationshipDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DsSchemaRelationships");
        return dsSchemaRelationshipRepository.findAll(pageable).map(dsSchemaRelationshipMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DsSchemaRelationshipDTO> findOne(Long id) {
        log.debug("Request to get DsSchemaRelationship : {}", id);
        return dsSchemaRelationshipRepository.findById(id).map(dsSchemaRelationshipMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DsSchemaRelationship : {}", id);
        dsSchemaRelationshipRepository.deleteById(id);
    }
}
