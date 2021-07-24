package com.dataely.app.service;

import com.dataely.app.service.dto.DsSchemaRelationshipDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.dataely.app.domain.DsSchemaRelationship}.
 */
public interface DsSchemaRelationshipService {
    /**
     * Save a dsSchemaRelationship.
     *
     * @param dsSchemaRelationshipDTO the entity to save.
     * @return the persisted entity.
     */
    DsSchemaRelationshipDTO save(DsSchemaRelationshipDTO dsSchemaRelationshipDTO);

    /**
     * Partially updates a dsSchemaRelationship.
     *
     * @param dsSchemaRelationshipDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DsSchemaRelationshipDTO> partialUpdate(DsSchemaRelationshipDTO dsSchemaRelationshipDTO);

    /**
     * Get all the dsSchemaRelationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DsSchemaRelationshipDTO> findAll(Pageable pageable);

    /**
     * Get the "id" dsSchemaRelationship.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DsSchemaRelationshipDTO> findOne(Long id);

    /**
     * Delete the "id" dsSchemaRelationship.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
