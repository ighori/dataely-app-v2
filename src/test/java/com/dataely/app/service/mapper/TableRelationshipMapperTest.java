package com.dataely.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TableRelationshipMapperTest {

    private TableRelationshipMapper tableRelationshipMapper;

    @BeforeEach
    public void setUp() {
        tableRelationshipMapper = new TableRelationshipMapperImpl();
    }
}
