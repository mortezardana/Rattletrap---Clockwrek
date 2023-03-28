package com.yellowmorty.steamball.service.mapper;

import com.yellowmorty.steamball.domain.Nfts;
import com.yellowmorty.steamball.service.dto.NftsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Nfts} and its DTO {@link NftsDTO}.
 */
@Mapper(componentModel = "spring")
public interface NftsMapper extends EntityMapper<NftsDTO, Nfts> {}
