package com.yellowmorty.steamball.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GalleriesMapperTest {

    private GalleriesMapper galleriesMapper;

    @BeforeEach
    public void setUp() {
        galleriesMapper = new GalleriesMapperImpl();
    }
}
