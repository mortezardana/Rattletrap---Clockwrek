package com.yellowmorty.steamball.service.mapper;

import com.yellowmorty.steamball.domain.Users;
import com.yellowmorty.steamball.domain.Wallets;
import com.yellowmorty.steamball.service.dto.UsersDTO;
import com.yellowmorty.steamball.service.dto.WalletsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Wallets} and its DTO {@link WalletsDTO}.
 */
@Mapper(componentModel = "spring")
public interface WalletsMapper extends EntityMapper<WalletsDTO, Wallets> {
    @Mapping(target = "userId", source = "userId", qualifiedByName = "usersId")
    WalletsDTO toDto(Wallets s);

    @Named("usersId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UsersDTO toDtoUsersId(Users users);
}
