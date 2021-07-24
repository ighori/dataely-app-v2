package com.dataely.app.service.impl;

import com.dataely.app.domain.AnalyzerEntities;
import com.dataely.app.repository.AnalyzerEntitiesRepository;
import com.dataely.app.service.AnalyzerEntitiesService;
import com.dataely.app.service.dto.AnalyzerEntitiesDTO;
import com.dataely.app.service.mapper.AnalyzerEntitiesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnalyzerEntities}.
 */
@Service
@Transactional
public class AnalyzerEntitiesServiceImpl implements AnalyzerEntitiesService {

    private final Logger log = LoggerFactory.getLogger(AnalyzerEntitiesServiceImpl.class);

    private final AnalyzerEntitiesRepository analyzerEntitiesRepository;

    private final AnalyzerEntitiesMapper analyzerEntitiesMapper;

    public AnalyzerEntitiesServiceImpl(
        AnalyzerEntitiesRepository analyzerEntitiesRepository,
        AnalyzerEntitiesMapper analyzerEntitiesMapper
    ) {
        this.analyzerEntitiesRepository = analyzerEntitiesRepository;
        this.analyzerEntitiesMapper = analyzerEntitiesMapper;
    }

    @Override
    public AnalyzerEntitiesDTO save(AnalyzerEntitiesDTO analyzerEntitiesDTO) {
        log.debug("Request to save AnalyzerEntities : {}", analyzerEntitiesDTO);
        AnalyzerEntities analyzerEntities = analyzerEntitiesMapper.toEntity(analyzerEntitiesDTO);
        analyzerEntities = analyzerEntitiesRepository.save(analyzerEntities);
        return analyzerEntitiesMapper.toDto(analyzerEntities);
    }

    @Override
    public Optional<AnalyzerEntitiesDTO> partialUpdate(AnalyzerEntitiesDTO analyzerEntitiesDTO) {
        log.debug("Request to partially update AnalyzerEntities : {}", analyzerEntitiesDTO);

        return analyzerEntitiesRepository
            .findById(analyzerEntitiesDTO.getId())
            .map(
                existingAnalyzerEntities -> {
                    analyzerEntitiesMapper.partialUpdate(existingAnalyzerEntities, analyzerEntitiesDTO);

                    return existingAnalyzerEntities;
                }
            )
            .map(analyzerEntitiesRepository::save)
            .map(analyzerEntitiesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnalyzerEntitiesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnalyzerEntities");
        return analyzerEntitiesRepository.findAll(pageable).map(analyzerEntitiesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnalyzerEntitiesDTO> findOne(Long id) {
        log.debug("Request to get AnalyzerEntities : {}", id);
        return analyzerEntitiesRepository.findById(id).map(analyzerEntitiesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnalyzerEntities : {}", id);
        analyzerEntitiesRepository.deleteById(id);
    }
}
