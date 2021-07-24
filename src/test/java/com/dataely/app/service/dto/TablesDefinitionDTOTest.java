package com.dataely.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TablesDefinitionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TablesDefinitionDTO.class);
        TablesDefinitionDTO tablesDefinitionDTO1 = new TablesDefinitionDTO();
        tablesDefinitionDTO1.setId(1L);
        TablesDefinitionDTO tablesDefinitionDTO2 = new TablesDefinitionDTO();
        assertThat(tablesDefinitionDTO1).isNotEqualTo(tablesDefinitionDTO2);
        tablesDefinitionDTO2.setId(tablesDefinitionDTO1.getId());
        assertThat(tablesDefinitionDTO1).isEqualTo(tablesDefinitionDTO2);
        tablesDefinitionDTO2.setId(2L);
        assertThat(tablesDefinitionDTO1).isNotEqualTo(tablesDefinitionDTO2);
        tablesDefinitionDTO1.setId(null);
        assertThat(tablesDefinitionDTO1).isNotEqualTo(tablesDefinitionDTO2);
    }
}
