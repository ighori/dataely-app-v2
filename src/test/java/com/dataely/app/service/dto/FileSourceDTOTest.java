package com.dataely.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FileSourceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileSourceDTO.class);
        FileSourceDTO fileSourceDTO1 = new FileSourceDTO();
        fileSourceDTO1.setId(1L);
        FileSourceDTO fileSourceDTO2 = new FileSourceDTO();
        assertThat(fileSourceDTO1).isNotEqualTo(fileSourceDTO2);
        fileSourceDTO2.setId(fileSourceDTO1.getId());
        assertThat(fileSourceDTO1).isEqualTo(fileSourceDTO2);
        fileSourceDTO2.setId(2L);
        assertThat(fileSourceDTO1).isNotEqualTo(fileSourceDTO2);
        fileSourceDTO1.setId(null);
        assertThat(fileSourceDTO1).isNotEqualTo(fileSourceDTO2);
    }
}
