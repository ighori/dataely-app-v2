package com.dataely.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RelatedTableColumnDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelatedTableColumnDTO.class);
        RelatedTableColumnDTO relatedTableColumnDTO1 = new RelatedTableColumnDTO();
        relatedTableColumnDTO1.setId(1L);
        RelatedTableColumnDTO relatedTableColumnDTO2 = new RelatedTableColumnDTO();
        assertThat(relatedTableColumnDTO1).isNotEqualTo(relatedTableColumnDTO2);
        relatedTableColumnDTO2.setId(relatedTableColumnDTO1.getId());
        assertThat(relatedTableColumnDTO1).isEqualTo(relatedTableColumnDTO2);
        relatedTableColumnDTO2.setId(2L);
        assertThat(relatedTableColumnDTO1).isNotEqualTo(relatedTableColumnDTO2);
        relatedTableColumnDTO1.setId(null);
        assertThat(relatedTableColumnDTO1).isNotEqualTo(relatedTableColumnDTO2);
    }
}
