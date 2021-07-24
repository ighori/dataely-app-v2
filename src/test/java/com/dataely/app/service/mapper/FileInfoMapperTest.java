package com.dataely.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileInfoMapperTest {

    private FileInfoMapper fileInfoMapper;

    @BeforeEach
    public void setUp() {
        fileInfoMapper = new FileInfoMapperImpl();
    }
}
