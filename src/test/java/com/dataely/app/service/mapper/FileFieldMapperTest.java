package com.dataely.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileFieldMapperTest {

    private FileFieldMapper fileFieldMapper;

    @BeforeEach
    public void setUp() {
        fileFieldMapper = new FileFieldMapperImpl();
    }
}
