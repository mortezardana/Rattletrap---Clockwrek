package com.yellowmorty.steamball.repository;

import com.yellowmorty.steamball.domain.Galleries;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface GalleriesRepositoryWithBagRelationships {
    Optional<Galleries> fetchBagRelationships(Optional<Galleries> galleries);

    List<Galleries> fetchBagRelationships(List<Galleries> galleries);

    Page<Galleries> fetchBagRelationships(Page<Galleries> galleries);
}
