package com.yellowmorty.steamball.web.rest;

import com.yellowmorty.steamball.repository.GalleriesRepository;
import com.yellowmorty.steamball.service.GalleriesService;
import com.yellowmorty.steamball.service.dto.GalleriesDTO;
import com.yellowmorty.steamball.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.yellowmorty.steamball.domain.Galleries}.
 */
@RestController
@RequestMapping("/api")
public class GalleriesResource {

    private final Logger log = LoggerFactory.getLogger(GalleriesResource.class);

    private static final String ENTITY_NAME = "galleries";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GalleriesService galleriesService;

    private final GalleriesRepository galleriesRepository;

    public GalleriesResource(GalleriesService galleriesService, GalleriesRepository galleriesRepository) {
        this.galleriesService = galleriesService;
        this.galleriesRepository = galleriesRepository;
    }

    /**
     * {@code POST  /galleries} : Create a new galleries.
     *
     * @param galleriesDTO the galleriesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new galleriesDTO, or with status {@code 400 (Bad Request)} if the galleries has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/galleries")
    public ResponseEntity<GalleriesDTO> createGalleries(@Valid @RequestBody GalleriesDTO galleriesDTO) throws URISyntaxException {
        log.debug("REST request to save Galleries : {}", galleriesDTO);
        if (galleriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new galleries cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GalleriesDTO result = galleriesService.save(galleriesDTO);
        return ResponseEntity
            .created(new URI("/api/galleries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /galleries/:id} : Updates an existing galleries.
     *
     * @param id the id of the galleriesDTO to save.
     * @param galleriesDTO the galleriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated galleriesDTO,
     * or with status {@code 400 (Bad Request)} if the galleriesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the galleriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/galleries/{id}")
    public ResponseEntity<GalleriesDTO> updateGalleries(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GalleriesDTO galleriesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Galleries : {}, {}", id, galleriesDTO);
        if (galleriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, galleriesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!galleriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GalleriesDTO result = galleriesService.update(galleriesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, galleriesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /galleries/:id} : Partial updates given fields of an existing galleries, field will ignore if it is null
     *
     * @param id the id of the galleriesDTO to save.
     * @param galleriesDTO the galleriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated galleriesDTO,
     * or with status {@code 400 (Bad Request)} if the galleriesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the galleriesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the galleriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/galleries/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GalleriesDTO> partialUpdateGalleries(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GalleriesDTO galleriesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Galleries partially : {}, {}", id, galleriesDTO);
        if (galleriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, galleriesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!galleriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GalleriesDTO> result = galleriesService.partialUpdate(galleriesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, galleriesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /galleries} : get all the galleries.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of galleries in body.
     */
    @GetMapping("/galleries")
    public List<GalleriesDTO> getAllGalleries(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Galleries");
        return galleriesService.findAll();
    }

    /**
     * {@code GET  /galleries/:id} : get the "id" galleries.
     *
     * @param id the id of the galleriesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the galleriesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/galleries/{id}")
    public ResponseEntity<GalleriesDTO> getGalleries(@PathVariable Long id) {
        log.debug("REST request to get Galleries : {}", id);
        Optional<GalleriesDTO> galleriesDTO = galleriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(galleriesDTO);
    }

    /**
     * {@code DELETE  /galleries/:id} : delete the "id" galleries.
     *
     * @param id the id of the galleriesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/galleries/{id}")
    public ResponseEntity<Void> deleteGalleries(@PathVariable Long id) {
        log.debug("REST request to delete Galleries : {}", id);
        galleriesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
