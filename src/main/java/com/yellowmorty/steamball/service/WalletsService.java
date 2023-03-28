package com.yellowmorty.steamball.service;

import com.yellowmorty.steamball.domain.Wallets;
import com.yellowmorty.steamball.repository.WalletsRepository;
import com.yellowmorty.steamball.service.dto.WalletsDTO;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Wallets}.
 */
@Service
@Transactional
public class WalletsService {

    private final Logger log = LoggerFactory.getLogger(WalletsService.class);

    private final WalletsRepository walletsRepository;

    //    private final WalletsMapper walletsMapper;

    public WalletsService(WalletsRepository walletsRepository) {
        this.walletsRepository = walletsRepository;
    }

    /**
     * Save a wallets.
     *
     * @param walletsDTO the entity to save.
     * @return the persisted entity.
     */
    public WalletsDTO save(WalletsDTO walletsDTO) {
        log.debug("Request to save Wallets : {}", walletsDTO);
        //        Wallets wallets = walletsMapper.toEntity(walletsDTO);
        //        wallets = walletsRepository.save(wallets);
        //        return walletsMapper.toDto(wallets);
        return walletsDTO;
    }

    /**
     * Update a wallets.
     *
     * @param walletsDTO the entity to save.
     * @return the persisted entity.
     */
    public WalletsDTO update(WalletsDTO walletsDTO) {
        log.debug("Request to update Wallets : {}", walletsDTO);
        //        Wallets wallets = walletsMapper.toEntity(walletsDTO);
        //        wallets = walletsRepository.save(wallets);
        //        return walletsMapper.toDto(wallets);
        return walletsDTO;
    }

    /**
     * Partially update a wallets.
     *
     * @param walletsDTO the entity to update partially.
     * @return the persisted entity.
     */
    //    public Optional<WalletsDTO> partialUpdate(WalletsDTO walletsDTO) {
    //        log.debug("Request to partially update Wallets : {}", walletsDTO);
    //
    //        return walletsRepository
    //            .findById(walletsDTO.getId())
    //            .map(existingWallets -> {
    //                walletsMapper.partialUpdate(existingWallets, walletsDTO);
    //
    //                return existingWallets;
    //            })
    //            .map(walletsRepository::save)
    //            .map(walletsMapper::toDto);
    //    }

    /**
     * Get all the wallets.
     *
     * @return the list of entities.
     */
    //    @Transactional(readOnly = true)
    //    public List<WalletsDTO> findAll() {
    //        log.debug("Request to get all Wallets");
    //        return walletsRepository.findAll().stream().map(walletsMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    //    }

    /**
     * Get one wallets by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    //    @Transactional(readOnly = true)
    //    public Optional<WalletsDTO> findOne(Long id) {
    //        log.debug("Request to get Wallets : {}", id);
    //        return walletsRepository.findById(id).map(walletsMapper::toDto);
    //    }

    /**
     * Delete the wallets by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Wallets : {}", id);
        walletsRepository.deleteById(id);
    }
}
