package com.dataely.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnvironmentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnvironmentDTO.class);
        EnvironmentDTO environmentDTO1 = new EnvironmentDTO();
        environmentDTO1.setId(1L);
        EnvironmentDTO environmentDTO2 = new EnvironmentDTO();
        assertThat(environmentDTO1).isNotEqualTo(environmentDTO2);
        environmentDTO2.setId(environmentDTO1.getId());
        assertThat(environmentDTO1).isEqualTo(environmentDTO2);
        environmentDTO2.setId(2L);
        assertThat(environmentDTO1).isNotEqualTo(environmentDTO2);
        environmentDTO1.setId(null);
        assertThat(environmentDTO1).isNotEqualTo(environmentDTO2);
    }
}
