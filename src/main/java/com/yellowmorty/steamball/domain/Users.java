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

    @Column(name = "wallets")
    private String wallets;

    @Column(name = "galleries")
    private String galleries;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private UserType type;

    @OneToMany(mappedBy = "userId")
    @OneToMany(mappedBy = "userId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "userId" }, allowSetters = true)
    private Set<Wallets> ids = new HashSet<>();

    @OneToMany(mappedBy = "userId")
    @OneToMany(mappedBy = "userId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "comments", "nfts", "userId" }, allowSetters = true)
    private Set<Galleries> ids = new HashSet<>();

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

    public String getWallets() {
        return this.wallets;
    }

    public Users wallets(String wallets) {
        this.setWallets(wallets);
        return this;
    }

    public void setWallets(String wallets) {
        this.wallets = wallets;
    }

    public String getGalleries() {
        return this.galleries;
    }

    public Users galleries(String galleries) {
        this.setGalleries(galleries);
        return this;
    }

    public void setGalleries(String galleries) {
        this.galleries = galleries;
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

    public Set<Wallets> getIds() {
        return this.ids;
    }

    public void setIds(Set<Wallets> wallets) {
        if (this.ids != null) {
            this.ids.forEach(i -> i.setUserId(null));
        }
        if (wallets != null) {
            wallets.forEach(i -> i.setUserId(this));
        }
        this.ids = wallets;
    }

    public Users ids(Set<Wallets> wallets) {
        this.setIds(wallets);
        return this;
    }

    public Users addId(Wallets wallets) {
        this.ids.add(wallets);
        wallets.setUserId(this);
        return this;
    }

    public Users removeId(Wallets wallets) {
        this.ids.remove(wallets);
        wallets.setUserId(null);
        return this;
    }

    public Set<Galleries> getIds() {
        return this.ids;
    }

    public void setIds(Set<Galleries> galleries) {
        if (this.ids != null) {
            this.ids.forEach(i -> i.setUserId(null));
        }
        if (galleries != null) {
            galleries.forEach(i -> i.setUserId(this));
        }
        this.ids = galleries;
    }

    public Users ids(Set<Galleries> galleries) {
        this.setIds(galleries);
        return this;
    }

    public Users addId(Galleries galleries) {
        this.ids.add(galleries);
        galleries.setUserId(this);
        return this;
    }

    public Users removeId(Galleries galleries) {
        this.ids.remove(galleries);
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
