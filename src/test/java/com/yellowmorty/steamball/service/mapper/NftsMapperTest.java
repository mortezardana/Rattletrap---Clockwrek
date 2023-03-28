package com.yellowmorty.steamball.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NftsMapperTest {

    private NftsMapper nftsMapper;

    @BeforeEach
    public void setUp() {
        nftsMapper = new NftsMapperImpl();
    }
}
