package com.dataely.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnalyzerAdHocRecognizersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnalyzerAdHocRecognizersDTO.class);
        AnalyzerAdHocRecognizersDTO analyzerAdHocRecognizersDTO1 = new AnalyzerAdHocRecognizersDTO();
        analyzerAdHocRecognizersDTO1.setId(1L);
        AnalyzerAdHocRecognizersDTO analyzerAdHocRecognizersDTO2 = new AnalyzerAdHocRecognizersDTO();
        assertThat(analyzerAdHocRecognizersDTO1).isNotEqualTo(analyzerAdHocRecognizersDTO2);
        analyzerAdHocRecognizersDTO2.setId(analyzerAdHocRecognizersDTO1.getId());
        assertThat(analyzerAdHocRecognizersDTO1).isEqualTo(analyzerAdHocRecognizersDTO2);
        analyzerAdHocRecognizersDTO2.setId(2L);
        assertThat(analyzerAdHocRecognizersDTO1).isNotEqualTo(analyzerAdHocRecognizersDTO2);
        analyzerAdHocRecognizersDTO1.setId(null);
        assertThat(analyzerAdHocRecognizersDTO1).isNotEqualTo(analyzerAdHocRecognizersDTO2);
    }
}
