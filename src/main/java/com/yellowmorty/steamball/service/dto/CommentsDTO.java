package com.yellowmorty.steamball.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.yellowmorty.steamball.domain.Comments} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CommentsDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private String text;

    private Long father;

    private GalleriesDTO galleries;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getFather() {
        return father;
    }

    public void setFather(Long father) {
        this.father = father;
    }

    public GalleriesDTO getGalleries() {
        return galleries;
    }

    public void setGalleries(GalleriesDTO galleries) {
        this.galleries = galleries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommentsDTO)) {
            return false;
        }

        CommentsDTO commentsDTO = (CommentsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, commentsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommentsDTO{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", father=" + getFather() +
            ", id=" + getId() +
            "}";
    }
}
