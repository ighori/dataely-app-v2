package com.dataely.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TableColumnMapperTest {

    private TableColumnMapper tableColumnMapper;

    @BeforeEach
    public void setUp() {
        tableColumnMapper = new TableColumnMapperImpl();
    }
}
