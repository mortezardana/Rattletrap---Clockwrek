package com.yellowmorty.steamball.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yellowmorty.steamball.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NftsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nfts.class);
        Nfts nfts1 = new Nfts();
        nfts1.setId(1L);
        Nfts nfts2 = new Nfts();
        nfts2.setId(nfts1.getId());
        assertThat(nfts1).isEqualTo(nfts2);
        nfts2.setId(2L);
        assertThat(nfts1).isNotEqualTo(nfts2);
        nfts1.setId(null);
        assertThat(nfts1).isNotEqualTo(nfts2);
    }
}
