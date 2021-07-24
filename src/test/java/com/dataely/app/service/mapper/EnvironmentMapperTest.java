package com.dataely.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EnvironmentMapperTest {

    private EnvironmentMapper environmentMapper;

    @BeforeEach
    public void setUp() {
        environmentMapper = new EnvironmentMapperImpl();
    }
}
