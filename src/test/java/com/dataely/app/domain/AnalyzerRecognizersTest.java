package com.dataely.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnalyzerRecognizersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnalyzerRecognizers.class);
        AnalyzerRecognizers analyzerRecognizers1 = new AnalyzerRecognizers();
        analyzerRecognizers1.setId(1L);
        AnalyzerRecognizers analyzerRecognizers2 = new AnalyzerRecognizers();
        analyzerRecognizers2.setId(analyzerRecognizers1.getId());
        assertThat(analyzerRecognizers1).isEqualTo(analyzerRecognizers2);
        analyzerRecognizers2.setId(2L);
        assertThat(analyzerRecognizers1).isNotEqualTo(analyzerRecognizers2);
        analyzerRecognizers1.setId(null);
        assertThat(analyzerRecognizers1).isNotEqualTo(analyzerRecognizers2);
    }
}
