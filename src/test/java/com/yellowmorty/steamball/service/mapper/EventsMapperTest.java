package com.yellowmorty.steamball.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventsMapperTest {

    private EventsMapper eventsMapper;

    @BeforeEach
    public void setUp() {
        eventsMapper = new EventsMapperImpl();
    }
}
