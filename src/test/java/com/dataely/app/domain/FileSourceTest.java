package com.dataely.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dataely.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FileSourceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileSource.class);
        FileSource fileSource1 = new FileSource();
        fileSource1.setId(1L);
        FileSource fileSource2 = new FileSource();
        fileSource2.setId(fileSource1.getId());
        assertThat(fileSource1).isEqualTo(fileSource2);
        fileSource2.setId(2L);
        assertThat(fileSource1).isNotEqualTo(fileSource2);
        fileSource1.setId(null);
        assertThat(fileSource1).isNotEqualTo(fileSource2);
    }
}
