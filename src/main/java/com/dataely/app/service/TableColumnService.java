package com.dataely.app.service;

import com.dataely.app.service.dto.TableColumnDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.dataely.app.domain.TableColumn}.
 */
public interface TableColumnService {
    /**
     * Save a tableColumn.
     *
     * @param tableColumnDTO the entity to save.
     * @return the persisted entity.
     */
    TableColumnDTO save(TableColumnDTO tableColumnDTO);

    /**
     * Partially updates a tableColumn.
     *
     * @param tableColumnDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TableColumnDTO> partialUpdate(TableColumnDTO tableColumnDTO);

    /**
     * Get all the tableColumns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TableColumnDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tableColumn.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TableColumnDTO> findOne(Long id);

    /**
     * Delete the "id" tableColumn.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
