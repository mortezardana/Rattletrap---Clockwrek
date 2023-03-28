package com.yellowmorty.steamball.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yellowmorty.steamball.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GalleriesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Galleries.class);
        Galleries galleries1 = new Galleries();
        galleries1.setId(1L);
        Galleries galleries2 = new Galleries();
        galleries2.setId(galleries1.getId());
        assertThat(galleries1).isEqualTo(galleries2);
        galleries2.setId(2L);
        assertThat(galleries1).isNotEqualTo(galleries2);
        galleries1.setId(null);
        assertThat(galleries1).isNotEqualTo(galleries2);
    }
}
