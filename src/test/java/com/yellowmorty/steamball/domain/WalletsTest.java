package com.yellowmorty.steamball.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.yellowmorty.steamball.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WalletsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wallets.class);
        Wallets wallets1 = new Wallets();
        wallets1.setId(1L);
        Wallets wallets2 = new Wallets();
        wallets2.setId(wallets1.getId());
        assertThat(wallets1).isEqualTo(wallets2);
        wallets2.setId(2L);
        assertThat(wallets1).isNotEqualTo(wallets2);
        wallets1.setId(null);
        assertThat(wallets1).isNotEqualTo(wallets2);
    }
}
