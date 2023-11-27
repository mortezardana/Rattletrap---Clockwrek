package com.yellowmorty.steamball.repository;

import com.yellowmorty.steamball.domain.Users;
import com.yellowmorty.steamball.domain.Wallets;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Users entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findUsersByWallets(Wallets wallet);
}
