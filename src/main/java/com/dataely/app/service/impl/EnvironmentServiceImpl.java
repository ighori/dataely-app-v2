package com.dataely.app.service.impl;

import com.dataely.app.domain.Environment;
import com.dataely.app.repository.EnvironmentRepository;
import com.dataely.app.service.EnvironmentService;
import com.dataely.app.service.dto.EnvironmentDTO;
import com.dataely.app.service.mapper.EnvironmentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Environment}.
 */
@Service
@Transactional
public class EnvironmentServiceImpl implements EnvironmentService {

    private final Logger log = LoggerFactory.getLogger(EnvironmentServiceImpl.class);

    private final EnvironmentRepository environmentRepository;

    private final EnvironmentMapper environmentMapper;

    public EnvironmentServiceImpl(EnvironmentRepository environmentRepository, EnvironmentMapper environmentMapper) {
        this.environmentRepository = environmentRepository;
        this.environmentMapper = environmentMapper;
    }

    @Override
    public EnvironmentDTO save(EnvironmentDTO environmentDTO) {
        log.debug("Request to save Environment : {}", environmentDTO);
        Environment environment = environmentMapper.toEntity(environmentDTO);
        environment = environmentRepository.save(environment);
        return environmentMapper.toDto(environment);
    }

    @Override
    public Optional<EnvironmentDTO> partialUpdate(EnvironmentDTO environmentDTO) {
        log.debug("Request to partially update Environment : {}", environmentDTO);

        return environmentRepository
            .findById(environmentDTO.getId())
            .map(
                existingEnvironment -> {
                    environmentMapper.partialUpdate(existingEnvironment, environmentDTO);

                    return existingEnvironment;
                }
            )
            .map(environmentRepository::save)
            .map(environmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EnvironmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Environments");
        return environmentRepository.findAll(pageable).map(environmentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EnvironmentDTO> findOne(Long id) {
        log.debug("Request to get Environment : {}", id);
        return environmentRepository.findById(id).map(environmentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Environment : {}", id);
        environmentRepository.deleteById(id);
    }
}
