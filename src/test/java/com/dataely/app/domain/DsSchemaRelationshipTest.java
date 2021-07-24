package com.dataely.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DsSchemaRelationshipTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DsSchemaRelationship.class);
        DsSchemaRelationship dsSchemaRelationship1 = new DsSchemaRelationship();
        dsSchemaRelationship1.setId(1L);
        DsSchemaRelationship dsSchemaRelationship2 = new DsSchemaRelationship();
        dsSchemaRelationship2.setId(dsSchemaRelationship1.getId());
        assertThat(dsSchemaRelationship1).isEqualTo(dsSchemaRelationship2);
        dsSchemaRelationship2.setId(2L);
        assertThat(dsSchemaRelationship1).isNotEqualTo(dsSchemaRelationship2);
        dsSchemaRelationship1.setId(null);
        assertThat(dsSchemaRelationship1).isNotEqualTo(dsSchemaRelationship2);
    }
}
