package com.yellowmorty.steamball.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yellowmorty.steamball.IntegrationTest;
import com.yellowmorty.steamball.domain.Nfts;
import com.yellowmorty.steamball.domain.enumeration.Formats;
import com.yellowmorty.steamball.repository.NftsRepository;
import com.yellowmorty.steamball.service.dto.NftsDTO;
import com.yellowmorty.steamball.service.mapper.NftsMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NftsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NftsResourceIT {

    private static final String DEFAULT_CREATOR_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CREATOR_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_OWNER_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CONTRACT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_FILE_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_ACTUAL_FILE = "AAAAAAAAAA";
    private static final String UPDATED_ACTUAL_FILE = "BBBBBBBBBB";

    private static final String DEFAULT_METADATA_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_METADATA_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_METADATA = "AAAAAAAAAA";
    private static final String UPDATED_METADATA = "BBBBBBBBBB";

    private static final String DEFAULT_TILE = "AAAAAAAAAA";
    private static final String UPDATED_TILE = "BBBBBBBBBB";

    private static final Formats DEFAULT_FORMAT = Formats.JPG;
    private static final Formats UPDATED_FORMAT = Formats.PNG;

    private static final String DEFAULT_TRAITS = "AAAAAAAAAA";
    private static final String UPDATED_TRAITS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nfts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NftsRepository nftsRepository;

    @Autowired
    private NftsMapper nftsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNftsMockMvc;

    private Nfts nfts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nfts createEntity(EntityManager em) {
        Nfts nfts = new Nfts()
            .creatorAddress(DEFAULT_CREATOR_ADDRESS)
            .ownerAddress(DEFAULT_OWNER_ADDRESS)
            .contractAddress(DEFAULT_CONTRACT_ADDRESS)
            .fileAddress(DEFAULT_FILE_ADDRESS)
            .actualFile(DEFAULT_ACTUAL_FILE)
            .metadataAddress(DEFAULT_METADATA_ADDRESS)
            .metadata(DEFAULT_METADATA)
            .tile(DEFAULT_TILE)
            .format(DEFAULT_FORMAT)
            .traits(DEFAULT_TRAITS);
        return nfts;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nfts createUpdatedEntity(EntityManager em) {
        Nfts nfts = new Nfts()
            .creatorAddress(UPDATED_CREATOR_ADDRESS)
            .ownerAddress(UPDATED_OWNER_ADDRESS)
            .contractAddress(UPDATED_CONTRACT_ADDRESS)
            .fileAddress(UPDATED_FILE_ADDRESS)
            .actualFile(UPDATED_ACTUAL_FILE)
            .metadataAddress(UPDATED_METADATA_ADDRESS)
            .metadata(UPDATED_METADATA)
            .tile(UPDATED_TILE)
            .format(UPDATED_FORMAT)
            .traits(UPDATED_TRAITS);
        return nfts;
    }

    @BeforeEach
    public void initTest() {
        nfts = createEntity(em);
    }

    @Test
    @Transactional
    void createNfts() throws Exception {
        int databaseSizeBeforeCreate = nftsRepository.findAll().size();
        // Create the Nfts
        NftsDTO nftsDTO = nftsMapper.toDto(nfts);
        restNftsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nftsDTO)))
            .andExpect(status().isCreated());

        // Validate the Nfts in the database
        List<Nfts> nftsList = nftsRepository.findAll();
        assertThat(nftsList).hasSize(databaseSizeBeforeCreate + 1);
        Nfts testNfts = nftsList.get(nftsList.size() - 1);
        assertThat(testNfts.getCreatorAddress()).isEqualTo(DEFAULT_CREATOR_ADDRESS);
        assertThat(testNfts.getOwnerAddress()).isEqualTo(DEFAULT_OWNER_ADDRESS);
        assertThat(testNfts.getContractAddress()).isEqualTo(DEFAULT_CONTRACT_ADDRESS);
        assertThat(testNfts.getFileAddress()).isEqualTo(DEFAULT_FILE_ADDRESS);
        assertThat(testNfts.getActualFile()).isEqualTo(DEFAULT_ACTUAL_FILE);
        assertThat(testNfts.getMetadataAddress()).isEqualTo(DEFAULT_METADATA_ADDRESS);
        assertThat(testNfts.getMetadata()).isEqualTo(DEFAULT_METADATA);
        assertThat(testNfts.getTile()).isEqualTo(DEFAULT_TILE);
        assertThat(testNfts.getFormat()).isEqualTo(DEFAULT_FORMAT);
        assertThat(testNfts.getTraits()).isEqualTo(DEFAULT_TRAITS);
    }

    @Test
    @Transactional
    void createNftsWithExistingId() throws Exception {
        // Create the Nfts with an existing ID
        nfts.setId(1L);
        NftsDTO nftsDTO = nftsMapper.toDto(nfts);

        int databaseSizeBeforeCreate = nftsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNftsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nftsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Nfts in the database
        List<Nfts> nftsList = nftsRepository.findAll();
        assertThat(nftsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNfts() throws Exception {
        // Initialize the database
        nftsRepository.saveAndFlush(nfts);

        // Get all the nftsList
        restNftsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nfts.getId().intValue())))
            .andExpect(jsonPath("$.[*].creatorAddress").value(hasItem(DEFAULT_CREATOR_ADDRESS)))
            .andExpect(jsonPath("$.[*].ownerAddress").value(hasItem(DEFAULT_OWNER_ADDRESS)))
            .andExpect(jsonPath("$.[*].contractAddress").value(hasItem(DEFAULT_CONTRACT_ADDRESS)))
            .andExpect(jsonPath("$.[*].fileAddress").value(hasItem(DEFAULT_FILE_ADDRESS)))
            .andExpect(jsonPath("$.[*].actualFile").value(hasItem(DEFAULT_ACTUAL_FILE)))
            .andExpect(jsonPath("$.[*].metadataAddress").value(hasItem(DEFAULT_METADATA_ADDRESS)))
            .andExpect(jsonPath("$.[*].metadata").value(hasItem(DEFAULT_METADATA)))
            .andExpect(jsonPath("$.[*].tile").value(hasItem(DEFAULT_TILE)))
            .andExpect(jsonPath("$.[*].format").value(hasItem(DEFAULT_FORMAT.toString())))
            .andExpect(jsonPath("$.[*].traits").value(hasItem(DEFAULT_TRAITS)));
    }

    @Test
    @Transactional
    void getNfts() throws Exception {
        // Initialize the database
        nftsRepository.saveAndFlush(nfts);

        // Get the nfts
        restNftsMockMvc
            .perform(get(ENTITY_API_URL_ID, nfts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nfts.getId().intValue()))
            .andExpect(jsonPath("$.creatorAddress").value(DEFAULT_CREATOR_ADDRESS))
            .andExpect(jsonPath("$.ownerAddress").value(DEFAULT_OWNER_ADDRESS))
            .andExpect(jsonPath("$.contractAddress").value(DEFAULT_CONTRACT_ADDRESS))
            .andExpect(jsonPath("$.fileAddress").value(DEFAULT_FILE_ADDRESS))
            .andExpect(jsonPath("$.actualFile").value(DEFAULT_ACTUAL_FILE))
            .andExpect(jsonPath("$.metadataAddress").value(DEFAULT_METADATA_ADDRESS))
            .andExpect(jsonPath("$.metadata").value(DEFAULT_METADATA))
            .andExpect(jsonPath("$.tile").value(DEFAULT_TILE))
            .andExpect(jsonPath("$.format").value(DEFAULT_FORMAT.toString()))
            .andExpect(jsonPath("$.traits").value(DEFAULT_TRAITS));
    }

    @Test
    @Transactional
    void getNonExistingNfts() throws Exception {
        // Get the nfts
        restNftsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingNfts() throws Exception {
        // Initialize the database
        nftsRepository.saveAndFlush(nfts);

        int databaseSizeBeforeUpdate = nftsRepository.findAll().size();

        // Update the nfts
        Nfts updatedNfts = nftsRepository.findById(nfts.getId()).get();
        // Disconnect from session so that the updates on updatedNfts are not directly saved in db
        em.detach(updatedNfts);
        updatedNfts
            .creatorAddress(UPDATED_CREATOR_ADDRESS)
            .ownerAddress(UPDATED_OWNER_ADDRESS)
            .contractAddress(UPDATED_CONTRACT_ADDRESS)
            .fileAddress(UPDATED_FILE_ADDRESS)
            .actualFile(UPDATED_ACTUAL_FILE)
            .metadataAddress(UPDATED_METADATA_ADDRESS)
            .metadata(UPDATED_METADATA)
            .tile(UPDATED_TILE)
            .format(UPDATED_FORMAT)
            .traits(UPDATED_TRAITS);
        NftsDTO nftsDTO = nftsMapper.toDto(updatedNfts);

        restNftsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nftsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nftsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Nfts in the database
        List<Nfts> nftsList = nftsRepository.findAll();
        assertThat(nftsList).hasSize(databaseSizeBeforeUpdate);
        Nfts testNfts = nftsList.get(nftsList.size() - 1);
        assertThat(testNfts.getCreatorAddress()).isEqualTo(UPDATED_CREATOR_ADDRESS);
        assertThat(testNfts.getOwnerAddress()).isEqualTo(UPDATED_OWNER_ADDRESS);
        assertThat(testNfts.getContractAddress()).isEqualTo(UPDATED_CONTRACT_ADDRESS);
        assertThat(testNfts.getFileAddress()).isEqualTo(UPDATED_FILE_ADDRESS);
        assertThat(testNfts.getActualFile()).isEqualTo(UPDATED_ACTUAL_FILE);
        assertThat(testNfts.getMetadataAddress()).isEqualTo(UPDATED_METADATA_ADDRESS);
        assertThat(testNfts.getMetadata()).isEqualTo(UPDATED_METADATA);
        assertThat(testNfts.getTile()).isEqualTo(UPDATED_TILE);
        assertThat(testNfts.getFormat()).isEqualTo(UPDATED_FORMAT);
        assertThat(testNfts.getTraits()).isEqualTo(UPDATED_TRAITS);
    }

    @Test
    @Transactional
    void putNonExistingNfts() throws Exception {
        int databaseSizeBeforeUpdate = nftsRepository.findAll().size();
        nfts.setId(count.incrementAndGet());

        // Create the Nfts
        NftsDTO nftsDTO = nftsMapper.toDto(nfts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNftsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, nftsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nftsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nfts in the database
        List<Nfts> nftsList = nftsRepository.findAll();
        assertThat(nftsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNfts() throws Exception {
        int databaseSizeBeforeUpdate = nftsRepository.findAll().size();
        nfts.setId(count.incrementAndGet());

        // Create the Nfts
        NftsDTO nftsDTO = nftsMapper.toDto(nfts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNftsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(nftsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nfts in the database
        List<Nfts> nftsList = nftsRepository.findAll();
        assertThat(nftsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNfts() throws Exception {
        int databaseSizeBeforeUpdate = nftsRepository.findAll().size();
        nfts.setId(count.incrementAndGet());

        // Create the Nfts
        NftsDTO nftsDTO = nftsMapper.toDto(nfts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNftsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(nftsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nfts in the database
        List<Nfts> nftsList = nftsRepository.findAll();
        assertThat(nftsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNftsWithPatch() throws Exception {
        // Initialize the database
        nftsRepository.saveAndFlush(nfts);

        int databaseSizeBeforeUpdate = nftsRepository.findAll().size();

        // Update the nfts using partial update
        Nfts partialUpdatedNfts = new Nfts();
        partialUpdatedNfts.setId(nfts.getId());

        partialUpdatedNfts
            .contractAddress(UPDATED_CONTRACT_ADDRESS)
            .actualFile(UPDATED_ACTUAL_FILE)
            .metadataAddress(UPDATED_METADATA_ADDRESS)
            .metadata(UPDATED_METADATA);

        restNftsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNfts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNfts))
            )
            .andExpect(status().isOk());

        // Validate the Nfts in the database
        List<Nfts> nftsList = nftsRepository.findAll();
        assertThat(nftsList).hasSize(databaseSizeBeforeUpdate);
        Nfts testNfts = nftsList.get(nftsList.size() - 1);
        assertThat(testNfts.getCreatorAddress()).isEqualTo(DEFAULT_CREATOR_ADDRESS);
        assertThat(testNfts.getOwnerAddress()).isEqualTo(DEFAULT_OWNER_ADDRESS);
        assertThat(testNfts.getContractAddress()).isEqualTo(UPDATED_CONTRACT_ADDRESS);
        assertThat(testNfts.getFileAddress()).isEqualTo(DEFAULT_FILE_ADDRESS);
        assertThat(testNfts.getActualFile()).isEqualTo(UPDATED_ACTUAL_FILE);
        assertThat(testNfts.getMetadataAddress()).isEqualTo(UPDATED_METADATA_ADDRESS);
        assertThat(testNfts.getMetadata()).isEqualTo(UPDATED_METADATA);
        assertThat(testNfts.getTile()).isEqualTo(DEFAULT_TILE);
        assertThat(testNfts.getFormat()).isEqualTo(DEFAULT_FORMAT);
        assertThat(testNfts.getTraits()).isEqualTo(DEFAULT_TRAITS);
    }

    @Test
    @Transactional
    void fullUpdateNftsWithPatch() throws Exception {
        // Initialize the database
        nftsRepository.saveAndFlush(nfts);

        int databaseSizeBeforeUpdate = nftsRepository.findAll().size();

        // Update the nfts using partial update
        Nfts partialUpdatedNfts = new Nfts();
        partialUpdatedNfts.setId(nfts.getId());

        partialUpdatedNfts
            .creatorAddress(UPDATED_CREATOR_ADDRESS)
            .ownerAddress(UPDATED_OWNER_ADDRESS)
            .contractAddress(UPDATED_CONTRACT_ADDRESS)
            .fileAddress(UPDATED_FILE_ADDRESS)
            .actualFile(UPDATED_ACTUAL_FILE)
            .metadataAddress(UPDATED_METADATA_ADDRESS)
            .metadata(UPDATED_METADATA)
            .tile(UPDATED_TILE)
            .format(UPDATED_FORMAT)
            .traits(UPDATED_TRAITS);

        restNftsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNfts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNfts))
            )
            .andExpect(status().isOk());

        // Validate the Nfts in the database
        List<Nfts> nftsList = nftsRepository.findAll();
        assertThat(nftsList).hasSize(databaseSizeBeforeUpdate);
        Nfts testNfts = nftsList.get(nftsList.size() - 1);
        assertThat(testNfts.getCreatorAddress()).isEqualTo(UPDATED_CREATOR_ADDRESS);
        assertThat(testNfts.getOwnerAddress()).isEqualTo(UPDATED_OWNER_ADDRESS);
        assertThat(testNfts.getContractAddress()).isEqualTo(UPDATED_CONTRACT_ADDRESS);
        assertThat(testNfts.getFileAddress()).isEqualTo(UPDATED_FILE_ADDRESS);
        assertThat(testNfts.getActualFile()).isEqualTo(UPDATED_ACTUAL_FILE);
        assertThat(testNfts.getMetadataAddress()).isEqualTo(UPDATED_METADATA_ADDRESS);
        assertThat(testNfts.getMetadata()).isEqualTo(UPDATED_METADATA);
        assertThat(testNfts.getTile()).isEqualTo(UPDATED_TILE);
        assertThat(testNfts.getFormat()).isEqualTo(UPDATED_FORMAT);
        assertThat(testNfts.getTraits()).isEqualTo(UPDATED_TRAITS);
    }

    @Test
    @Transactional
    void patchNonExistingNfts() throws Exception {
        int databaseSizeBeforeUpdate = nftsRepository.findAll().size();
        nfts.setId(count.incrementAndGet());

        // Create the Nfts
        NftsDTO nftsDTO = nftsMapper.toDto(nfts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNftsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, nftsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nftsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nfts in the database
        List<Nfts> nftsList = nftsRepository.findAll();
        assertThat(nftsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNfts() throws Exception {
        int databaseSizeBeforeUpdate = nftsRepository.findAll().size();
        nfts.setId(count.incrementAndGet());

        // Create the Nfts
        NftsDTO nftsDTO = nftsMapper.toDto(nfts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNftsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(nftsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Nfts in the database
        List<Nfts> nftsList = nftsRepository.findAll();
        assertThat(nftsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNfts() throws Exception {
        int databaseSizeBeforeUpdate = nftsRepository.findAll().size();
        nfts.setId(count.incrementAndGet());

        // Create the Nfts
        NftsDTO nftsDTO = nftsMapper.toDto(nfts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNftsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(nftsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Nfts in the database
        List<Nfts> nftsList = nftsRepository.findAll();
        assertThat(nftsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNfts() throws Exception {
        // Initialize the database
        nftsRepository.saveAndFlush(nfts);

        int databaseSizeBeforeDelete = nftsRepository.findAll().size();

        // Delete the nfts
        restNftsMockMvc
            .perform(delete(ENTITY_API_URL_ID, nfts.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nfts> nftsList = nftsRepository.findAll();
        assertThat(nftsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
