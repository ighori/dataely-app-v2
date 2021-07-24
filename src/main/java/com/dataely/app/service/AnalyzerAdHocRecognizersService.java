package com.dataely.app.service;

import com.dataely.app.service.dto.AnalyzerAdHocRecognizersDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.dataely.app.domain.AnalyzerAdHocRecognizers}.
 */
public interface AnalyzerAdHocRecognizersService {
    /**
     * Save a analyzerAdHocRecognizers.
     *
     * @param analyzerAdHocRecognizersDTO the entity to save.
     * @return the persisted entity.
     */
    AnalyzerAdHocRecognizersDTO save(AnalyzerAdHocRecognizersDTO analyzerAdHocRecognizersDTO);

    /**
     * Partially updates a analyzerAdHocRecognizers.
     *
     * @param analyzerAdHocRecognizersDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnalyzerAdHocRecognizersDTO> partialUpdate(AnalyzerAdHocRecognizersDTO analyzerAdHocRecognizersDTO);

    /**
     * Get all the analyzerAdHocRecognizers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnalyzerAdHocRecognizersDTO> findAll(Pageable pageable);

    /**
     * Get the "id" analyzerAdHocRecognizers.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnalyzerAdHocRecognizersDTO> findOne(Long id);

    /**
     * Delete the "id" analyzerAdHocRecognizers.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
