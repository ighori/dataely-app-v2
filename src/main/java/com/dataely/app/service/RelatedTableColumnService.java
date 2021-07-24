package com.dataely.app.service;

import com.dataely.app.service.dto.RelatedTableColumnDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.dataely.app.domain.RelatedTableColumn}.
 */
public interface RelatedTableColumnService {
    /**
     * Save a relatedTableColumn.
     *
     * @param relatedTableColumnDTO the entity to save.
     * @return the persisted entity.
     */
    RelatedTableColumnDTO save(RelatedTableColumnDTO relatedTableColumnDTO);

    /**
     * Partially updates a relatedTableColumn.
     *
     * @param relatedTableColumnDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RelatedTableColumnDTO> partialUpdate(RelatedTableColumnDTO relatedTableColumnDTO);

    /**
     * Get all the relatedTableColumns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RelatedTableColumnDTO> findAll(Pageable pageable);

    /**
     * Get the "id" relatedTableColumn.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RelatedTableColumnDTO> findOne(Long id);

    /**
     * Delete the "id" relatedTableColumn.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
