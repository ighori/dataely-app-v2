package com.dataely.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileConfigMapperTest {

    private FileConfigMapper fileConfigMapper;

    @BeforeEach
    public void setUp() {
        fileConfigMapper = new FileConfigMapperImpl();
    }
}
