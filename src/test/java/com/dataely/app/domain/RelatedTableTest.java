package com.dataely.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RelatedTableTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelatedTable.class);
        RelatedTable relatedTable1 = new RelatedTable();
        relatedTable1.setId(1L);
        RelatedTable relatedTable2 = new RelatedTable();
        relatedTable2.setId(relatedTable1.getId());
        assertThat(relatedTable1).isEqualTo(relatedTable2);
        relatedTable2.setId(2L);
        assertThat(relatedTable1).isNotEqualTo(relatedTable2);
        relatedTable1.setId(null);
        assertThat(relatedTable1).isNotEqualTo(relatedTable2);
    }
}
