package com.dataely.app.service;

import com.dataely.app.service.dto.AnalyzerEntitiesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.dataely.app.domain.AnalyzerEntities}.
 */
public interface AnalyzerEntitiesService {
    /**
     * Save a analyzerEntities.
     *
     * @param analyzerEntitiesDTO the entity to save.
     * @return the persisted entity.
     */
    AnalyzerEntitiesDTO save(AnalyzerEntitiesDTO analyzerEntitiesDTO);

    /**
     * Partially updates a analyzerEntities.
     *
     * @param analyzerEntitiesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnalyzerEntitiesDTO> partialUpdate(AnalyzerEntitiesDTO analyzerEntitiesDTO);

    /**
     * Get all the analyzerEntities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnalyzerEntitiesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" analyzerEntities.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnalyzerEntitiesDTO> findOne(Long id);

    /**
     * Delete the "id" analyzerEntities.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
