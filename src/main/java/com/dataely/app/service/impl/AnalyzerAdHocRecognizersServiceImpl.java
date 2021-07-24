package com.dataely.app.service.impl;

import com.dataely.app.domain.AnalyzerAdHocRecognizers;
import com.dataely.app.repository.AnalyzerAdHocRecognizersRepository;
import com.dataely.app.service.AnalyzerAdHocRecognizersService;
import com.dataely.app.service.dto.AnalyzerAdHocRecognizersDTO;
import com.dataely.app.service.mapper.AnalyzerAdHocRecognizersMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnalyzerAdHocRecognizers}.
 */
@Service
@Transactional
public class AnalyzerAdHocRecognizersServiceImpl implements AnalyzerAdHocRecognizersService {

    private final Logger log = LoggerFactory.getLogger(AnalyzerAdHocRecognizersServiceImpl.class);

    private final AnalyzerAdHocRecognizersRepository analyzerAdHocRecognizersRepository;

    private final AnalyzerAdHocRecognizersMapper analyzerAdHocRecognizersMapper;

    public AnalyzerAdHocRecognizersServiceImpl(
        AnalyzerAdHocRecognizersRepository analyzerAdHocRecognizersRepository,
        AnalyzerAdHocRecognizersMapper analyzerAdHocRecognizersMapper
    ) {
        this.analyzerAdHocRecognizersRepository = analyzerAdHocRecognizersRepository;
        this.analyzerAdHocRecognizersMapper = analyzerAdHocRecognizersMapper;
    }

    @Override
    public AnalyzerAdHocRecognizersDTO save(AnalyzerAdHocRecognizersDTO analyzerAdHocRecognizersDTO) {
        log.debug("Request to save AnalyzerAdHocRecognizers : {}", analyzerAdHocRecognizersDTO);
        AnalyzerAdHocRecognizers analyzerAdHocRecognizers = analyzerAdHocRecognizersMapper.toEntity(analyzerAdHocRecognizersDTO);
        analyzerAdHocRecognizers = analyzerAdHocRecognizersRepository.save(analyzerAdHocRecognizers);
        return analyzerAdHocRecognizersMapper.toDto(analyzerAdHocRecognizers);
    }

    @Override
    public Optional<AnalyzerAdHocRecognizersDTO> partialUpdate(AnalyzerAdHocRecognizersDTO analyzerAdHocRecognizersDTO) {
        log.debug("Request to partially update AnalyzerAdHocRecognizers : {}", analyzerAdHocRecognizersDTO);

        return analyzerAdHocRecognizersRepository
            .findById(analyzerAdHocRecognizersDTO.getId())
            .map(
                existingAnalyzerAdHocRecognizers -> {
                    analyzerAdHocRecognizersMapper.partialUpdate(existingAnalyzerAdHocRecognizers, analyzerAdHocRecognizersDTO);

                    return existingAnalyzerAdHocRecognizers;
                }
            )
            .map(analyzerAdHocRecognizersRepository::save)
            .map(analyzerAdHocRecognizersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnalyzerAdHocRecognizersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnalyzerAdHocRecognizers");
        return analyzerAdHocRecognizersRepository.findAll(pageable).map(analyzerAdHocRecognizersMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnalyzerAdHocRecognizersDTO> findOne(Long id) {
        log.debug("Request to get AnalyzerAdHocRecognizers : {}", id);
        return analyzerAdHocRecognizersRepository.findById(id).map(analyzerAdHocRecognizersMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnalyzerAdHocRecognizers : {}", id);
        analyzerAdHocRecognizersRepository.deleteById(id);
    }
}
