package com.dataely.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RelatedTableColumnMapperTest {

    private RelatedTableColumnMapper relatedTableColumnMapper;

    @BeforeEach
    public void setUp() {
        relatedTableColumnMapper = new RelatedTableColumnMapperImpl();
    }
}
