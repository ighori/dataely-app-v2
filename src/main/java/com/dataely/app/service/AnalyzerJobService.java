package com.dataely.app.service;

import com.dataely.app.service.dto.AnalyzerJobDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.dataely.app.domain.AnalyzerJob}.
 */
public interface AnalyzerJobService {
    /**
     * Save a analyzerJob.
     *
     * @param analyzerJobDTO the entity to save.
     * @return the persisted entity.
     */
    AnalyzerJobDTO save(AnalyzerJobDTO analyzerJobDTO);

    /**
     * Partially updates a analyzerJob.
     *
     * @param analyzerJobDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnalyzerJobDTO> partialUpdate(AnalyzerJobDTO analyzerJobDTO);

    /**
     * Get all the analyzerJobs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnalyzerJobDTO> findAll(Pageable pageable);

    /**
     * Get the "id" analyzerJob.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnalyzerJobDTO> findOne(Long id);

    /**
     * Delete the "id" analyzerJob.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
