package com.dataely.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DsSchemaMapperTest {

    private DsSchemaMapper dsSchemaMapper;

    @BeforeEach
    public void setUp() {
        dsSchemaMapper = new DsSchemaMapperImpl();
    }
}
