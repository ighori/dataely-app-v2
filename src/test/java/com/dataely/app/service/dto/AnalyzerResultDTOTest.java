package com.dataely.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnalyzerResultDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnalyzerResultDTO.class);
        AnalyzerResultDTO analyzerResultDTO1 = new AnalyzerResultDTO();
        analyzerResultDTO1.setId(1L);
        AnalyzerResultDTO analyzerResultDTO2 = new AnalyzerResultDTO();
        assertThat(analyzerResultDTO1).isNotEqualTo(analyzerResultDTO2);
        analyzerResultDTO2.setId(analyzerResultDTO1.getId());
        assertThat(analyzerResultDTO1).isEqualTo(analyzerResultDTO2);
        analyzerResultDTO2.setId(2L);
        assertThat(analyzerResultDTO1).isNotEqualTo(analyzerResultDTO2);
        analyzerResultDTO1.setId(null);
        assertThat(analyzerResultDTO1).isNotEqualTo(analyzerResultDTO2);
    }
}
