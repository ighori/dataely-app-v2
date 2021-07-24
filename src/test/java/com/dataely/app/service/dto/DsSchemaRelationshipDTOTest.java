package com.dataely.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DsSchemaRelationshipDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DsSchemaRelationshipDTO.class);
        DsSchemaRelationshipDTO dsSchemaRelationshipDTO1 = new DsSchemaRelationshipDTO();
        dsSchemaRelationshipDTO1.setId(1L);
        DsSchemaRelationshipDTO dsSchemaRelationshipDTO2 = new DsSchemaRelationshipDTO();
        assertThat(dsSchemaRelationshipDTO1).isNotEqualTo(dsSchemaRelationshipDTO2);
        dsSchemaRelationshipDTO2.setId(dsSchemaRelationshipDTO1.getId());
        assertThat(dsSchemaRelationshipDTO1).isEqualTo(dsSchemaRelationshipDTO2);
        dsSchemaRelationshipDTO2.setId(2L);
        assertThat(dsSchemaRelationshipDTO1).isNotEqualTo(dsSchemaRelationshipDTO2);
        dsSchemaRelationshipDTO1.setId(null);
        assertThat(dsSchemaRelationshipDTO1).isNotEqualTo(dsSchemaRelationshipDTO2);
    }
}
