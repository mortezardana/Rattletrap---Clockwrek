package com.yellowmorty.steamball.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Galleries.
 */
@Entity
@Table(name = "galleries")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Galleries implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @NotNull
    @Column(name = "creator", nullable = false, unique = true)
    private Long creator;

    @Column(name = "likes")
    private String likes;

    @OneToMany(mappedBy = "id")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "id" }, allowSetters = true)
    private Set<Comments> comments = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_galleries__nfts",
        joinColumns = @JoinColumn(name = "galleries_id"),
        inverseJoinColumns = @JoinColumn(name = "nfts_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ids" }, allowSetters = true)
    private Set<Nfts> nfts = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "ids", "ids" }, allowSetters = true)
    private Users userId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Galleries id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreator() {
        return this.creator;
    }

    public Galleries creator(Long creator) {
        this.setCreator(creator);
        return this;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public String getLikes() {
        return this.likes;
    }

    public Galleries likes(String likes) {
        this.setLikes(likes);
        return this;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public Set<Comments> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comments> comments) {
        if (this.comments != null) {
            //            this.comments.forEach(i -> i.setId(null));
        }
        if (comments != null) {
            comments.forEach(i -> i.setId(this.id));
        }
        this.comments = comments;
    }

    public Galleries comments(Set<Comments> comments) {
        this.setComments(comments);
        return this;
    }

    public Galleries addComments(Comments comments) {
        this.comments.add(comments);
        comments.setId(this.id);
        return this;
    }

    public Galleries removeComments(Comments comments) {
        this.comments.remove(comments);
        return this;
    }

    public Set<Nfts> getNfts() {
        return this.nfts;
    }

    public void setNfts(Set<Nfts> nfts) {
        this.nfts = nfts;
    }

    public Galleries nfts(Set<Nfts> nfts) {
        this.setNfts(nfts);
        return this;
    }

    public Galleries addNfts(Nfts nfts) {
        this.nfts.add(nfts);
        nfts.getIds().add(this);
        return this;
    }

    public Galleries removeNfts(Nfts nfts) {
        this.nfts.remove(nfts);
        nfts.getIds().remove(this);
        return this;
    }

    public Users getUserId() {
        return this.userId;
    }

    public void setUserId(Users users) {
        this.userId = users;
    }

    public Galleries userId(Users users) {
        this.setUserId(users);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Galleries)) {
            return false;
        }
        return id != null && id.equals(((Galleries) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Galleries{" +
            "id=" + getId() +
            ", creator=" + getCreator() +
            ", nfts='" + getNfts() + "'" +
            ", likes='" + getLikes() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}
