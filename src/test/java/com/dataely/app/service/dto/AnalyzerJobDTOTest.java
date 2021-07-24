package com.dataely.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnalyzerJobDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnalyzerJobDTO.class);
        AnalyzerJobDTO analyzerJobDTO1 = new AnalyzerJobDTO();
        analyzerJobDTO1.setId(1L);
        AnalyzerJobDTO analyzerJobDTO2 = new AnalyzerJobDTO();
        assertThat(analyzerJobDTO1).isNotEqualTo(analyzerJobDTO2);
        analyzerJobDTO2.setId(analyzerJobDTO1.getId());
        assertThat(analyzerJobDTO1).isEqualTo(analyzerJobDTO2);
        analyzerJobDTO2.setId(2L);
        assertThat(analyzerJobDTO1).isNotEqualTo(analyzerJobDTO2);
        analyzerJobDTO1.setId(null);
        assertThat(analyzerJobDTO1).isNotEqualTo(analyzerJobDTO2);
    }
}
