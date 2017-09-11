package isa.repository;

import isa.domain.Gost;
import isa.domain.User;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Gost entity.
 */
@SuppressWarnings("unused")
@Repository

public interface GostRepository extends JpaRepository<Gost, Long> {
    Optional<Gost> findOneByLogin(String login);
    @Query("select distinct gost from Gost gost left join fetch gost.prijateljis")
    List<Gost> findAllWithEagerRelationships();

    @Query("select gost from Gost gost left join fetch gost.prijateljis where gost.id =:id")
    Gost findOneWithEagerRelationships(@Param("id") Long id);

}
