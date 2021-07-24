package com.dataely.app.service.impl;

import com.dataely.app.domain.RelatedTableColumn;
import com.dataely.app.repository.RelatedTableColumnRepository;
import com.dataely.app.service.RelatedTableColumnService;
import com.dataely.app.service.dto.RelatedTableColumnDTO;
import com.dataely.app.service.mapper.RelatedTableColumnMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RelatedTableColumn}.
 */
@Service
@Transactional
public class RelatedTableColumnServiceImpl implements RelatedTableColumnService {

    private final Logger log = LoggerFactory.getLogger(RelatedTableColumnServiceImpl.class);

    private final RelatedTableColumnRepository relatedTableColumnRepository;

    private final RelatedTableColumnMapper relatedTableColumnMapper;

    public RelatedTableColumnServiceImpl(
        RelatedTableColumnRepository relatedTableColumnRepository,
        RelatedTableColumnMapper relatedTableColumnMapper
    ) {
        this.relatedTableColumnRepository = relatedTableColumnRepository;
        this.relatedTableColumnMapper = relatedTableColumnMapper;
    }

    @Override
    public RelatedTableColumnDTO save(RelatedTableColumnDTO relatedTableColumnDTO) {
        log.debug("Request to save RelatedTableColumn : {}", relatedTableColumnDTO);
        RelatedTableColumn relatedTableColumn = relatedTableColumnMapper.toEntity(relatedTableColumnDTO);
        relatedTableColumn = relatedTableColumnRepository.save(relatedTableColumn);
        return relatedTableColumnMapper.toDto(relatedTableColumn);
    }

    @Override
    public Optional<RelatedTableColumnDTO> partialUpdate(RelatedTableColumnDTO relatedTableColumnDTO) {
        log.debug("Request to partially update RelatedTableColumn : {}", relatedTableColumnDTO);

        return relatedTableColumnRepository
            .findById(relatedTableColumnDTO.getId())
            .map(
                existingRelatedTableColumn -> {
                    relatedTableColumnMapper.partialUpdate(existingRelatedTableColumn, relatedTableColumnDTO);

                    return existingRelatedTableColumn;
                }
            )
            .map(relatedTableColumnRepository::save)
            .map(relatedTableColumnMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RelatedTableColumnDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RelatedTableColumns");
        return relatedTableColumnRepository.findAll(pageable).map(relatedTableColumnMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RelatedTableColumnDTO> findOne(Long id) {
        log.debug("Request to get RelatedTableColumn : {}", id);
        return relatedTableColumnRepository.findById(id).map(relatedTableColumnMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RelatedTableColumn : {}", id);
        relatedTableColumnRepository.deleteById(id);
    }
}
