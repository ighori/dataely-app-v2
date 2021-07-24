package com.dataely.app.service.impl;

import com.dataely.app.domain.AnalyzerJob;
import com.dataely.app.repository.AnalyzerJobRepository;
import com.dataely.app.service.AnalyzerJobService;
import com.dataely.app.service.dto.AnalyzerJobDTO;
import com.dataely.app.service.mapper.AnalyzerJobMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnalyzerJob}.
 */
@Service
@Transactional
public class AnalyzerJobServiceImpl implements AnalyzerJobService {

    private final Logger log = LoggerFactory.getLogger(AnalyzerJobServiceImpl.class);

    private final AnalyzerJobRepository analyzerJobRepository;

    private final AnalyzerJobMapper analyzerJobMapper;

    public AnalyzerJobServiceImpl(AnalyzerJobRepository analyzerJobRepository, AnalyzerJobMapper analyzerJobMapper) {
        this.analyzerJobRepository = analyzerJobRepository;
        this.analyzerJobMapper = analyzerJobMapper;
    }

    @Override
    public AnalyzerJobDTO save(AnalyzerJobDTO analyzerJobDTO) {
        log.debug("Request to save AnalyzerJob : {}", analyzerJobDTO);
        AnalyzerJob analyzerJob = analyzerJobMapper.toEntity(analyzerJobDTO);
        analyzerJob = analyzerJobRepository.save(analyzerJob);
        return analyzerJobMapper.toDto(analyzerJob);
    }

    @Override
    public Optional<AnalyzerJobDTO> partialUpdate(AnalyzerJobDTO analyzerJobDTO) {
        log.debug("Request to partially update AnalyzerJob : {}", analyzerJobDTO);

        return analyzerJobRepository
            .findById(analyzerJobDTO.getId())
            .map(
                existingAnalyzerJob -> {
                    analyzerJobMapper.partialUpdate(existingAnalyzerJob, analyzerJobDTO);

                    return existingAnalyzerJob;
                }
            )
            .map(analyzerJobRepository::save)
            .map(analyzerJobMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnalyzerJobDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnalyzerJobs");
        return analyzerJobRepository.findAll(pageable).map(analyzerJobMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnalyzerJobDTO> findOne(Long id) {
        log.debug("Request to get AnalyzerJob : {}", id);
        return analyzerJobRepository.findById(id).map(analyzerJobMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnalyzerJob : {}", id);
        analyzerJobRepository.deleteById(id);
    }
}
