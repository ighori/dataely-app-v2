package com.dataely.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TablesDefinitionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TablesDefinition.class);
        TablesDefinition tablesDefinition1 = new TablesDefinition();
        tablesDefinition1.setId(1L);
        TablesDefinition tablesDefinition2 = new TablesDefinition();
        tablesDefinition2.setId(tablesDefinition1.getId());
        assertThat(tablesDefinition1).isEqualTo(tablesDefinition2);
        tablesDefinition2.setId(2L);
        assertThat(tablesDefinition1).isNotEqualTo(tablesDefinition2);
        tablesDefinition1.setId(null);
        assertThat(tablesDefinition1).isNotEqualTo(tablesDefinition2);
    }
}
