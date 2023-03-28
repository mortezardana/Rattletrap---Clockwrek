package com.yellowmorty.steamball.service.dto;

import com.yellowmorty.steamball.domain.enumeration.UserType;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.yellowmorty.steamball.domain.Users} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UsersDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1, max = 64)
    @Pattern(regexp = "^[A-Z][a-z]+\\d$")
    private String userName;

    private String wallets;

    private String galleries;

    @NotNull
    private String password;

    private UserType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWallets() {
        return wallets;
    }

    public void setWallets(String wallets) {
        this.wallets = wallets;
    }

    public String getGalleries() {
        return galleries;
    }

    public void setGalleries(String galleries) {
        this.galleries = galleries;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsersDTO)) {
            return false;
        }

        UsersDTO usersDTO = (UsersDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, usersDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsersDTO{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", wallets='" + getWallets() + "'" +
            ", galleries='" + getGalleries() + "'" +
            ", password='" + getPassword() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
