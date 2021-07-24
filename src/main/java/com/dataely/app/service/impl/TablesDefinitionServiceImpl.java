package com.dataely.app.service.impl;

import com.dataely.app.domain.TablesDefinition;
import com.dataely.app.repository.TablesDefinitionRepository;
import com.dataely.app.service.TablesDefinitionService;
import com.dataely.app.service.dto.TablesDefinitionDTO;
import com.dataely.app.service.mapper.TablesDefinitionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TablesDefinition}.
 */
@Service
@Transactional
public class TablesDefinitionServiceImpl implements TablesDefinitionService {

    private final Logger log = LoggerFactory.getLogger(TablesDefinitionServiceImpl.class);

    private final TablesDefinitionRepository tablesDefinitionRepository;

    private final TablesDefinitionMapper tablesDefinitionMapper;

    public TablesDefinitionServiceImpl(
        TablesDefinitionRepository tablesDefinitionRepository,
        TablesDefinitionMapper tablesDefinitionMapper
    ) {
        this.tablesDefinitionRepository = tablesDefinitionRepository;
        this.tablesDefinitionMapper = tablesDefinitionMapper;
    }

    @Override
    public TablesDefinitionDTO save(TablesDefinitionDTO tablesDefinitionDTO) {
        log.debug("Request to save TablesDefinition : {}", tablesDefinitionDTO);
        TablesDefinition tablesDefinition = tablesDefinitionMapper.toEntity(tablesDefinitionDTO);
        tablesDefinition = tablesDefinitionRepository.save(tablesDefinition);
        return tablesDefinitionMapper.toDto(tablesDefinition);
    }

    @Override
    public Optional<TablesDefinitionDTO> partialUpdate(TablesDefinitionDTO tablesDefinitionDTO) {
        log.debug("Request to partially update TablesDefinition : {}", tablesDefinitionDTO);

        return tablesDefinitionRepository
            .findById(tablesDefinitionDTO.getId())
            .map(
                existingTablesDefinition -> {
                    tablesDefinitionMapper.partialUpdate(existingTablesDefinition, tablesDefinitionDTO);

                    return existingTablesDefinition;
                }
            )
            .map(tablesDefinitionRepository::save)
            .map(tablesDefinitionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TablesDefinitionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TablesDefinitions");
        return tablesDefinitionRepository.findAll(pageable).map(tablesDefinitionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TablesDefinitionDTO> findOne(Long id) {
        log.debug("Request to get TablesDefinition : {}", id);
        return tablesDefinitionRepository.findById(id).map(tablesDefinitionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TablesDefinition : {}", id);
        tablesDefinitionRepository.deleteById(id);
    }
}
