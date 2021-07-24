package com.dataely.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DsSchemaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DsSchema.class);
        DsSchema dsSchema1 = new DsSchema();
        dsSchema1.setId(1L);
        DsSchema dsSchema2 = new DsSchema();
        dsSchema2.setId(dsSchema1.getId());
        assertThat(dsSchema1).isEqualTo(dsSchema2);
        dsSchema2.setId(2L);
        assertThat(dsSchema1).isNotEqualTo(dsSchema2);
        dsSchema1.setId(null);
        assertThat(dsSchema1).isNotEqualTo(dsSchema2);
    }
}
