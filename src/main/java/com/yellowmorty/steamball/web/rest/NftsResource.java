package com.yellowmorty.steamball.web.rest;

import com.yellowmorty.steamball.repository.NftsRepository;
import com.yellowmorty.steamball.service.NftsService;
import com.yellowmorty.steamball.service.dto.NftsDTO;
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
 * REST controller for managing {@link com.yellowmorty.steamball.domain.Nfts}.
 */
@RestController
@RequestMapping("/api")
public class NftsResource {

    private final Logger log = LoggerFactory.getLogger(NftsResource.class);

    private static final String ENTITY_NAME = "nfts";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NftsService nftsService;

    private final NftsRepository nftsRepository;

    public NftsResource(NftsService nftsService, NftsRepository nftsRepository) {
        this.nftsService = nftsService;
        this.nftsRepository = nftsRepository;
    }

    /**
     * {@code POST  /nfts} : Create a new nfts.
     *
     * @param nftsDTO the nftsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nftsDTO, or with status {@code 400 (Bad Request)} if the nfts has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nfts")
    public ResponseEntity<NftsDTO> createNfts(@Valid @RequestBody NftsDTO nftsDTO) throws URISyntaxException {
        log.debug("REST request to save Nfts : {}", nftsDTO);
        if (nftsDTO.getId() != null) {
            throw new BadRequestAlertException("A new nfts cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NftsDTO result = nftsService.save(nftsDTO);
        return ResponseEntity
            .created(new URI("/api/nfts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nfts/:id} : Updates an existing nfts.
     *
     * @param id the id of the nftsDTO to save.
     * @param nftsDTO the nftsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nftsDTO,
     * or with status {@code 400 (Bad Request)} if the nftsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nftsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nfts/{id}")
    public ResponseEntity<NftsDTO> updateNfts(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody NftsDTO nftsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Nfts : {}, {}", id, nftsDTO);
        if (nftsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nftsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nftsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NftsDTO result = nftsService.update(nftsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nftsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nfts/:id} : Partial updates given fields of an existing nfts, field will ignore if it is null
     *
     * @param id the id of the nftsDTO to save.
     * @param nftsDTO the nftsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nftsDTO,
     * or with status {@code 400 (Bad Request)} if the nftsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the nftsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the nftsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nfts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NftsDTO> partialUpdateNfts(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody NftsDTO nftsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Nfts partially : {}, {}", id, nftsDTO);
        if (nftsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, nftsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!nftsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NftsDTO> result = nftsService.partialUpdate(nftsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nftsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nfts} : get all the nfts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nfts in body.
     */
    @GetMapping("/nfts")
    public List<NftsDTO> getAllNfts() {
        log.debug("REST request to get all Nfts");
        return nftsService.findAll();
    }

    /**
     * {@code GET  /nfts/:id} : get the "id" nfts.
     *
     * @param id the id of the nftsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nftsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nfts/{id}")
    public ResponseEntity<NftsDTO> getNfts(@PathVariable Long id) {
        log.debug("REST request to get Nfts : {}", id);
        Optional<NftsDTO> nftsDTO = nftsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nftsDTO);
    }

    /**
     * {@code DELETE  /nfts/:id} : delete the "id" nfts.
     *
     * @param id the id of the nftsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nfts/{id}")
    public ResponseEntity<Void> deleteNfts(@PathVariable Long id) {
        log.debug("REST request to delete Nfts : {}", id);
        nftsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
