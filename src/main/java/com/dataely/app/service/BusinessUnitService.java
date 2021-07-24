package com.dataely.app.service;

import com.dataely.app.service.dto.BusinessUnitDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.dataely.app.domain.BusinessUnit}.
 */
public interface BusinessUnitService {
    /**
     * Save a businessUnit.
     *
     * @param businessUnitDTO the entity to save.
     * @return the persisted entity.
     */
    BusinessUnitDTO save(BusinessUnitDTO businessUnitDTO);

    /**
     * Partially updates a businessUnit.
     *
     * @param businessUnitDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BusinessUnitDTO> partialUpdate(BusinessUnitDTO businessUnitDTO);

    /**
     * Get all the businessUnits.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BusinessUnitDTO> findAll(Pageable pageable);

    /**
     * Get the "id" businessUnit.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BusinessUnitDTO> findOne(Long id);

    /**
     * Delete the "id" businessUnit.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
