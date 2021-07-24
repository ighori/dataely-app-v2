package com.dataely.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DsSchemaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DsSchemaDTO.class);
        DsSchemaDTO dsSchemaDTO1 = new DsSchemaDTO();
        dsSchemaDTO1.setId(1L);
        DsSchemaDTO dsSchemaDTO2 = new DsSchemaDTO();
        assertThat(dsSchemaDTO1).isNotEqualTo(dsSchemaDTO2);
        dsSchemaDTO2.setId(dsSchemaDTO1.getId());
        assertThat(dsSchemaDTO1).isEqualTo(dsSchemaDTO2);
        dsSchemaDTO2.setId(2L);
        assertThat(dsSchemaDTO1).isNotEqualTo(dsSchemaDTO2);
        dsSchemaDTO1.setId(null);
        assertThat(dsSchemaDTO1).isNotEqualTo(dsSchemaDTO2);
    }
}
