package com.dataely.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FileFieldDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileFieldDTO.class);
        FileFieldDTO fileFieldDTO1 = new FileFieldDTO();
        fileFieldDTO1.setId(1L);
        FileFieldDTO fileFieldDTO2 = new FileFieldDTO();
        assertThat(fileFieldDTO1).isNotEqualTo(fileFieldDTO2);
        fileFieldDTO2.setId(fileFieldDTO1.getId());
        assertThat(fileFieldDTO1).isEqualTo(fileFieldDTO2);
        fileFieldDTO2.setId(2L);
        assertThat(fileFieldDTO1).isNotEqualTo(fileFieldDTO2);
        fileFieldDTO1.setId(null);
        assertThat(fileFieldDTO1).isNotEqualTo(fileFieldDTO2);
    }
}
