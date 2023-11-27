package com.yellowmorty.steamball.repository;

import com.yellowmorty.steamball.domain.Wallets;
import com.yellowmorty.steamball.service.dto.WalletsDTO;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Wallets entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WalletsRepository extends JpaRepository<Wallets, Long> {
    Optional<WalletsDTO> findOneByWalletAddress(String publicAddress);
}
