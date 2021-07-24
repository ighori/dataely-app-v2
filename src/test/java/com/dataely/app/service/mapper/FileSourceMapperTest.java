package com.dataely.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileSourceMapperTest {

    private FileSourceMapper fileSourceMapper;

    @BeforeEach
    public void setUp() {
        fileSourceMapper = new FileSourceMapperImpl();
    }
}
