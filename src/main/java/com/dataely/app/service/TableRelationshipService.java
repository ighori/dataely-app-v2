package com.dataely.app.service;

import com.dataely.app.service.dto.TableRelationshipDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.dataely.app.domain.TableRelationship}.
 */
public interface TableRelationshipService {
    /**
     * Save a tableRelationship.
     *
     * @param tableRelationshipDTO the entity to save.
     * @return the persisted entity.
     */
    TableRelationshipDTO save(TableRelationshipDTO tableRelationshipDTO);

    /**
     * Partially updates a tableRelationship.
     *
     * @param tableRelationshipDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TableRelationshipDTO> partialUpdate(TableRelationshipDTO tableRelationshipDTO);

    /**
     * Get all the tableRelationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TableRelationshipDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tableRelationship.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TableRelationshipDTO> findOne(Long id);

    /**
     * Delete the "id" tableRelationship.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
