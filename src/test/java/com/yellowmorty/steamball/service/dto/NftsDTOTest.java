package com.yellowmorty.steamball.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.yellowmorty.steamball.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NftsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NftsDTO.class);
        NftsDTO nftsDTO1 = new NftsDTO();
        nftsDTO1.setId(1L);
        NftsDTO nftsDTO2 = new NftsDTO();
        assertThat(nftsDTO1).isNotEqualTo(nftsDTO2);
        nftsDTO2.setId(nftsDTO1.getId());
        assertThat(nftsDTO1).isEqualTo(nftsDTO2);
        nftsDTO2.setId(2L);
        assertThat(nftsDTO1).isNotEqualTo(nftsDTO2);
        nftsDTO1.setId(null);
        assertThat(nftsDTO1).isNotEqualTo(nftsDTO2);
    }
}
