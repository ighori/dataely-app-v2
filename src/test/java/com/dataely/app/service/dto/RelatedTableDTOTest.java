package com.dataely.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RelatedTableDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelatedTableDTO.class);
        RelatedTableDTO relatedTableDTO1 = new RelatedTableDTO();
        relatedTableDTO1.setId(1L);
        RelatedTableDTO relatedTableDTO2 = new RelatedTableDTO();
        assertThat(relatedTableDTO1).isNotEqualTo(relatedTableDTO2);
        relatedTableDTO2.setId(relatedTableDTO1.getId());
        assertThat(relatedTableDTO1).isEqualTo(relatedTableDTO2);
        relatedTableDTO2.setId(2L);
        assertThat(relatedTableDTO1).isNotEqualTo(relatedTableDTO2);
        relatedTableDTO1.setId(null);
        assertThat(relatedTableDTO1).isNotEqualTo(relatedTableDTO2);
    }
}
