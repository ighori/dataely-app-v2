package com.dataely.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FileInfoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileInfoDTO.class);
        FileInfoDTO fileInfoDTO1 = new FileInfoDTO();
        fileInfoDTO1.setId(1L);
        FileInfoDTO fileInfoDTO2 = new FileInfoDTO();
        assertThat(fileInfoDTO1).isNotEqualTo(fileInfoDTO2);
        fileInfoDTO2.setId(fileInfoDTO1.getId());
        assertThat(fileInfoDTO1).isEqualTo(fileInfoDTO2);
        fileInfoDTO2.setId(2L);
        assertThat(fileInfoDTO1).isNotEqualTo(fileInfoDTO2);
        fileInfoDTO1.setId(null);
        assertThat(fileInfoDTO1).isNotEqualTo(fileInfoDTO2);
    }
}
