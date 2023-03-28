package com.yellowmorty.steamball.service.dto;

import com.yellowmorty.steamball.domain.Comments;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.yellowmorty.steamball.domain.Galleries} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GalleriesDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private Long creator;

    private String likes;

    private Set<Comments> comments;

    private Set<NftsDTO> nfts = new HashSet<>();

    private UsersDTO userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public Set<Comments> getComments() {
        return comments;
    }

    public void setComments(Set<Comments> comments) {
        this.comments = comments;
    }

    public Set<NftsDTO> getNfts() {
        return nfts;
    }

    public void setNfts(Set<NftsDTO> nfts) {
        this.nfts = nfts;
    }

    public UsersDTO getUserId() {
        return userId;
    }

    public void setUserId(UsersDTO userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GalleriesDTO)) {
            return false;
        }

        GalleriesDTO galleriesDTO = (GalleriesDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, galleriesDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GalleriesDTO{" +
            "id=" + getId() +
            ", creator=" + getCreator() +
            ", nfts='" + getNfts() + "'" +
            ", likes='" + getLikes() + "'" +
            ", comments='" + getComments() + "'" +
            ", nfts=" + getNfts() +
            ", userId=" + getUserId() +
            "}";
    }
}
