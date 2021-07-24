package com.dataely.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnalyzerRecognizersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnalyzerRecognizersDTO.class);
        AnalyzerRecognizersDTO analyzerRecognizersDTO1 = new AnalyzerRecognizersDTO();
        analyzerRecognizersDTO1.setId(1L);
        AnalyzerRecognizersDTO analyzerRecognizersDTO2 = new AnalyzerRecognizersDTO();
        assertThat(analyzerRecognizersDTO1).isNotEqualTo(analyzerRecognizersDTO2);
        analyzerRecognizersDTO2.setId(analyzerRecognizersDTO1.getId());
        assertThat(analyzerRecognizersDTO1).isEqualTo(analyzerRecognizersDTO2);
        analyzerRecognizersDTO2.setId(2L);
        assertThat(analyzerRecognizersDTO1).isNotEqualTo(analyzerRecognizersDTO2);
        analyzerRecognizersDTO1.setId(null);
        assertThat(analyzerRecognizersDTO1).isNotEqualTo(analyzerRecognizersDTO2);
    }
}
