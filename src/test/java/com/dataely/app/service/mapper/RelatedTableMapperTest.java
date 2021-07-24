package com.dataely.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RelatedTableMapperTest {

    private RelatedTableMapper relatedTableMapper;

    @BeforeEach
    public void setUp() {
        relatedTableMapper = new RelatedTableMapperImpl();
    }
}
