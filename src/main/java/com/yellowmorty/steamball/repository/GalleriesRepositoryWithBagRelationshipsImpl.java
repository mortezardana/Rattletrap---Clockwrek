package com.yellowmorty.steamball.repository;

import com.yellowmorty.steamball.domain.Galleries;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class GalleriesRepositoryWithBagRelationshipsImpl implements GalleriesRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Galleries> fetchBagRelationships(Optional<Galleries> galleries) {
        return galleries.map(this::fetchNfts);
    }

    @Override
    public Page<Galleries> fetchBagRelationships(Page<Galleries> galleries) {
        return new PageImpl<>(fetchBagRelationships(galleries.getContent()), galleries.getPageable(), galleries.getTotalElements());
    }

    @Override
    public List<Galleries> fetchBagRelationships(List<Galleries> galleries) {
        return Optional.of(galleries).map(this::fetchNfts).orElse(Collections.emptyList());
    }

    Galleries fetchNfts(Galleries result) {
        return entityManager
            .createQuery(
                "select galleries from Galleries galleries left join fetch galleries.nfts where galleries is :galleries",
                Galleries.class
            )
            .setParameter("galleries", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Galleries> fetchNfts(List<Galleries> galleries) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, galleries.size()).forEach(index -> order.put(galleries.get(index).getId(), index));
        List<Galleries> result = entityManager
            .createQuery(
                "select distinct galleries from Galleries galleries left join fetch galleries.nfts where galleries in :galleries",
                Galleries.class
            )
            .setParameter("galleries", galleries)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
