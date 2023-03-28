package com.yellowmorty.steamball.repository;

import com.yellowmorty.steamball.domain.Nfts;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Nfts entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NftsRepository extends JpaRepository<Nfts, Long> {}
