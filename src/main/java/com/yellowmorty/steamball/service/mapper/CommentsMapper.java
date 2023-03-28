package com.yellowmorty.steamball.service.mapper;

import com.yellowmorty.steamball.domain.Comments;
import com.yellowmorty.steamball.domain.Galleries;
import com.yellowmorty.steamball.service.dto.CommentsDTO;
import com.yellowmorty.steamball.service.dto.GalleriesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comments} and its DTO {@link CommentsDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommentsMapper extends EntityMapper<CommentsDTO, Comments> {
    @Mapping(target = "id", source = "id", qualifiedByName = "galleriesId")
    CommentsDTO toDto(Comments s);

    @Named("galleriesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GalleriesDTO toDtoGalleriesId(Galleries galleries);
}
