package com.dataely.app.service;

import com.dataely.app.service.dto.DataSourceDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.dataely.app.domain.DataSource}.
 */
public interface DataSourceService {
    /**
     * Save a dataSource.
     *
     * @param dataSourceDTO the entity to save.
     * @return the persisted entity.
     */
    DataSourceDTO save(DataSourceDTO dataSourceDTO);

    /**
     * Partially updates a dataSource.
     *
     * @param dataSourceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DataSourceDTO> partialUpdate(DataSourceDTO dataSourceDTO);

    /**
     * Get all the dataSources.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DataSourceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" dataSource.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DataSourceDTO> findOne(Long id);

    /**
     * Delete the "id" dataSource.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
