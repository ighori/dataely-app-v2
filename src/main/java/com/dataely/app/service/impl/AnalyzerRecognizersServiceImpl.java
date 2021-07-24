package com.dataely.app.service.impl;

import com.dataely.app.domain.AnalyzerRecognizers;
import com.dataely.app.repository.AnalyzerRecognizersRepository;
import com.dataely.app.service.AnalyzerRecognizersService;
import com.dataely.app.service.dto.AnalyzerRecognizersDTO;
import com.dataely.app.service.mapper.AnalyzerRecognizersMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnalyzerRecognizers}.
 */
@Service
@Transactional
public class AnalyzerRecognizersServiceImpl implements AnalyzerRecognizersService {

    private final Logger log = LoggerFactory.getLogger(AnalyzerRecognizersServiceImpl.class);

    private final AnalyzerRecognizersRepository analyzerRecognizersRepository;

    private final AnalyzerRecognizersMapper analyzerRecognizersMapper;

    public AnalyzerRecognizersServiceImpl(
        AnalyzerRecognizersRepository analyzerRecognizersRepository,
        AnalyzerRecognizersMapper analyzerRecognizersMapper
    ) {
        this.analyzerRecognizersRepository = analyzerRecognizersRepository;
        this.analyzerRecognizersMapper = analyzerRecognizersMapper;
    }

    @Override
    public AnalyzerRecognizersDTO save(AnalyzerRecognizersDTO analyzerRecognizersDTO) {
        log.debug("Request to save AnalyzerRecognizers : {}", analyzerRecognizersDTO);
        AnalyzerRecognizers analyzerRecognizers = analyzerRecognizersMapper.toEntity(analyzerRecognizersDTO);
        analyzerRecognizers = analyzerRecognizersRepository.save(analyzerRecognizers);
        return analyzerRecognizersMapper.toDto(analyzerRecognizers);
    }

    @Override
    public Optional<AnalyzerRecognizersDTO> partialUpdate(AnalyzerRecognizersDTO analyzerRecognizersDTO) {
        log.debug("Request to partially update AnalyzerRecognizers : {}", analyzerRecognizersDTO);

        return analyzerRecognizersRepository
            .findById(analyzerRecognizersDTO.getId())
            .map(
                existingAnalyzerRecognizers -> {
                    analyzerRecognizersMapper.partialUpdate(existingAnalyzerRecognizers, analyzerRecognizersDTO);

                    return existingAnalyzerRecognizers;
                }
            )
            .map(analyzerRecognizersRepository::save)
            .map(analyzerRecognizersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnalyzerRecognizersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnalyzerRecognizers");
        return analyzerRecognizersRepository.findAll(pageable).map(analyzerRecognizersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnalyzerRecognizersDTO> findOne(Long id) {
        log.debug("Request to get AnalyzerRecognizers : {}", id);
        return analyzerRecognizersRepository.findById(id).map(analyzerRecognizersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnalyzerRecognizers : {}", id);
        analyzerRecognizersRepository.deleteById(id);
    }
}
