package com.yellowmorty.steamball.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yellowmorty.steamball.IntegrationTest;
import com.yellowmorty.steamball.domain.Wallets;
import com.yellowmorty.steamball.domain.enumeration.WalletType;
import com.yellowmorty.steamball.repository.WalletsRepository;
import com.yellowmorty.steamball.service.dto.WalletsDTO;
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
 * Integration tests for the {@link WalletsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WalletsResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_WALLET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_WALLET_ADDRESS = "BBBBBBBBBB";

    private static final WalletType DEFAULT_WALLET_TYPE = WalletType.ETHEREUM;
    private static final WalletType UPDATED_WALLET_TYPE = WalletType.BITCOINchrome;

    private static final String ENTITY_API_URL = "/api/wallets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WalletsRepository walletsRepository;

    //    @Autowired
    //    private WalletsMapper walletsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWalletsMockMvc;

    private Wallets wallets;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    //    public static Wallets createEntity(EntityManager em) {
    //        Wallets wallets = new Wallets().userId(DEFAULT_USER_ID).walletAddress(DEFAULT_WALLET_ADDRESS).walletType(DEFAULT_WALLET_TYPE);
    //        return wallets;
    //    }
    //
    //    /**
    //     * Create an updated entity for this test.
    //     *
    //     * This is a static method, as tests for other entities might also need it,
    //     * if they test an entity which requires the current entity.
    //     */
    //    public static Wallets createUpdatedEntity(EntityManager em) {
    //        Wallets wallets = new Wallets().userId(UPDATED_USER_ID).walletAddress(UPDATED_WALLET_ADDRESS).walletType(UPDATED_WALLET_TYPE);
    //        return wallets;
    //    }
    //
    //    @BeforeEach
    //    public void initTest() {
    //        wallets = createEntity(em);
    //    }
    //
    //    @Test
    //    @Transactional
    //    void createWallets() throws Exception {
    //        int databaseSizeBeforeCreate = walletsRepository.findAll().size();
    //        // Create the Wallets
    //        WalletsDTO walletsDTO = walletsMapper.toDto(wallets);
    //        restWalletsMockMvc
    //            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(walletsDTO)))
    //            .andExpect(status().isCreated());
    //
    //        // Validate the Wallets in the database
    //        List<Wallets> walletsList = walletsRepository.findAll();
    //        assertThat(walletsList).hasSize(databaseSizeBeforeCreate + 1);
    //        Wallets testWallets = walletsList.get(walletsList.size() - 1);
    //        assertThat(testWallets.getUserId()).isEqualTo(DEFAULT_USER_ID);
    //        assertThat(testWallets.getWalletAddress()).isEqualTo(DEFAULT_WALLET_ADDRESS);
    //        assertThat(testWallets.getWalletType()).isEqualTo(DEFAULT_WALLET_TYPE);
    //    }
    //
    //    @Test
    //    @Transactional
    //    void createWalletsWithExistingId() throws Exception {
    //        // Create the Wallets with an existing ID
    //        wallets.setId(1L);
    //        WalletsDTO walletsDTO = walletsMapper.toDto(wallets);
    //
    //        int databaseSizeBeforeCreate = walletsRepository.findAll().size();
    //
    //        // An entity with an existing ID cannot be created, so this API call must fail
    //        restWalletsMockMvc
    //            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(walletsDTO)))
    //            .andExpect(status().isBadRequest());
    //
    //        // Validate the Wallets in the database
    //        List<Wallets> walletsList = walletsRepository.findAll();
    //        assertThat(walletsList).hasSize(databaseSizeBeforeCreate);
    //    }
    //
    //    @Test
    //    @Transactional
    //    void checkUserIdIsRequired() throws Exception {
    //        int databaseSizeBeforeTest = walletsRepository.findAll().size();
    //        // set the field null
    //        wallets.setUserId(null);
    //
    //        // Create the Wallets, which fails.
    //        WalletsDTO walletsDTO = walletsMapper.toDto(wallets);
    //
    //        restWalletsMockMvc
    //            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(walletsDTO)))
    //            .andExpect(status().isBadRequest());
    //
    //        List<Wallets> walletsList = walletsRepository.findAll();
    //        assertThat(walletsList).hasSize(databaseSizeBeforeTest);
    //    }

    @Test
    @Transactional
    void getAllWallets() throws Exception {
        // Initialize the database
        walletsRepository.saveAndFlush(wallets);

        // Get all the walletsList
        restWalletsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wallets.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].walletAddress").value(hasItem(DEFAULT_WALLET_ADDRESS)))
            .andExpect(jsonPath("$.[*].walletType").value(hasItem(DEFAULT_WALLET_TYPE.toString())));
    }

    @Test
    @Transactional
    void getWallets() throws Exception {
        // Initialize the database
        walletsRepository.saveAndFlush(wallets);

        // Get the wallets
        restWalletsMockMvc
            .perform(get(ENTITY_API_URL_ID, wallets.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wallets.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.walletAddress").value(DEFAULT_WALLET_ADDRESS))
            .andExpect(jsonPath("$.walletType").value(DEFAULT_WALLET_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingWallets() throws Exception {
        // Get the wallets
        restWalletsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    //    @Test
    //    @Transactional
    //    void putExistingWallets() throws Exception {
    //        // Initialize the database
    //        walletsRepository.saveAndFlush(wallets);
    //
    //        int databaseSizeBeforeUpdate = walletsRepository.findAll().size();
    //
    //        // Update the wallets
    //        Wallets updatedWallets = walletsRepository.findById(wallets.getId()).get();
    //        // Disconnect from session so that the updates on updatedWallets are not directly saved in db
    //        em.detach(updatedWallets);
    //        updatedWallets.userId(UPDATED_USER_ID).walletAddress(UPDATED_WALLET_ADDRESS).walletType(UPDATED_WALLET_TYPE);
    //        WalletsDTO walletsDTO = walletsMapper.toDto(updatedWallets);
    //
    //        restWalletsMockMvc
    //            .perform(
    //                put(ENTITY_API_URL_ID, walletsDTO.getId())
    //                    .contentType(MediaType.APPLICATION_JSON)
    //                    .content(TestUtil.convertObjectToJsonBytes(walletsDTO))
    //            )
    //            .andExpect(status().isOk());
    //
    //        // Validate the Wallets in the database
    //        List<Wallets> walletsList = walletsRepository.findAll();
    //        assertThat(walletsList).hasSize(databaseSizeBeforeUpdate);
    //        Wallets testWallets = walletsList.get(walletsList.size() - 1);
    //        assertThat(testWallets.getUserId()).isEqualTo(UPDATED_USER_ID);
    //        assertThat(testWallets.getWalletAddress()).isEqualTo(UPDATED_WALLET_ADDRESS);
    //        assertThat(testWallets.getWalletType()).isEqualTo(UPDATED_WALLET_TYPE);
    //    }
    //
    //    @Test
    //    @Transactional
    //    void putNonExistingWallets() throws Exception {
    //        int databaseSizeBeforeUpdate = walletsRepository.findAll().size();
    //        wallets.setId(count.incrementAndGet());
    //
    //        // Create the Wallets
    //        WalletsDTO walletsDTO = walletsMapper.toDto(wallets);
    //
    //        // If the entity doesn't have an ID, it will throw BadRequestAlertException
    //        restWalletsMockMvc
    //            .perform(
    //                put(ENTITY_API_URL_ID, walletsDTO.getId())
    //                    .contentType(MediaType.APPLICATION_JSON)
    //                    .content(TestUtil.convertObjectToJsonBytes(walletsDTO))
    //            )
    //            .andExpect(status().isBadRequest());
    //
    //        // Validate the Wallets in the database
    //        List<Wallets> walletsList = walletsRepository.findAll();
    //        assertThat(walletsList).hasSize(databaseSizeBeforeUpdate);
    //    }
    //
    //    @Test
    //    @Transactional
    //    void putWithIdMismatchWallets() throws Exception {
    //        int databaseSizeBeforeUpdate = walletsRepository.findAll().size();
    //        wallets.setId(count.incrementAndGet());
    //
    //        // Create the Wallets
    //        WalletsDTO walletsDTO = walletsMapper.toDto(wallets);
    //
    //        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //        restWalletsMockMvc
    //            .perform(
    //                put(ENTITY_API_URL_ID, count.incrementAndGet())
    //                    .contentType(MediaType.APPLICATION_JSON)
    //                    .content(TestUtil.convertObjectToJsonBytes(walletsDTO))
    //            )
    //            .andExpect(status().isBadRequest());
    //
    //        // Validate the Wallets in the database
    //        List<Wallets> walletsList = walletsRepository.findAll();
    //        assertThat(walletsList).hasSize(databaseSizeBeforeUpdate);
    //    }
    //
    //    @Test
    //    @Transactional
    //    void putWithMissingIdPathParamWallets() throws Exception {
    //        int databaseSizeBeforeUpdate = walletsRepository.findAll().size();
    //        wallets.setId(count.incrementAndGet());
    //
    //        // Create the Wallets
    //        WalletsDTO walletsDTO = walletsMapper.toDto(wallets);
    //
    //        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //        restWalletsMockMvc
    //            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(walletsDTO)))
    //            .andExpect(status().isMethodNotAllowed());
    //
    //        // Validate the Wallets in the database
    //        List<Wallets> walletsList = walletsRepository.findAll();
    //        assertThat(walletsList).hasSize(databaseSizeBeforeUpdate);
    //    }

    //    @Test
    //    @Transactional
    //    void partialUpdateWalletsWithPatch() throws Exception {
    //        // Initialize the database
    //        walletsRepository.saveAndFlush(wallets);
    //
    //        int databaseSizeBeforeUpdate = walletsRepository.findAll().size();
    //
    //        // Update the wallets using partial update
    //        Wallets partialUpdatedWallets = new Wallets();
    //        partialUpdatedWallets.setId(wallets.getId());
    //
    //        partialUpdatedWallets.userId(UPDATED_USER_ID).walletType(UPDATED_WALLET_TYPE);
    //
    //        restWalletsMockMvc
    //            .perform(
    //                patch(ENTITY_API_URL_ID, partialUpdatedWallets.getId())
    //                    .contentType("application/merge-patch+json")
    //                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWallets))
    //            )
    //            .andExpect(status().isOk());
    //
    //        // Validate the Wallets in the database
    //        List<Wallets> walletsList = walletsRepository.findAll();
    //        assertThat(walletsList).hasSize(databaseSizeBeforeUpdate);
    //        Wallets testWallets = walletsList.get(walletsList.size() - 1);
    //        assertThat(testWallets.getUserId()).isEqualTo(UPDATED_USER_ID);
    //        assertThat(testWallets.getWalletAddress()).isEqualTo(DEFAULT_WALLET_ADDRESS);
    //        assertThat(testWallets.getWalletType()).isEqualTo(UPDATED_WALLET_TYPE);
    //    }
    //
    //    @Test
    //    @Transactional
    //    void fullUpdateWalletsWithPatch() throws Exception {
    //        // Initialize the database
    //        walletsRepository.saveAndFlush(wallets);
    //
    //        int databaseSizeBeforeUpdate = walletsRepository.findAll().size();
    //
    //        // Update the wallets using partial update
    //        Wallets partialUpdatedWallets = new Wallets();
    //        partialUpdatedWallets.setId(wallets.getId());
    //
    //        partialUpdatedWallets.userId(UPDATED_USER_ID).walletAddress(UPDATED_WALLET_ADDRESS).walletType(UPDATED_WALLET_TYPE);
    //
    //        restWalletsMockMvc
    //            .perform(
    //                patch(ENTITY_API_URL_ID, partialUpdatedWallets.getId())
    //                    .contentType("application/merge-patch+json")
    //                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWallets))
    //            )
    //            .andExpect(status().isOk());
    //
    //        // Validate the Wallets in the database
    //        List<Wallets> walletsList = walletsRepository.findAll();
    //        assertThat(walletsList).hasSize(databaseSizeBeforeUpdate);
    //        Wallets testWallets = walletsList.get(walletsList.size() - 1);
    //        assertThat(testWallets.getUserId()).isEqualTo(UPDATED_USER_ID);
    //        assertThat(testWallets.getWalletAddress()).isEqualTo(UPDATED_WALLET_ADDRESS);
    //        assertThat(testWallets.getWalletType()).isEqualTo(UPDATED_WALLET_TYPE);
    //    }
    //
    //    @Test
    //    @Transactional
    //    void patchNonExistingWallets() throws Exception {
    //        int databaseSizeBeforeUpdate = walletsRepository.findAll().size();
    //        wallets.setId(count.incrementAndGet());
    //
    //        // Create the Wallets
    //        WalletsDTO walletsDTO = walletsMapper.toDto(wallets);
    //
    //        // If the entity doesn't have an ID, it will throw BadRequestAlertException
    //        restWalletsMockMvc
    //            .perform(
    //                patch(ENTITY_API_URL_ID, walletsDTO.getId())
    //                    .contentType("application/merge-patch+json")
    //                    .content(TestUtil.convertObjectToJsonBytes(walletsDTO))
    //            )
    //            .andExpect(status().isBadRequest());
    //
    //        // Validate the Wallets in the database
    //        List<Wallets> walletsList = walletsRepository.findAll();
    //        assertThat(walletsList).hasSize(databaseSizeBeforeUpdate);
    //    }
    //
    //    @Test
    //    @Transactional
    //    void patchWithIdMismatchWallets() throws Exception {
    //        int databaseSizeBeforeUpdate = walletsRepository.findAll().size();
    //        wallets.setId(count.incrementAndGet());
    //
    //        // Create the Wallets
    //        WalletsDTO walletsDTO = walletsMapper.toDto(wallets);
    //
    //        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //        restWalletsMockMvc
    //            .perform(
    //                patch(ENTITY_API_URL_ID, count.incrementAndGet())
    //                    .contentType("application/merge-patch+json")
    //                    .content(TestUtil.convertObjectToJsonBytes(walletsDTO))
    //            )
    //            .andExpect(status().isBadRequest());
    //
    //        // Validate the Wallets in the database
    //        List<Wallets> walletsList = walletsRepository.findAll();
    //        assertThat(walletsList).hasSize(databaseSizeBeforeUpdate);
    //    }
    //
    //    @Test
    //    @Transactional
    //    void patchWithMissingIdPathParamWallets() throws Exception {
    //        int databaseSizeBeforeUpdate = walletsRepository.findAll().size();
    //        wallets.setId(count.incrementAndGet());
    //
    //        // Create the Wallets
    //        WalletsDTO walletsDTO = walletsMapper.toDto(wallets);
    //
    //        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    //        restWalletsMockMvc
    //            .perform(
    //                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(walletsDTO))
    //            )
    //            .andExpect(status().isMethodNotAllowed());
    //
    //        // Validate the Wallets in the database
    //        List<Wallets> walletsList = walletsRepository.findAll();
    //        assertThat(walletsList).hasSize(databaseSizeBeforeUpdate);
    //    }

    @Test
    @Transactional
    void deleteWallets() throws Exception {
        // Initialize the database
        walletsRepository.saveAndFlush(wallets);

        int databaseSizeBeforeDelete = walletsRepository.findAll().size();

        // Delete the wallets
        restWalletsMockMvc
            .perform(delete(ENTITY_API_URL_ID, wallets.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Wallets> walletsList = walletsRepository.findAll();
        assertThat(walletsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
