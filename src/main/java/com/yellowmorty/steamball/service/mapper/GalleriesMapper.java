package com.yellowmorty.steamball.service.mapper;

import com.yellowmorty.steamball.domain.Galleries;
import com.yellowmorty.steamball.domain.Nfts;
import com.yellowmorty.steamball.domain.Users;
import com.yellowmorty.steamball.service.dto.GalleriesDTO;
import com.yellowmorty.steamball.service.dto.NftsDTO;
import com.yellowmorty.steamball.service.dto.UsersDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Galleries} and its DTO {@link GalleriesDTO}.
 */
@Mapper(componentModel = "spring")
public interface GalleriesMapper extends EntityMapper<GalleriesDTO, Galleries> {
    @Mapping(target = "nfts", source = "nfts", qualifiedByName = "nftsIdSet")
    @Mapping(target = "userId", source = "userId", qualifiedByName = "usersId")
    GalleriesDTO toDto(Galleries s);

    @Mapping(target = "removeNfts", ignore = true)
    Galleries toEntity(GalleriesDTO galleriesDTO);

    @Named("nftsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    NftsDTO toDtoNftsId(Nfts nfts);

    @Named("nftsIdSet")
    default Set<NftsDTO> toDtoNftsIdSet(Set<Nfts> nfts) {
        return nfts.stream().map(this::toDtoNftsId).collect(Collectors.toSet());
    }

    @Named("usersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsersDTO toDtoUsersId(Users users);
}
