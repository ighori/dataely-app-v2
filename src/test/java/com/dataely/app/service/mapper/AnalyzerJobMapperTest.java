package com.dataely.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnalyzerJobMapperTest {

    private AnalyzerJobMapper analyzerJobMapper;

    @BeforeEach
    public void setUp() {
        analyzerJobMapper = new AnalyzerJobMapperImpl();
    }
}
