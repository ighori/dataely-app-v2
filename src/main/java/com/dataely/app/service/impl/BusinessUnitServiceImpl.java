package com.dataely.app.service.impl;

import com.dataely.app.domain.BusinessUnit;
import com.dataely.app.repository.BusinessUnitRepository;
import com.dataely.app.service.BusinessUnitService;
import com.dataely.app.service.dto.BusinessUnitDTO;
import com.dataely.app.service.mapper.BusinessUnitMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BusinessUnit}.
 */
@Service
@Transactional
public class BusinessUnitServiceImpl implements BusinessUnitService {

    private final Logger log = LoggerFactory.getLogger(BusinessUnitServiceImpl.class);

    private final BusinessUnitRepository businessUnitRepository;

    private final BusinessUnitMapper businessUnitMapper;

    public BusinessUnitServiceImpl(BusinessUnitRepository businessUnitRepository, BusinessUnitMapper businessUnitMapper) {
        this.businessUnitRepository = businessUnitRepository;
        this.businessUnitMapper = businessUnitMapper;
    }

    @Override
    public BusinessUnitDTO save(BusinessUnitDTO businessUnitDTO) {
        log.debug("Request to save BusinessUnit : {}", businessUnitDTO);
        BusinessUnit businessUnit = businessUnitMapper.toEntity(businessUnitDTO);
        businessUnit = businessUnitRepository.save(businessUnit);
        return businessUnitMapper.toDto(businessUnit);
    }

    @Override
    public Optional<BusinessUnitDTO> partialUpdate(BusinessUnitDTO businessUnitDTO) {
        log.debug("Request to partially update BusinessUnit : {}", businessUnitDTO);

        return businessUnitRepository
            .findById(businessUnitDTO.getId())
            .map(
                existingBusinessUnit -> {
                    businessUnitMapper.partialUpdate(existingBusinessUnit, businessUnitDTO);

                    return existingBusinessUnit;
                }
            )
            .map(businessUnitRepository::save)
            .map(businessUnitMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BusinessUnitDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BusinessUnits");
        return businessUnitRepository.findAll(pageable).map(businessUnitMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BusinessUnitDTO> findOne(Long id) {
        log.debug("Request to get BusinessUnit : {}", id);
        return businessUnitRepository.findById(id).map(businessUnitMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BusinessUnit : {}", id);
        businessUnitRepository.deleteById(id);
    }
}
