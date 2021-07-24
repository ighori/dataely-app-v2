package com.dataely.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnalyzerResultTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnalyzerResult.class);
        AnalyzerResult analyzerResult1 = new AnalyzerResult();
        analyzerResult1.setId(1L);
        AnalyzerResult analyzerResult2 = new AnalyzerResult();
        analyzerResult2.setId(analyzerResult1.getId());
        assertThat(analyzerResult1).isEqualTo(analyzerResult2);
        analyzerResult2.setId(2L);
        assertThat(analyzerResult1).isNotEqualTo(analyzerResult2);
        analyzerResult1.setId(null);
        assertThat(analyzerResult1).isNotEqualTo(analyzerResult2);
    }
}
