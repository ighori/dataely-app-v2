package com.dataely.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TablesDefinitionMapperTest {

    private TablesDefinitionMapper tablesDefinitionMapper;

    @BeforeEach
    public void setUp() {
        tablesDefinitionMapper = new TablesDefinitionMapperImpl();
    }
}
