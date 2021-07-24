package com.dataely.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FileConfigDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileConfigDTO.class);
        FileConfigDTO fileConfigDTO1 = new FileConfigDTO();
        fileConfigDTO1.setId(1L);
        FileConfigDTO fileConfigDTO2 = new FileConfigDTO();
        assertThat(fileConfigDTO1).isNotEqualTo(fileConfigDTO2);
        fileConfigDTO2.setId(fileConfigDTO1.getId());
        assertThat(fileConfigDTO1).isEqualTo(fileConfigDTO2);
        fileConfigDTO2.setId(2L);
        assertThat(fileConfigDTO1).isNotEqualTo(fileConfigDTO2);
        fileConfigDTO1.setId(null);
        assertThat(fileConfigDTO1).isNotEqualTo(fileConfigDTO2);
    }
}
