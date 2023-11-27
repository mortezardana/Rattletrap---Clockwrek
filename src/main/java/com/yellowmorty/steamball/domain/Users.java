package com.yellowmorty.steamball.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yellowmorty.steamball.domain.enumeration.UserType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Users.
 */
@Entity
@Table(name = "users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Size(min = 1, max = 64)
    @Pattern(regexp = "^[A-Z][a-z]+\\d$")
    @Column(name = "user_name", length = 64, nullable = false, unique = true)
    private String userName;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private UserType type;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userId" }, allowSetters = true)
    private Set<Wallets> wallets = new HashSet<>();

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "comments", "nfts", "userId" }, allowSetters = true)
    private Set<Galleries> galleries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Users id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return this.userName;
    }

    public Users userName(String userName) {
        this.setUserName(userName);
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public Users password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return this.type;
    }

    public Users type(UserType type) {
        this.setType(type);
        return this;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Set<Wallets> getWallets() {
        return this.wallets;
    }

    public void setWallets(Set<Wallets> wallets) {
        if (this.wallets != null) {
            //            this.wallets.forEach(i -> i.setUserId(null));
        }
        if (wallets != null) {
            wallets.forEach(i -> i.setUserId(this));
        }
        this.wallets = wallets;
    }

    public void setWallets(Wallets wallet) {
        if (this.wallets != null) {}
        if (wallet != null) {
            this.wallets.add(wallet);
        }
    }

    public Users wallets(Set<Wallets> wallets) {
        this.setWallets(wallets);
        return this;
    }

    public Users galleries(Set<Galleries> galleries) {
        this.setGalleries(galleries);
        return this;
    }

    public Users addWallet(Wallets wallets) {
        this.wallets.add(wallets);
        wallets.setUserId(this);
        return this;
    }

    public Users removeWallet(Wallets wallets) {
        this.wallets.remove(wallets);
        return this;
    }

    public Set<Galleries> getGalleries() {
        return this.galleries;
    }

    public void setGalleries(Set<Galleries> galleries) {
        if (this.galleries != null) {
            //            this.galleries.forEach(i -> i.setUserId(null));
        }
        if (galleries != null) {
            galleries.forEach(i -> i.setUserId(this));
        }
        this.galleries = galleries;
    }

    public Users addGallery(Galleries galleries) {
        this.galleries.add(galleries);
        galleries.setUserId(this);
        return this;
    }

    public Users removeGallery(Galleries galleries) {
        this.galleries.remove(galleries);
        galleries.setUserId(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Users)) {
            return false;
        }
        return id != null && id.equals(((Users) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Users{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", wallets='" + getWallets() + "'" +
            ", galleries='" + getGalleries() + "'" +
            ", password='" + getPassword() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
