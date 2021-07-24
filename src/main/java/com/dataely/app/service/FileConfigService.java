package com.dataely.app.service;

import com.dataely.app.service.dto.FileConfigDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.dataely.app.domain.FileConfig}.
 */
public interface FileConfigService {
    /**
     * Save a fileConfig.
     *
     * @param fileConfigDTO the entity to save.
     * @return the persisted entity.
     */
    FileConfigDTO save(FileConfigDTO fileConfigDTO);

    /**
     * Partially updates a fileConfig.
     *
     * @param fileConfigDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FileConfigDTO> partialUpdate(FileConfigDTO fileConfigDTO);

    /**
     * Get all the fileConfigs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FileConfigDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fileConfig.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FileConfigDTO> findOne(Long id);

    /**
     * Delete the "id" fileConfig.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
