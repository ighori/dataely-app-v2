package com.dataely.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TableColumnTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TableColumn.class);
        TableColumn tableColumn1 = new TableColumn();
        tableColumn1.setId(1L);
        TableColumn tableColumn2 = new TableColumn();
        tableColumn2.setId(tableColumn1.getId());
        assertThat(tableColumn1).isEqualTo(tableColumn2);
        tableColumn2.setId(2L);
        assertThat(tableColumn1).isNotEqualTo(tableColumn2);
        tableColumn1.setId(null);
        assertThat(tableColumn1).isNotEqualTo(tableColumn2);
    }
}
