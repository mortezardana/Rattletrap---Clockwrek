package com.yellowmorty.steamball.web.rest;

import com.yellowmorty.steamball.repository.WalletsRepository;
import com.yellowmorty.steamball.service.WalletsService;
import com.yellowmorty.steamball.service.dto.WalletsDTO;
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
 * REST controller for managing {@link com.yellowmorty.steamball.domain.Wallets}.
 */
@RestController
@RequestMapping("/api")
public class WalletsResource {

    private final Logger log = LoggerFactory.getLogger(WalletsResource.class);

    private static final String ENTITY_NAME = "wallets";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WalletsService walletsService;

    private final WalletsRepository walletsRepository;

    public WalletsResource(WalletsService walletsService, WalletsRepository walletsRepository) {
        this.walletsService = walletsService;
        this.walletsRepository = walletsRepository;
    }

    /**
     * {@code POST  /wallets} : Create a new wallets.
     *
     * @param walletsDTO the walletsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new walletsDTO, or with status {@code 400 (Bad Request)} if the wallets has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/wallets")
    public ResponseEntity<WalletsDTO> createWallets(@Valid @RequestBody WalletsDTO walletsDTO) throws URISyntaxException {
        log.debug("REST request to save Wallets : {}", walletsDTO);
        if (walletsDTO.getId() != null) {
            throw new BadRequestAlertException("A new wallets cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WalletsDTO result = walletsService.save(walletsDTO);
        return ResponseEntity
            .created(new URI("/api/wallets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /wallets/:id} : Updates an existing wallets.
     *
     * @param id the id of the walletsDTO to save.
     * @param walletsDTO the walletsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated walletsDTO,
     * or with status {@code 400 (Bad Request)} if the walletsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the walletsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/wallets/{id}")
    public ResponseEntity<WalletsDTO> updateWallets(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WalletsDTO walletsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Wallets : {}, {}", id, walletsDTO);
        if (walletsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, walletsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!walletsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WalletsDTO result = walletsService.update(walletsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, walletsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /wallets/:id} : Partial updates given fields of an existing wallets, field will ignore if it is null
     *
     * @param id the id of the walletsDTO to save.
     * @param walletsDTO the walletsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated walletsDTO,
     * or with status {@code 400 (Bad Request)} if the walletsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the walletsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the walletsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    //    @PatchMapping(value = "/wallets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    //    public ResponseEntity<WalletsDTO> partialUpdateWallets(
    //        @PathVariable(value = "id", required = false) final Long id,
    //        @NotNull @RequestBody WalletsDTO walletsDTO
    //    ) throws URISyntaxException {
    //        log.debug("REST request to partial update Wallets partially : {}, {}", id, walletsDTO);
    //        if (walletsDTO.getId() == null) {
    //            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    //        }
    //        if (!Objects.equals(id, walletsDTO.getId())) {
    //            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    //        }
    //
    //        if (!walletsRepository.existsById(id)) {
    //            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    //        }
    //
    //        Optional<WalletsDTO> result = walletsService.partialUpdate(walletsDTO);
    //
    //        return ResponseUtil.wrapOrNotFound(
    //            result,
    //            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, walletsDTO.getId().toString())
    //        );
    //    }

    /**
     * {@code GET  /wallets} : get all the wallets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of wallets in body.
     */
    //    @GetMapping("/wallets")
    //    public List<WalletsDTO> getAllWallets() {
    //        log.debug("REST request to get all Wallets");
    //        return walletsService.findAll();
    //    }

    /**
     * {@code GET  /wallets/:id} : get the "id" wallets.
     *
     * @param id the id of the walletsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the walletsDTO, or with status {@code 404 (Not Found)}.
     */
    //    @GetMapping("/wallets/{id}")
    //    public ResponseEntity<WalletsDTO> getWallets(@PathVariable Long id) {
    //        log.debug("REST request to get Wallets : {}", id);
    //        Optional<WalletsDTO> walletsDTO = walletsService.findOne(id);
    //        return ResponseUtil.wrapOrNotFound(walletsDTO);
    //    }

    /**
     * {@code DELETE  /wallets/:id} : delete the "id" wallets.
     *
     * @param id the id of the walletsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/wallets/{id}")
    public ResponseEntity<Void> deleteWallets(@PathVariable Long id) {
        log.debug("REST request to delete Wallets : {}", id);
        walletsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
