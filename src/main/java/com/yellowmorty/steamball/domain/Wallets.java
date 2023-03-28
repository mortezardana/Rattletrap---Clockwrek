package com.yellowmorty.steamball.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yellowmorty.steamball.domain.enumeration.WalletType;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Wallets.
 */
@Entity
@Table(name = "wallets")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Wallets implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "wallet_address")
    private String walletAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "wallet_type")
    private WalletType walletType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ids", "ids" }, allowSetters = true)
    private Users userId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Wallets id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Wallets userId(Long userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getWalletAddress() {
        return this.walletAddress;
    }

    public Wallets walletAddress(String walletAddress) {
        this.setWalletAddress(walletAddress);
        return this;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public WalletType getWalletType() {
        return this.walletType;
    }

    public Wallets walletType(WalletType walletType) {
        this.setWalletType(walletType);
        return this;
    }

    public void setWalletType(WalletType walletType) {
        this.walletType = walletType;
    }

    public Users getUserId() {
        return this.userId;
    }

    public void setUserId(Users users) {
        this.userId = users;
    }

    public Wallets userId(Users users) {
        this.setUserId(users);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wallets)) {
            return false;
        }
        return id != null && id.equals(((Wallets) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wallets{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", walletAddress='" + getWalletAddress() + "'" +
            ", walletType='" + getWalletType() + "'" +
            "}";
    }
}
