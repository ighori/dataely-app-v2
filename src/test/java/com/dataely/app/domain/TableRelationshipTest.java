package com.dataely.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TableRelationshipTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TableRelationship.class);
        TableRelationship tableRelationship1 = new TableRelationship();
        tableRelationship1.setId(1L);
        TableRelationship tableRelationship2 = new TableRelationship();
        tableRelationship2.setId(tableRelationship1.getId());
        assertThat(tableRelationship1).isEqualTo(tableRelationship2);
        tableRelationship2.setId(2L);
        assertThat(tableRelationship1).isNotEqualTo(tableRelationship2);
        tableRelationship1.setId(null);
        assertThat(tableRelationship1).isNotEqualTo(tableRelationship2);
    }
}
