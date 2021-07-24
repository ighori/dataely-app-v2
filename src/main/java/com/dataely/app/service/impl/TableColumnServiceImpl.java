package com.dataely.app.service.impl;

import com.dataely.app.domain.TableColumn;
import com.dataely.app.repository.TableColumnRepository;
import com.dataely.app.service.TableColumnService;
import com.dataely.app.service.dto.TableColumnDTO;
import com.dataely.app.service.mapper.TableColumnMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TableColumn}.
 */
@Service
@Transactional
public class TableColumnServiceImpl implements TableColumnService {

    private final Logger log = LoggerFactory.getLogger(TableColumnServiceImpl.class);

    private final TableColumnRepository tableColumnRepository;

    private final TableColumnMapper tableColumnMapper;

    public TableColumnServiceImpl(TableColumnRepository tableColumnRepository, TableColumnMapper tableColumnMapper) {
        this.tableColumnRepository = tableColumnRepository;
        this.tableColumnMapper = tableColumnMapper;
    }

    @Override
    public TableColumnDTO save(TableColumnDTO tableColumnDTO) {
        log.debug("Request to save TableColumn : {}", tableColumnDTO);
        TableColumn tableColumn = tableColumnMapper.toEntity(tableColumnDTO);
        tableColumn = tableColumnRepository.save(tableColumn);
        return tableColumnMapper.toDto(tableColumn);
    }

    @Override
    public Optional<TableColumnDTO> partialUpdate(TableColumnDTO tableColumnDTO) {
        log.debug("Request to partially update TableColumn : {}", tableColumnDTO);

        return tableColumnRepository
            .findById(tableColumnDTO.getId())
            .map(
                existingTableColumn -> {
                    tableColumnMapper.partialUpdate(existingTableColumn, tableColumnDTO);

                    return existingTableColumn;
                }
            )
            .map(tableColumnRepository::save)
            .map(tableColumnMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TableColumnDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TableColumns");
        return tableColumnRepository.findAll(pageable).map(tableColumnMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TableColumnDTO> findOne(Long id) {
        log.debug("Request to get TableColumn : {}", id);
        return tableColumnRepository.findById(id).map(tableColumnMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TableColumn : {}", id);
        tableColumnRepository.deleteById(id);
    }
}
