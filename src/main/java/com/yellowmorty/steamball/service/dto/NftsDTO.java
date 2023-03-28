package com.yellowmorty.steamball.service.dto;

import com.yellowmorty.steamball.domain.enumeration.Formats;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.yellowmorty.steamball.domain.Nfts} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NftsDTO implements Serializable {

    @NotNull
    private Long id;

    private String creatorAddress;

    private String ownerAddress;

    private String contractAddress;

    private String fileAddress;

    private String actualFile;

    private String metadataAddress;

    private String metadata;

    private String tile;

    private Formats format;

    private String traits;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatorAddress() {
        return creatorAddress;
    }

    public void setCreatorAddress(String creatorAddress) {
        this.creatorAddress = creatorAddress;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public String getActualFile() {
        return actualFile;
    }

    public void setActualFile(String actualFile) {
        this.actualFile = actualFile;
    }

    public String getMetadataAddress() {
        return metadataAddress;
    }

    public void setMetadataAddress(String metadataAddress) {
        this.metadataAddress = metadataAddress;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

    public Formats getFormat() {
        return format;
    }

    public void setFormat(Formats format) {
        this.format = format;
    }

    public String getTraits() {
        return traits;
    }

    public void setTraits(String traits) {
        this.traits = traits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NftsDTO)) {
            return false;
        }

        NftsDTO nftsDTO = (NftsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, nftsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NftsDTO{" +
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
