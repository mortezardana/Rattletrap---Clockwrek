package com.yellowmorty.steamball.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yellowmorty.steamball.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WalletsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WalletsDTO.class);
        WalletsDTO walletsDTO1 = new WalletsDTO();
        walletsDTO1.setId(1L);
        WalletsDTO walletsDTO2 = new WalletsDTO();
        assertThat(walletsDTO1).isNotEqualTo(walletsDTO2);
        walletsDTO2.setId(walletsDTO1.getId());
        assertThat(walletsDTO1).isEqualTo(walletsDTO2);
        walletsDTO2.setId(2L);
        assertThat(walletsDTO1).isNotEqualTo(walletsDTO2);
        walletsDTO1.setId(null);
        assertThat(walletsDTO1).isNotEqualTo(walletsDTO2);
    }
}
