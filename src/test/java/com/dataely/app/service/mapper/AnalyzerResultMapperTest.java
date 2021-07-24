package com.dataely.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnalyzerResultMapperTest {

    private AnalyzerResultMapper analyzerResultMapper;

    @BeforeEach
    public void setUp() {
        analyzerResultMapper = new AnalyzerResultMapperImpl();
    }
}
