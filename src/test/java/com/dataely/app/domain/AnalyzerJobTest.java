package com.dataely.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnalyzerJobTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnalyzerJob.class);
        AnalyzerJob analyzerJob1 = new AnalyzerJob();
        analyzerJob1.setId(1L);
        AnalyzerJob analyzerJob2 = new AnalyzerJob();
        analyzerJob2.setId(analyzerJob1.getId());
        assertThat(analyzerJob1).isEqualTo(analyzerJob2);
        analyzerJob2.setId(2L);
        assertThat(analyzerJob1).isNotEqualTo(analyzerJob2);
        analyzerJob1.setId(null);
        assertThat(analyzerJob1).isNotEqualTo(analyzerJob2);
    }
}
