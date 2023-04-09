package com.yellowmorty.steamball.repository;

import com.yellowmorty.steamball.domain.Wallets;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Wallets entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WalletsRepository extends JpaRepository<Wallets, Long> {
    Optional<Wallets> findOneByWalletAddress(String publicAddress);
}
