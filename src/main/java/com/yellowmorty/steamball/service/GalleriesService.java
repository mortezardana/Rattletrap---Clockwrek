package com.yellowmorty.steamball.service;

import com.yellowmorty.steamball.domain.Galleries;
import com.yellowmorty.steamball.repository.GalleriesRepository;
import com.yellowmorty.steamball.service.dto.GalleriesDTO;
import com.yellowmorty.steamball.service.mapper.GalleriesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Galleries}.
 */
@Service
@Transactional
public class GalleriesService {

    private final Logger log = LoggerFactory.getLogger(GalleriesService.class);

    private final GalleriesRepository galleriesRepository;

    private final GalleriesMapper galleriesMapper;

    public GalleriesService(GalleriesRepository galleriesRepository, GalleriesMapper galleriesMapper) {
        this.galleriesRepository = galleriesRepository;
        this.galleriesMapper = galleriesMapper;
    }

    /**
     * Save a galleries.
     *
     * @param galleriesDTO the entity to save.
     * @return the persisted entity.
     */
    public GalleriesDTO save(GalleriesDTO galleriesDTO) {
        log.debug("Request to save Galleries : {}", galleriesDTO);
        Galleries galleries = galleriesMapper.toEntity(galleriesDTO);
        galleries = galleriesRepository.save(galleries);
        return galleriesMapper.toDto(galleries);
    }

    /**
     * Update a galleries.
     *
     * @param galleriesDTO the entity to save.
     * @return the persisted entity.
     */
    public GalleriesDTO update(GalleriesDTO galleriesDTO) {
        log.debug("Request to update Galleries : {}", galleriesDTO);
        Galleries galleries = galleriesMapper.toEntity(galleriesDTO);
        galleries = galleriesRepository.save(galleries);
        return galleriesMapper.toDto(galleries);
    }

    /**
     * Partially update a galleries.
     *
     * @param galleriesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<GalleriesDTO> partialUpdate(GalleriesDTO galleriesDTO) {
        log.debug("Request to partially update Galleries : {}", galleriesDTO);

        return galleriesRepository
            .findById(galleriesDTO.getId())
            .map(existingGalleries -> {
                galleriesMapper.partialUpdate(existingGalleries, galleriesDTO);

                return existingGalleries;
            })
            .map(galleriesRepository::save)
            .map(galleriesMapper::toDto);
    }

    /**
     * Get all the galleries.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GalleriesDTO> findAll() {
        log.debug("Request to get all Galleries");
        return galleriesRepository.findAll().stream().map(galleriesMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the galleries with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<GalleriesDTO> findAllWithEagerRelationships(Pageable pageable) {
        return galleriesRepository.findAllWithEagerRelationships(pageable).map(galleriesMapper::toDto);
    }

    /**
     * Get one galleries by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GalleriesDTO> findOne(Long id) {
        log.debug("Request to get Galleries : {}", id);
        return galleriesRepository.findOneWithEagerRelationships(id).map(galleriesMapper::toDto);
    }

    /**
     * Delete the galleries by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Galleries : {}", id);
        galleriesRepository.deleteById(id);
    }
}
