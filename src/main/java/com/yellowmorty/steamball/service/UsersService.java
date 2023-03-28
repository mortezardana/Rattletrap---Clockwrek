package com.yellowmorty.steamball.service;

import com.yellowmorty.steamball.domain.Users;
import com.yellowmorty.steamball.repository.UsersRepository;
import com.yellowmorty.steamball.service.dto.UsersDTO;
import com.yellowmorty.steamball.service.mapper.UsersMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Users}.
 */
@Service
@Transactional
public class UsersService {

    private final Logger log = LoggerFactory.getLogger(UsersService.class);

    private final UsersRepository usersRepository;

    private final UsersMapper usersMapper;

    public UsersService(UsersRepository usersRepository, UsersMapper usersMapper) {
        this.usersRepository = usersRepository;
        this.usersMapper = usersMapper;
    }

    /**
     * Save a users.
     *
     * @param usersDTO the entity to save.
     * @return the persisted entity.
     */
    public UsersDTO save(UsersDTO usersDTO) {
        log.debug("Request to save Users : {}", usersDTO);
        Users users = usersMapper.toEntity(usersDTO);
        users = usersRepository.save(users);
        return usersMapper.toDto(users);
    }

    /**
     * Update a users.
     *
     * @param usersDTO the entity to save.
     * @return the persisted entity.
     */
    public UsersDTO update(UsersDTO usersDTO) {
        log.debug("Request to update Users : {}", usersDTO);
        Users users = usersMapper.toEntity(usersDTO);
        users = usersRepository.save(users);
        return usersMapper.toDto(users);
    }

    /**
     * Partially update a users.
     *
     * @param usersDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UsersDTO> partialUpdate(UsersDTO usersDTO) {
        log.debug("Request to partially update Users : {}", usersDTO);

        return usersRepository
            .findById(usersDTO.getId())
            .map(existingUsers -> {
                usersMapper.partialUpdate(existingUsers, usersDTO);

                return existingUsers;
            })
            .map(usersRepository::save)
            .map(usersMapper::toDto);
    }

    /**
     * Get all the users.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<UsersDTO> findAll() {
        log.debug("Request to get all Users");
        return usersRepository.findAll().stream().map(usersMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one users by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UsersDTO> findOne(Long id) {
        log.debug("Request to get Users : {}", id);
        return usersRepository.findById(id).map(usersMapper::toDto);
    }

    /**
     * Delete the users by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Users : {}", id);
        usersRepository.deleteById(id);
    }
}
