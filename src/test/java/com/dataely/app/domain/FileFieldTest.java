package com.dataely.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FileFieldTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileField.class);
        FileField fileField1 = new FileField();
        fileField1.setId(1L);
        FileField fileField2 = new FileField();
        fileField2.setId(fileField1.getId());
        assertThat(fileField1).isEqualTo(fileField2);
        fileField2.setId(2L);
        assertThat(fileField1).isNotEqualTo(fileField2);
        fileField1.setId(null);
        assertThat(fileField1).isNotEqualTo(fileField2);
    }
}
