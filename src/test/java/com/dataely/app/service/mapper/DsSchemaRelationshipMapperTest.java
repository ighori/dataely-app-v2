package com.dataely.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DsSchemaRelationshipMapperTest {

    private DsSchemaRelationshipMapper dsSchemaRelationshipMapper;

    @BeforeEach
    public void setUp() {
        dsSchemaRelationshipMapper = new DsSchemaRelationshipMapperImpl();
    }
}
