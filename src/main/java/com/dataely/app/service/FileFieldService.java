package com.dataely.app.service;

import com.dataely.app.service.dto.FileFieldDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.dataely.app.domain.FileField}.
 */
public interface FileFieldService {
    /**
     * Save a fileField.
     *
     * @param fileFieldDTO the entity to save.
     * @return the persisted entity.
     */
    FileFieldDTO save(FileFieldDTO fileFieldDTO);

    /**
     * Partially updates a fileField.
     *
     * @param fileFieldDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FileFieldDTO> partialUpdate(FileFieldDTO fileFieldDTO);

    /**
     * Get all the fileFields.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FileFieldDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fileField.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FileFieldDTO> findOne(Long id);

    /**
     * Delete the "id" fileField.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
