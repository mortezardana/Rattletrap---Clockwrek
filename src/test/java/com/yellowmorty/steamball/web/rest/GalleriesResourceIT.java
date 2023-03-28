package com.yellowmorty.steamball.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.yellowmorty.steamball.IntegrationTest;
import com.yellowmorty.steamball.domain.Galleries;
import com.yellowmorty.steamball.repository.GalleriesRepository;
import com.yellowmorty.steamball.service.GalleriesService;
import com.yellowmorty.steamball.service.dto.GalleriesDTO;
import com.yellowmorty.steamball.service.mapper.GalleriesMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link GalleriesResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class GalleriesResourceIT {

    private static final Long DEFAULT_CREATOR = 1L;
    private static final Long UPDATED_CREATOR = 2L;

    private static final String DEFAULT_NFTS = "AAAAAAAAAA";
    private static final String UPDATED_NFTS = "BBBBBBBBBB";

    private static final String DEFAULT_LIKES = "AAAAAAAAAA";
    private static final String UPDATED_LIKES = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/galleries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GalleriesRepository galleriesRepository;

    @Mock
    private GalleriesRepository galleriesRepositoryMock;

    @Autowired
    private GalleriesMapper galleriesMapper;

    @Mock
    private GalleriesService galleriesServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGalleriesMockMvc;

    private Galleries galleries;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Galleries createEntity(EntityManager em) {
        Galleries galleries = new Galleries().creator(DEFAULT_CREATOR).nfts(DEFAULT_NFTS).likes(DEFAULT_LIKES).comments(DEFAULT_COMMENTS);
        return galleries;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Galleries createUpdatedEntity(EntityManager em) {
        Galleries galleries = new Galleries().creator(UPDATED_CREATOR).nfts(UPDATED_NFTS).likes(UPDATED_LIKES).comments(UPDATED_COMMENTS);
        return galleries;
    }

    @BeforeEach
    public void initTest() {
        galleries = createEntity(em);
    }

    @Test
    @Transactional
    void createGalleries() throws Exception {
        int databaseSizeBeforeCreate = galleriesRepository.findAll().size();
        // Create the Galleries
        GalleriesDTO galleriesDTO = galleriesMapper.toDto(galleries);
        restGalleriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(galleriesDTO)))
            .andExpect(status().isCreated());

        // Validate the Galleries in the database
        List<Galleries> galleriesList = galleriesRepository.findAll();
        assertThat(galleriesList).hasSize(databaseSizeBeforeCreate + 1);
        Galleries testGalleries = galleriesList.get(galleriesList.size() - 1);
        assertThat(testGalleries.getCreator()).isEqualTo(DEFAULT_CREATOR);
        assertThat(testGalleries.getNfts()).isEqualTo(DEFAULT_NFTS);
        assertThat(testGalleries.getLikes()).isEqualTo(DEFAULT_LIKES);
        assertThat(testGalleries.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    void createGalleriesWithExistingId() throws Exception {
        // Create the Galleries with an existing ID
        galleries.setId(1L);
        GalleriesDTO galleriesDTO = galleriesMapper.toDto(galleries);

        int databaseSizeBeforeCreate = galleriesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGalleriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(galleriesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Galleries in the database
        List<Galleries> galleriesList = galleriesRepository.findAll();
        assertThat(galleriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCreatorIsRequired() throws Exception {
        int databaseSizeBeforeTest = galleriesRepository.findAll().size();
        // set the field null
        galleries.setCreator(null);

        // Create the Galleries, which fails.
        GalleriesDTO galleriesDTO = galleriesMapper.toDto(galleries);

        restGalleriesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(galleriesDTO)))
            .andExpect(status().isBadRequest());

        List<Galleries> galleriesList = galleriesRepository.findAll();
        assertThat(galleriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGalleries() throws Exception {
        // Initialize the database
        galleriesRepository.saveAndFlush(galleries);

        // Get all the galleriesList
        restGalleriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(galleries.getId().intValue())))
            .andExpect(jsonPath("$.[*].creator").value(hasItem(DEFAULT_CREATOR.intValue())))
            .andExpect(jsonPath("$.[*].nfts").value(hasItem(DEFAULT_NFTS)))
            .andExpect(jsonPath("$.[*].likes").value(hasItem(DEFAULT_LIKES)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGalleriesWithEagerRelationshipsIsEnabled() throws Exception {
        when(galleriesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGalleriesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(galleriesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllGalleriesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(galleriesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restGalleriesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(galleriesRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getGalleries() throws Exception {
        // Initialize the database
        galleriesRepository.saveAndFlush(galleries);

        // Get the galleries
        restGalleriesMockMvc
            .perform(get(ENTITY_API_URL_ID, galleries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(galleries.getId().intValue()))
            .andExpect(jsonPath("$.creator").value(DEFAULT_CREATOR.intValue()))
            .andExpect(jsonPath("$.nfts").value(DEFAULT_NFTS))
            .andExpect(jsonPath("$.likes").value(DEFAULT_LIKES))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }

    @Test
    @Transactional
    void getNonExistingGalleries() throws Exception {
        // Get the galleries
        restGalleriesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingGalleries() throws Exception {
        // Initialize the database
        galleriesRepository.saveAndFlush(galleries);

        int databaseSizeBeforeUpdate = galleriesRepository.findAll().size();

        // Update the galleries
        Galleries updatedGalleries = galleriesRepository.findById(galleries.getId()).get();
        // Disconnect from session so that the updates on updatedGalleries are not directly saved in db
        em.detach(updatedGalleries);
        updatedGalleries.creator(UPDATED_CREATOR).nfts(UPDATED_NFTS).likes(UPDATED_LIKES).comments(UPDATED_COMMENTS);
        GalleriesDTO galleriesDTO = galleriesMapper.toDto(updatedGalleries);

        restGalleriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, galleriesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(galleriesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Galleries in the database
        List<Galleries> galleriesList = galleriesRepository.findAll();
        assertThat(galleriesList).hasSize(databaseSizeBeforeUpdate);
        Galleries testGalleries = galleriesList.get(galleriesList.size() - 1);
        assertThat(testGalleries.getCreator()).isEqualTo(UPDATED_CREATOR);
        assertThat(testGalleries.getNfts()).isEqualTo(UPDATED_NFTS);
        assertThat(testGalleries.getLikes()).isEqualTo(UPDATED_LIKES);
        assertThat(testGalleries.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void putNonExistingGalleries() throws Exception {
        int databaseSizeBeforeUpdate = galleriesRepository.findAll().size();
        galleries.setId(count.incrementAndGet());

        // Create the Galleries
        GalleriesDTO galleriesDTO = galleriesMapper.toDto(galleries);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGalleriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, galleriesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(galleriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Galleries in the database
        List<Galleries> galleriesList = galleriesRepository.findAll();
        assertThat(galleriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGalleries() throws Exception {
        int databaseSizeBeforeUpdate = galleriesRepository.findAll().size();
        galleries.setId(count.incrementAndGet());

        // Create the Galleries
        GalleriesDTO galleriesDTO = galleriesMapper.toDto(galleries);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGalleriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(galleriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Galleries in the database
        List<Galleries> galleriesList = galleriesRepository.findAll();
        assertThat(galleriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGalleries() throws Exception {
        int databaseSizeBeforeUpdate = galleriesRepository.findAll().size();
        galleries.setId(count.incrementAndGet());

        // Create the Galleries
        GalleriesDTO galleriesDTO = galleriesMapper.toDto(galleries);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGalleriesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(galleriesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Galleries in the database
        List<Galleries> galleriesList = galleriesRepository.findAll();
        assertThat(galleriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGalleriesWithPatch() throws Exception {
        // Initialize the database
        galleriesRepository.saveAndFlush(galleries);

        int databaseSizeBeforeUpdate = galleriesRepository.findAll().size();

        // Update the galleries using partial update
        Galleries partialUpdatedGalleries = new Galleries();
        partialUpdatedGalleries.setId(galleries.getId());

        partialUpdatedGalleries.creator(UPDATED_CREATOR).nfts(UPDATED_NFTS);

        restGalleriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGalleries.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGalleries))
            )
            .andExpect(status().isOk());

        // Validate the Galleries in the database
        List<Galleries> galleriesList = galleriesRepository.findAll();
        assertThat(galleriesList).hasSize(databaseSizeBeforeUpdate);
        Galleries testGalleries = galleriesList.get(galleriesList.size() - 1);
        assertThat(testGalleries.getCreator()).isEqualTo(UPDATED_CREATOR);
        assertThat(testGalleries.getNfts()).isEqualTo(UPDATED_NFTS);
        assertThat(testGalleries.getLikes()).isEqualTo(DEFAULT_LIKES);
        assertThat(testGalleries.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    void fullUpdateGalleriesWithPatch() throws Exception {
        // Initialize the database
        galleriesRepository.saveAndFlush(galleries);

        int databaseSizeBeforeUpdate = galleriesRepository.findAll().size();

        // Update the galleries using partial update
        Galleries partialUpdatedGalleries = new Galleries();
        partialUpdatedGalleries.setId(galleries.getId());

        partialUpdatedGalleries.creator(UPDATED_CREATOR).nfts(UPDATED_NFTS).likes(UPDATED_LIKES).comments(UPDATED_COMMENTS);

        restGalleriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGalleries.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGalleries))
            )
            .andExpect(status().isOk());

        // Validate the Galleries in the database
        List<Galleries> galleriesList = galleriesRepository.findAll();
        assertThat(galleriesList).hasSize(databaseSizeBeforeUpdate);
        Galleries testGalleries = galleriesList.get(galleriesList.size() - 1);
        assertThat(testGalleries.getCreator()).isEqualTo(UPDATED_CREATOR);
        assertThat(testGalleries.getNfts()).isEqualTo(UPDATED_NFTS);
        assertThat(testGalleries.getLikes()).isEqualTo(UPDATED_LIKES);
        assertThat(testGalleries.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    void patchNonExistingGalleries() throws Exception {
        int databaseSizeBeforeUpdate = galleriesRepository.findAll().size();
        galleries.setId(count.incrementAndGet());

        // Create the Galleries
        GalleriesDTO galleriesDTO = galleriesMapper.toDto(galleries);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGalleriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, galleriesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(galleriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Galleries in the database
        List<Galleries> galleriesList = galleriesRepository.findAll();
        assertThat(galleriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGalleries() throws Exception {
        int databaseSizeBeforeUpdate = galleriesRepository.findAll().size();
        galleries.setId(count.incrementAndGet());

        // Create the Galleries
        GalleriesDTO galleriesDTO = galleriesMapper.toDto(galleries);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGalleriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(galleriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Galleries in the database
        List<Galleries> galleriesList = galleriesRepository.findAll();
        assertThat(galleriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGalleries() throws Exception {
        int databaseSizeBeforeUpdate = galleriesRepository.findAll().size();
        galleries.setId(count.incrementAndGet());

        // Create the Galleries
        GalleriesDTO galleriesDTO = galleriesMapper.toDto(galleries);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGalleriesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(galleriesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Galleries in the database
        List<Galleries> galleriesList = galleriesRepository.findAll();
        assertThat(galleriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGalleries() throws Exception {
        // Initialize the database
        galleriesRepository.saveAndFlush(galleries);

        int databaseSizeBeforeDelete = galleriesRepository.findAll().size();

        // Delete the galleries
        restGalleriesMockMvc
            .perform(delete(ENTITY_API_URL_ID, galleries.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Galleries> galleriesList = galleriesRepository.findAll();
        assertThat(galleriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
