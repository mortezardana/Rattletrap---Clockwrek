package com.yellowmorty.steamball.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yellowmorty.steamball.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventsDTO.class);
        EventsDTO eventsDTO1 = new EventsDTO();
        eventsDTO1.setId(1L);
        EventsDTO eventsDTO2 = new EventsDTO();
        assertThat(eventsDTO1).isNotEqualTo(eventsDTO2);
        eventsDTO2.setId(eventsDTO1.getId());
        assertThat(eventsDTO1).isEqualTo(eventsDTO2);
        eventsDTO2.setId(2L);
        assertThat(eventsDTO1).isNotEqualTo(eventsDTO2);
        eventsDTO1.setId(null);
        assertThat(eventsDTO1).isNotEqualTo(eventsDTO2);
    }
}
