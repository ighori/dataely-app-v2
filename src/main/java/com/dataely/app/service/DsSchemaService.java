package com.dataely.app.service;

import com.dataely.app.service.dto.DsSchemaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.dataely.app.domain.DsSchema}.
 */
public interface DsSchemaService {
    /**
     * Save a dsSchema.
     *
     * @param dsSchemaDTO the entity to save.
     * @return the persisted entity.
     */
    DsSchemaDTO save(DsSchemaDTO dsSchemaDTO);

    /**
     * Partially updates a dsSchema.
     *
     * @param dsSchemaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DsSchemaDTO> partialUpdate(DsSchemaDTO dsSchemaDTO);

    /**
     * Get all the dsSchemas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DsSchemaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" dsSchema.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DsSchemaDTO> findOne(Long id);

    /**
     * Delete the "id" dsSchema.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
