package com.dataely.app.service.impl;

import com.dataely.app.domain.RelatedTable;
import com.dataely.app.repository.RelatedTableRepository;
import com.dataely.app.service.RelatedTableService;
import com.dataely.app.service.dto.RelatedTableDTO;
import com.dataely.app.service.mapper.RelatedTableMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RelatedTable}.
 */
@Service
@Transactional
public class RelatedTableServiceImpl implements RelatedTableService {

    private final Logger log = LoggerFactory.getLogger(RelatedTableServiceImpl.class);

    private final RelatedTableRepository relatedTableRepository;

    private final RelatedTableMapper relatedTableMapper;

    public RelatedTableServiceImpl(RelatedTableRepository relatedTableRepository, RelatedTableMapper relatedTableMapper) {
        this.relatedTableRepository = relatedTableRepository;
        this.relatedTableMapper = relatedTableMapper;
    }

    @Override
    public RelatedTableDTO save(RelatedTableDTO relatedTableDTO) {
        log.debug("Request to save RelatedTable : {}", relatedTableDTO);
        RelatedTable relatedTable = relatedTableMapper.toEntity(relatedTableDTO);
        relatedTable = relatedTableRepository.save(relatedTable);
        return relatedTableMapper.toDto(relatedTable);
    }

    @Override
    public Optional<RelatedTableDTO> partialUpdate(RelatedTableDTO relatedTableDTO) {
        log.debug("Request to partially update RelatedTable : {}", relatedTableDTO);

        return relatedTableRepository
            .findById(relatedTableDTO.getId())
            .map(
                existingRelatedTable -> {
                    relatedTableMapper.partialUpdate(existingRelatedTable, relatedTableDTO);

                    return existingRelatedTable;
                }
            )
            .map(relatedTableRepository::save)
            .map(relatedTableMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RelatedTableDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RelatedTables");
        return relatedTableRepository.findAll(pageable).map(relatedTableMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RelatedTableDTO> findOne(Long id) {
        log.debug("Request to get RelatedTable : {}", id);
        return relatedTableRepository.findById(id).map(relatedTableMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RelatedTable : {}", id);
        relatedTableRepository.deleteById(id);
    }
}
