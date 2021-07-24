package com.dataely.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TableColumnDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TableColumnDTO.class);
        TableColumnDTO tableColumnDTO1 = new TableColumnDTO();
        tableColumnDTO1.setId(1L);
        TableColumnDTO tableColumnDTO2 = new TableColumnDTO();
        assertThat(tableColumnDTO1).isNotEqualTo(tableColumnDTO2);
        tableColumnDTO2.setId(tableColumnDTO1.getId());
        assertThat(tableColumnDTO1).isEqualTo(tableColumnDTO2);
        tableColumnDTO2.setId(2L);
        assertThat(tableColumnDTO1).isNotEqualTo(tableColumnDTO2);
        tableColumnDTO1.setId(null);
        assertThat(tableColumnDTO1).isNotEqualTo(tableColumnDTO2);
    }
}
