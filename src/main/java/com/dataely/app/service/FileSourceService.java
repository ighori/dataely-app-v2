package com.dataely.app.service;

import com.dataely.app.service.dto.FileSourceDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.dataely.app.domain.FileSource}.
 */
public interface FileSourceService {
    /**
     * Save a fileSource.
     *
     * @param fileSourceDTO the entity to save.
     * @return the persisted entity.
     */
    FileSourceDTO save(FileSourceDTO fileSourceDTO);

    /**
     * Partially updates a fileSource.
     *
     * @param fileSourceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FileSourceDTO> partialUpdate(FileSourceDTO fileSourceDTO);

    /**
     * Get all the fileSources.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FileSourceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fileSource.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FileSourceDTO> findOne(Long id);

    /**
     * Delete the "id" fileSource.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
