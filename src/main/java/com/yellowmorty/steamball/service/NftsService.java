package com.yellowmorty.steamball.service;

import com.yellowmorty.steamball.domain.Nfts;
import com.yellowmorty.steamball.repository.NftsRepository;
import com.yellowmorty.steamball.service.dto.NftsDTO;
import com.yellowmorty.steamball.service.mapper.NftsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Nfts}.
 */
@Service
@Transactional
public class NftsService {

    private final Logger log = LoggerFactory.getLogger(NftsService.class);

    private final NftsRepository nftsRepository;

    private final NftsMapper nftsMapper;

    public NftsService(NftsRepository nftsRepository, NftsMapper nftsMapper) {
        this.nftsRepository = nftsRepository;
        this.nftsMapper = nftsMapper;
    }

    /**
     * Save a nfts.
     *
     * @param nftsDTO the entity to save.
     * @return the persisted entity.
     */
    public NftsDTO save(NftsDTO nftsDTO) {
        log.debug("Request to save Nfts : {}", nftsDTO);
        Nfts nfts = nftsMapper.toEntity(nftsDTO);
        nfts = nftsRepository.save(nfts);
        return nftsMapper.toDto(nfts);
    }

    /**
     * Update a nfts.
     *
     * @param nftsDTO the entity to save.
     * @return the persisted entity.
     */
    public NftsDTO update(NftsDTO nftsDTO) {
        log.debug("Request to update Nfts : {}", nftsDTO);
        Nfts nfts = nftsMapper.toEntity(nftsDTO);
        nfts = nftsRepository.save(nfts);
        return nftsMapper.toDto(nfts);
    }

    /**
     * Partially update a nfts.
     *
     * @param nftsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<NftsDTO> partialUpdate(NftsDTO nftsDTO) {
        log.debug("Request to partially update Nfts : {}", nftsDTO);

        return nftsRepository
            .findById(nftsDTO.getId())
            .map(existingNfts -> {
                nftsMapper.partialUpdate(existingNfts, nftsDTO);

                return existingNfts;
            })
            .map(nftsRepository::save)
            .map(nftsMapper::toDto);
    }

    /**
     * Get all the nfts.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<NftsDTO> findAll() {
        log.debug("Request to get all Nfts");
        return nftsRepository.findAll().stream().map(nftsMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one nfts by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NftsDTO> findOne(Long id) {
        log.debug("Request to get Nfts : {}", id);
        return nftsRepository.findById(id).map(nftsMapper::toDto);
    }

    /**
     * Delete the nfts by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Nfts : {}", id);
        nftsRepository.deleteById(id);
    }
}
