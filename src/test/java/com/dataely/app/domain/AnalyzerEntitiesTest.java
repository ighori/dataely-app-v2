package com.dataely.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnalyzerEntitiesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnalyzerEntities.class);
        AnalyzerEntities analyzerEntities1 = new AnalyzerEntities();
        analyzerEntities1.setId(1L);
        AnalyzerEntities analyzerEntities2 = new AnalyzerEntities();
        analyzerEntities2.setId(analyzerEntities1.getId());
        assertThat(analyzerEntities1).isEqualTo(analyzerEntities2);
        analyzerEntities2.setId(2L);
        assertThat(analyzerEntities1).isNotEqualTo(analyzerEntities2);
        analyzerEntities1.setId(null);
        assertThat(analyzerEntities1).isNotEqualTo(analyzerEntities2);
    }
}
