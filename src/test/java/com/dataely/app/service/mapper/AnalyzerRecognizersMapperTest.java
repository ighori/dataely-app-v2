package com.dataely.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnalyzerRecognizersMapperTest {

    private AnalyzerRecognizersMapper analyzerRecognizersMapper;

    @BeforeEach
    public void setUp() {
        analyzerRecognizersMapper = new AnalyzerRecognizersMapperImpl();
    }
}
