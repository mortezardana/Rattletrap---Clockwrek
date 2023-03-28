package com.yellowmorty.steamball.service.mapper;

import com.yellowmorty.steamball.domain.Events;
import com.yellowmorty.steamball.service.dto.EventsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Events} and its DTO {@link EventsDTO}.
 */
@Mapper(componentModel = "spring")
public interface EventsMapper extends EntityMapper<EventsDTO, Events> {}
