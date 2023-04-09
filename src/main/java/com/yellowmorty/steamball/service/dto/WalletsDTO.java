package com.yellowmorty.steamball.service.dto;

import com.yellowmorty.steamball.domain.enumeration.WalletType;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.yellowmorty.steamball.domain.Wallets} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WalletsDTO implements Serializable {

    private Long id;

    private String walletAddress;

    private WalletType walletType;

    private UsersDTO userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public WalletType getWalletType() {
        return walletType;
    }

    public void setWalletType(WalletType walletType) {
        this.walletType = walletType;
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
        if (!(o instanceof WalletsDTO)) {
            return false;
        }

        WalletsDTO walletsDTO = (WalletsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, walletsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WalletsDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", walletAddress='" + getWalletAddress() + "'" +
            ", walletType='" + getWalletType() + "'" +
            ", userId=" + getUserId() +
            "}";
    }
}
