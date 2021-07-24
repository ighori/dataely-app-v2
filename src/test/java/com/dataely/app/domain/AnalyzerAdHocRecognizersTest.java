package com.dataely.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnalyzerAdHocRecognizersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnalyzerAdHocRecognizers.class);
        AnalyzerAdHocRecognizers analyzerAdHocRecognizers1 = new AnalyzerAdHocRecognizers();
        analyzerAdHocRecognizers1.setId(1L);
        AnalyzerAdHocRecognizers analyzerAdHocRecognizers2 = new AnalyzerAdHocRecognizers();
        analyzerAdHocRecognizers2.setId(analyzerAdHocRecognizers1.getId());
        assertThat(analyzerAdHocRecognizers1).isEqualTo(analyzerAdHocRecognizers2);
        analyzerAdHocRecognizers2.setId(2L);
        assertThat(analyzerAdHocRecognizers1).isNotEqualTo(analyzerAdHocRecognizers2);
        analyzerAdHocRecognizers1.setId(null);
        assertThat(analyzerAdHocRecognizers1).isNotEqualTo(analyzerAdHocRecognizers2);
    }
}
