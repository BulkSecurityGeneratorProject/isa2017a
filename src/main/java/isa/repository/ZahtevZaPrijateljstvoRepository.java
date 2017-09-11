package isa.repository;

import isa.domain.ZahtevZaPrijateljstvo;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;

import java.awt.print.Pageable;


/**
 * Spring Data JPA repository for the ZahtevZaPrijateljstvo entity.
 */
@SuppressWarnings("unused")

public interface ZahtevZaPrijateljstvoRepository extends JpaRepository<ZahtevZaPrijateljstvo, Long> {
    //@Query("select zahtevZaPrijateljstvo from ZahtevZaPrijateljstvo zahtevZaPrijateljstvo  where zahtevZaPrijateljstvo.idPodnosiocaZahteva =:id")
    //Page<ZahtevZaPrijateljstvo> findByUserIsCurrentUser(Pageable pageable);
}
