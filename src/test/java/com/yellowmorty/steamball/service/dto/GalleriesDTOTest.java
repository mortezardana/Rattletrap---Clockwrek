package com.yellowmorty.steamball.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yellowmorty.steamball.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GalleriesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GalleriesDTO.class);
        GalleriesDTO galleriesDTO1 = new GalleriesDTO();
        galleriesDTO1.setId(1L);
        GalleriesDTO galleriesDTO2 = new GalleriesDTO();
        assertThat(galleriesDTO1).isNotEqualTo(galleriesDTO2);
        galleriesDTO2.setId(galleriesDTO1.getId());
        assertThat(galleriesDTO1).isEqualTo(galleriesDTO2);
        galleriesDTO2.setId(2L);
        assertThat(galleriesDTO1).isNotEqualTo(galleriesDTO2);
        galleriesDTO1.setId(null);
        assertThat(galleriesDTO1).isNotEqualTo(galleriesDTO2);
    }
}
