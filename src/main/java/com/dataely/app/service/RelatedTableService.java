package com.dataely.app.service;

import com.dataely.app.service.dto.RelatedTableDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.dataely.app.domain.RelatedTable}.
 */
public interface RelatedTableService {
    /**
     * Save a relatedTable.
     *
     * @param relatedTableDTO the entity to save.
     * @return the persisted entity.
     */
    RelatedTableDTO save(RelatedTableDTO relatedTableDTO);

    /**
     * Partially updates a relatedTable.
     *
     * @param relatedTableDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RelatedTableDTO> partialUpdate(RelatedTableDTO relatedTableDTO);

    /**
     * Get all the relatedTables.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RelatedTableDTO> findAll(Pageable pageable);

    /**
     * Get the "id" relatedTable.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RelatedTableDTO> findOne(Long id);

    /**
     * Delete the "id" relatedTable.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
