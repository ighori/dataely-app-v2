package com.dataely.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnalyzerEntitiesMapperTest {

    private AnalyzerEntitiesMapper analyzerEntitiesMapper;

    @BeforeEach
    public void setUp() {
        analyzerEntitiesMapper = new AnalyzerEntitiesMapperImpl();
    }
}
