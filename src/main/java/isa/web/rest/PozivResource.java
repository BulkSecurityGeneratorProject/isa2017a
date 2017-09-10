package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.Poziv;

import isa.repository.PozivRepository;
import isa.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Poziv.
 */
@RestController
@RequestMapping("/api")
public class PozivResource {

    private final Logger log = LoggerFactory.getLogger(PozivResource.class);

    private static final String ENTITY_NAME = "poziv";

    private final PozivRepository pozivRepository;
    public PozivResource(PozivRepository pozivRepository) {
        this.pozivRepository = pozivRepository;
    }

    /**
     * POST  /pozivs : Create a new poziv.
     *
     * @param poziv the poziv to create
     * @return the ResponseEntity with status 201 (Created) and with body the new poziv, or with status 400 (Bad Request) if the poziv has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pozivs")
    @Timed
    public ResponseEntity<Poziv> createPoziv(@RequestBody Poziv poziv) throws URISyntaxException {
        log.debug("REST request to save Poziv : {}", poziv);
        if (poziv.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new poziv cannot already have an ID")).body(null);
        }
        Poziv result = pozivRepository.save(poziv);
        return ResponseEntity.created(new URI("/api/pozivs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pozivs : Updates an existing poziv.
     *
     * @param poziv the poziv to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated poziv,
     * or with status 400 (Bad Request) if the poziv is not valid,
     * or with status 500 (Internal Server Error) if the poziv couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pozivs")
    @Timed
    public ResponseEntity<Poziv> updatePoziv(@RequestBody Poziv poziv) throws URISyntaxException {
        log.debug("REST request to update Poziv : {}", poziv);
        if (poziv.getId() == null) {
            return createPoziv(poziv);
        }
        Poziv result = pozivRepository.save(poziv);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, poziv.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pozivs : get all the pozivs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pozivs in body
     */
    @GetMapping("/pozivs")
    @Timed
    public List<Poziv> getAllPozivs() {
        log.debug("REST request to get all Pozivs");
        return pozivRepository.findAll();
        }

    /**
     * GET  /pozivs/:id : get the "id" poziv.
     *
     * @param id the id of the poziv to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the poziv, or with status 404 (Not Found)
     */
    @GetMapping("/pozivs/{id}")
    @Timed
    public ResponseEntity<Poziv> getPoziv(@PathVariable Long id) {
        log.debug("REST request to get Poziv : {}", id);
        Poziv poziv = pozivRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(poziv));
    }

    /**
     * DELETE  /pozivs/:id : delete the "id" poziv.
     *
     * @param id the id of the poziv to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pozivs/{id}")
    @Timed
    public ResponseEntity<Void> deletePoziv(@PathVariable Long id) {
        log.debug("REST request to delete Poziv : {}", id);
        pozivRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
