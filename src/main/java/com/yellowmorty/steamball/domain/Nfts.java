package com.yellowmorty.steamball.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yellowmorty.steamball.domain.enumeration.Formats;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Nfts.
 */
@Entity
@Table(name = "nfts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Nfts implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "creator_address")
    private String creatorAddress;

    @Column(name = "owner_address")
    private String ownerAddress;

    @Column(name = "contract_address")
    private String contractAddress;

    @Column(name = "file_address")
    private String fileAddress;

    @Column(name = "actual_file")
    private String actualFile;

    @Column(name = "metadata_address")
    private String metadataAddress;

    @Column(name = "metadata")
    private String metadata;

    @Column(name = "tile")
    private String tile;

    @Enumerated(EnumType.STRING)
    @Column(name = "format")
    private Formats format;

    @Column(name = "traits")
    private String traits;

    @ManyToMany(mappedBy = "nfts")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "comments", "nfts", "userId" }, allowSetters = true)
    private Set<Galleries> ids = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Nfts id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatorAddress() {
        return this.creatorAddress;
    }

    public Nfts creatorAddress(String creatorAddress) {
        this.setCreatorAddress(creatorAddress);
        return this;
    }

    public void setCreatorAddress(String creatorAddress) {
        this.creatorAddress = creatorAddress;
    }

    public String getOwnerAddress() {
        return this.ownerAddress;
    }

    public Nfts ownerAddress(String ownerAddress) {
        this.setOwnerAddress(ownerAddress);
        return this;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public String getContractAddress() {
        return this.contractAddress;
    }

    public Nfts contractAddress(String contractAddress) {
        this.setContractAddress(contractAddress);
        return this;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getFileAddress() {
        return this.fileAddress;
    }

    public Nfts fileAddress(String fileAddress) {
        this.setFileAddress(fileAddress);
        return this;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public String getActualFile() {
        return this.actualFile;
    }

    public Nfts actualFile(String actualFile) {
        this.setActualFile(actualFile);
        return this;
    }

    public void setActualFile(String actualFile) {
        this.actualFile = actualFile;
    }

    public String getMetadataAddress() {
        return this.metadataAddress;
    }

    public Nfts metadataAddress(String metadataAddress) {
        this.setMetadataAddress(metadataAddress);
        return this;
    }

    public void setMetadataAddress(String metadataAddress) {
        this.metadataAddress = metadataAddress;
    }

    public String getMetadata() {
        return this.metadata;
    }

    public Nfts metadata(String metadata) {
        this.setMetadata(metadata);
        return this;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getTile() {
        return this.tile;
    }

    public Nfts tile(String tile) {
        this.setTile(tile);
        return this;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

    public Formats getFormat() {
        return this.format;
    }

    public Nfts format(Formats format) {
        this.setFormat(format);
        return this;
    }

    public void setFormat(Formats format) {
        this.format = format;
    }

    public String getTraits() {
        return this.traits;
    }

    public Nfts traits(String traits) {
        this.setTraits(traits);
        return this;
    }

    public void setTraits(String traits) {
        this.traits = traits;
    }

    public Set<Galleries> getIds() {
        return this.ids;
    }

    public void setIds(Set<Galleries> galleries) {
        if (this.ids != null) {
            this.ids.forEach(i -> i.removeNfts(this));
        }
        if (galleries != null) {
            galleries.forEach(i -> i.addNfts(this));
        }
        this.ids = galleries;
    }

    public Nfts ids(Set<Galleries> galleries) {
        this.setIds(galleries);
        return this;
    }

    public Nfts addId(Galleries galleries) {
        this.ids.add(galleries);
        galleries.getNfts().add(this);
        return this;
    }

    public Nfts removeId(Galleries galleries) {
        this.ids.remove(galleries);
        galleries.getNfts().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nfts)) {
            return false;
        }
        return id != null && id.equals(((Nfts) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nfts{" +
            "id=" + getId() +
            ", creatorAddress='" + getCreatorAddress() + "'" +
            ", ownerAddress='" + getOwnerAddress() + "'" +
            ", contractAddress='" + getContractAddress() + "'" +
            ", fileAddress='" + getFileAddress() + "'" +
            ", actualFile='" + getActualFile() + "'" +
            ", metadataAddress='" + getMetadataAddress() + "'" +
            ", metadata='" + getMetadata() + "'" +
            ", tile='" + getTile() + "'" +
            ", format='" + getFormat() + "'" +
            ", traits='" + getTraits() + "'" +
            "}";
    }
}
