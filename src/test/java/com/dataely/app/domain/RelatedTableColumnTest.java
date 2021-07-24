package com.dataely.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RelatedTableColumnTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelatedTableColumn.class);
        RelatedTableColumn relatedTableColumn1 = new RelatedTableColumn();
        relatedTableColumn1.setId(1L);
        RelatedTableColumn relatedTableColumn2 = new RelatedTableColumn();
        relatedTableColumn2.setId(relatedTableColumn1.getId());
        assertThat(relatedTableColumn1).isEqualTo(relatedTableColumn2);
        relatedTableColumn2.setId(2L);
        assertThat(relatedTableColumn1).isNotEqualTo(relatedTableColumn2);
        relatedTableColumn1.setId(null);
        assertThat(relatedTableColumn1).isNotEqualTo(relatedTableColumn2);
    }
}
