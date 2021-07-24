package com.dataely.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnalyzerEntitiesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnalyzerEntitiesDTO.class);
        AnalyzerEntitiesDTO analyzerEntitiesDTO1 = new AnalyzerEntitiesDTO();
        analyzerEntitiesDTO1.setId(1L);
        AnalyzerEntitiesDTO analyzerEntitiesDTO2 = new AnalyzerEntitiesDTO();
        assertThat(analyzerEntitiesDTO1).isNotEqualTo(analyzerEntitiesDTO2);
        analyzerEntitiesDTO2.setId(analyzerEntitiesDTO1.getId());
        assertThat(analyzerEntitiesDTO1).isEqualTo(analyzerEntitiesDTO2);
        analyzerEntitiesDTO2.setId(2L);
        assertThat(analyzerEntitiesDTO1).isNotEqualTo(analyzerEntitiesDTO2);
        analyzerEntitiesDTO1.setId(null);
        assertThat(analyzerEntitiesDTO1).isNotEqualTo(analyzerEntitiesDTO2);
    }
}
