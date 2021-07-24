package com.dataely.app.service;

import com.dataely.app.service.dto.TablesDefinitionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.dataely.app.domain.TablesDefinition}.
 */
public interface TablesDefinitionService {
    /**
     * Save a tablesDefinition.
     *
     * @param tablesDefinitionDTO the entity to save.
     * @return the persisted entity.
     */
    TablesDefinitionDTO save(TablesDefinitionDTO tablesDefinitionDTO);

    /**
     * Partially updates a tablesDefinition.
     *
     * @param tablesDefinitionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TablesDefinitionDTO> partialUpdate(TablesDefinitionDTO tablesDefinitionDTO);

    /**
     * Get all the tablesDefinitions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TablesDefinitionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tablesDefinition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TablesDefinitionDTO> findOne(Long id);

    /**
     * Delete the "id" tablesDefinition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
