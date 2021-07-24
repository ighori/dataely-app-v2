package com.dataely.app.service;

import com.dataely.app.service.dto.AnalyzerResultDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.dataely.app.domain.AnalyzerResult}.
 */
public interface AnalyzerResultService {
    /**
     * Save a analyzerResult.
     *
     * @param analyzerResultDTO the entity to save.
     * @return the persisted entity.
     */
    AnalyzerResultDTO save(AnalyzerResultDTO analyzerResultDTO);

    /**
     * Partially updates a analyzerResult.
     *
     * @param analyzerResultDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnalyzerResultDTO> partialUpdate(AnalyzerResultDTO analyzerResultDTO);

    /**
     * Get all the analyzerResults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnalyzerResultDTO> findAll(Pageable pageable);

    /**
     * Get the "id" analyzerResult.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnalyzerResultDTO> findOne(Long id);

    /**
     * Delete the "id" analyzerResult.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
