package com.yellowmorty.steamball.service.mapper;

import com.yellowmorty.steamball.domain.Users;
import com.yellowmorty.steamball.service.dto.UsersDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Users} and its DTO {@link UsersDTO}.
 */
@Mapper(componentModel = "spring")
public interface UsersMapper extends EntityMapper<UsersDTO, Users> {}
