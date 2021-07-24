package com.dataely.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TableRelationshipDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TableRelationshipDTO.class);
        TableRelationshipDTO tableRelationshipDTO1 = new TableRelationshipDTO();
        tableRelationshipDTO1.setId(1L);
        TableRelationshipDTO tableRelationshipDTO2 = new TableRelationshipDTO();
        assertThat(tableRelationshipDTO1).isNotEqualTo(tableRelationshipDTO2);
        tableRelationshipDTO2.setId(tableRelationshipDTO1.getId());
        assertThat(tableRelationshipDTO1).isEqualTo(tableRelationshipDTO2);
        tableRelationshipDTO2.setId(2L);
        assertThat(tableRelationshipDTO1).isNotEqualTo(tableRelationshipDTO2);
        tableRelationshipDTO1.setId(null);
        assertThat(tableRelationshipDTO1).isNotEqualTo(tableRelationshipDTO2);
    }
}
