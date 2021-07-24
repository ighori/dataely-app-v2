package com.dataely.app.service;

import com.dataely.app.service.dto.AnalyzerRecognizersDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.dataely.app.domain.AnalyzerRecognizers}.
 */
public interface AnalyzerRecognizersService {
    /**
     * Save a analyzerRecognizers.
     *
     * @param analyzerRecognizersDTO the entity to save.
     * @return the persisted entity.
     */
    AnalyzerRecognizersDTO save(AnalyzerRecognizersDTO analyzerRecognizersDTO);

    /**
     * Partially updates a analyzerRecognizers.
     *
     * @param analyzerRecognizersDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnalyzerRecognizersDTO> partialUpdate(AnalyzerRecognizersDTO analyzerRecognizersDTO);

    /**
     * Get all the analyzerRecognizers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnalyzerRecognizersDTO> findAll(Pageable pageable);

    /**
     * Get the "id" analyzerRecognizers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnalyzerRecognizersDTO> findOne(Long id);

    /**
     * Delete the "id" analyzerRecognizers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
