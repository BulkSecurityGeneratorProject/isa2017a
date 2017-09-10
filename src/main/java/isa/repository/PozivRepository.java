package isa.repository;

import isa.domain.Poziv;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Poziv entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PozivRepository extends JpaRepository<Poziv, Long> {

}
