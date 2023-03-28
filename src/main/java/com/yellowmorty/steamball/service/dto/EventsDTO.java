package com.yellowmorty.steamball.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.yellowmorty.steamball.domain.Events} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventsDTO implements Serializable {

    @NotNull
    private Long id;

    private String name;

    private String startDate;

    private String enDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEnDate() {
        return enDate;
    }

    public void setEnDate(String enDate) {
        this.enDate = enDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventsDTO)) {
            return false;
        }

        EventsDTO eventsDTO = (EventsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventsDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", enDate='" + getEnDate() + "'" +
            "}";
    }
}
