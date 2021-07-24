package com.dataely.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnalyzerAdHocRecognizersMapperTest {

    private AnalyzerAdHocRecognizersMapper analyzerAdHocRecognizersMapper;

    @BeforeEach
    public void setUp() {
        analyzerAdHocRecognizersMapper = new AnalyzerAdHocRecognizersMapperImpl();
    }
}
