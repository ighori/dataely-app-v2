package com.dataely.app.service.impl;

import com.dataely.app.domain.AnalyzerResult;
import com.dataely.app.repository.AnalyzerResultRepository;
import com.dataely.app.service.AnalyzerResultService;
import com.dataely.app.service.dto.AnalyzerResultDTO;
import com.dataely.app.service.mapper.AnalyzerResultMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnalyzerResult}.
 */
@Service
@Transactional
public class AnalyzerResultServiceImpl implements AnalyzerResultService {

    private final Logger log = LoggerFactory.getLogger(AnalyzerResultServiceImpl.class);

    private final AnalyzerResultRepository analyzerResultRepository;

    private final AnalyzerResultMapper analyzerResultMapper;

    public AnalyzerResultServiceImpl(AnalyzerResultRepository analyzerResultRepository, AnalyzerResultMapper analyzerResultMapper) {
        this.analyzerResultRepository = analyzerResultRepository;
        this.analyzerResultMapper = analyzerResultMapper;
    }

    @Override
    public AnalyzerResultDTO save(AnalyzerResultDTO analyzerResultDTO) {
        log.debug("Request to save AnalyzerResult : {}", analyzerResultDTO);
        AnalyzerResult analyzerResult = analyzerResultMapper.toEntity(analyzerResultDTO);
        analyzerResult = analyzerResultRepository.save(analyzerResult);
        return analyzerResultMapper.toDto(analyzerResult);
    }

    @Override
    public Optional<AnalyzerResultDTO> partialUpdate(AnalyzerResultDTO analyzerResultDTO) {
        log.debug("Request to partially update AnalyzerResult : {}", analyzerResultDTO);

        return analyzerResultRepository
            .findById(analyzerResultDTO.getId())
            .map(
                existingAnalyzerResult -> {
                    analyzerResultMapper.partialUpdate(existingAnalyzerResult, analyzerResultDTO);

                    return existingAnalyzerResult;
                }
            )
            .map(analyzerResultRepository::save)
            .map(analyzerResultMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnalyzerResultDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnalyzerResults");
        return analyzerResultRepository.findAll(pageable).map(analyzerResultMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnalyzerResultDTO> findOne(Long id) {
        log.debug("Request to get AnalyzerResult : {}", id);
        return analyzerResultRepository.findById(id).map(analyzerResultMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnalyzerResult : {}", id);
        analyzerResultRepository.deleteById(id);
    }
}
