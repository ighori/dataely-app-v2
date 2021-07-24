package com.dataely.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnvironmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Environment.class);
        Environment environment1 = new Environment();
        environment1.setId(1L);
        Environment environment2 = new Environment();
        environment2.setId(environment1.getId());
        assertThat(environment1).isEqualTo(environment2);
        environment2.setId(2L);
        assertThat(environment1).isNotEqualTo(environment2);
        environment1.setId(null);
        assertThat(environment1).isNotEqualTo(environment2);
    }
}
