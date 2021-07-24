package com.dataely.app.service.impl;

import com.dataely.app.domain.TableRelationship;
import com.dataely.app.repository.TableRelationshipRepository;
import com.dataely.app.service.TableRelationshipService;
import com.dataely.app.service.dto.TableRelationshipDTO;
import com.dataely.app.service.mapper.TableRelationshipMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TableRelationship}.
 */
@Service
@Transactional
public class TableRelationshipServiceImpl implements TableRelationshipService {

    private final Logger log = LoggerFactory.getLogger(TableRelationshipServiceImpl.class);

    private final TableRelationshipRepository tableRelationshipRepository;

    private final TableRelationshipMapper tableRelationshipMapper;

    public TableRelationshipServiceImpl(
        TableRelationshipRepository tableRelationshipRepository,
        TableRelationshipMapper tableRelationshipMapper
    ) {
        this.tableRelationshipRepository = tableRelationshipRepository;
        this.tableRelationshipMapper = tableRelationshipMapper;
    }

    @Override
    public TableRelationshipDTO save(TableRelationshipDTO tableRelationshipDTO) {
        log.debug("Request to save TableRelationship : {}", tableRelationshipDTO);
        TableRelationship tableRelationship = tableRelationshipMapper.toEntity(tableRelationshipDTO);
        tableRelationship = tableRelationshipRepository.save(tableRelationship);
        return tableRelationshipMapper.toDto(tableRelationship);
    }

    @Override
    public Optional<TableRelationshipDTO> partialUpdate(TableRelationshipDTO tableRelationshipDTO) {
        log.debug("Request to partially update TableRelationship : {}", tableRelationshipDTO);

        return tableRelationshipRepository
            .findById(tableRelationshipDTO.getId())
            .map(
                existingTableRelationship -> {
                    tableRelationshipMapper.partialUpdate(existingTableRelationship, tableRelationshipDTO);

                    return existingTableRelationship;
                }
            )
            .map(tableRelationshipRepository::save)
            .map(tableRelationshipMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TableRelationshipDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TableRelationships");
        return tableRelationshipRepository.findAll(pageable).map(tableRelationshipMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TableRelationshipDTO> findOne(Long id) {
        log.debug("Request to get TableRelationship : {}", id);
        return tableRelationshipRepository.findById(id).map(tableRelationshipMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TableRelationship : {}", id);
        tableRelationshipRepository.deleteById(id);
    }
}
