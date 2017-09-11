package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.Authority;
import isa.domain.Ponudjac;

import isa.domain.User;
import isa.repository.AuthorityRepository;
import isa.repository.PonudjacRepository;
import isa.repository.UserRepository;
import isa.security.AuthoritiesConstants;
import isa.security.SecurityUtils;
import isa.service.util.RandomUtil;
import isa.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Ponudjac.
 */
@RestController
@RequestMapping("/api")
public class PonudjacResource {

    private final Logger log = LoggerFactory.getLogger(PonudjacResource.class);

    private static final String ENTITY_NAME = "ponudjac";

    private final PonudjacRepository ponudjacRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public PonudjacResource(PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, UserRepository userRepository,  PonudjacRepository ponudjacRepository) {
        this.ponudjacRepository = ponudjacRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * POST  /ponudjacs : Create a new ponudjac.
     *
     * @param ponudjac the ponudjac to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ponudjac, or with status 400 (Bad Request) if the ponudjac has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ponudjacs")
    @Timed
    public ResponseEntity<Ponudjac> createPonudjac(@RequestBody Ponudjac ponudjac) throws URISyntaxException {
        log.debug("REST request to save Ponudjac : {}", ponudjac);
        if (ponudjac.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ponudjac cannot already have an ID")).body(null);
        }
        User newUser = new User();
        Authority authority = authorityRepository.findOne(AuthoritiesConstants.PONUDJAC);
        Set<Authority> authorities = new HashSet<>();
        String encryptedPassword = passwordEncoder.encode("menadzersistema");
        newUser.setLogin(ponudjac.getLogin());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(ponudjac.getIme());
        newUser.setLastName(ponudjac.getPrezime());
        newUser.setEmail(ponudjac.getEmail());
        newUser.setLangKey("en");

        newUser.setImageUrl("");

        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        User user = userRepository.save(newUser);
        ponudjac.setUserID(user);
        Ponudjac result = ponudjacRepository.save(ponudjac);
        return ResponseEntity.created(new URI("/api/ponudjacs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);

    }

    /**
     * PUT  /ponudjacs : Updates an existing ponudjac.
     *
     * @param ponudjac the ponudjac to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ponudjac,
     * or with status 400 (Bad Request) if the ponudjac is not valid,
     * or with status 500 (Internal Server Error) if the ponudjac couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ponudjacs")
    @Timed
    public ResponseEntity<Ponudjac> updatePonudjac(@RequestBody Ponudjac ponudjac) throws URISyntaxException {
        log.debug("REST request to update Ponudjac : {}", ponudjac);
        if (ponudjac.getId() == null) {
            return createPonudjac(ponudjac);
        }
        Ponudjac result = ponudjacRepository.save(ponudjac);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ponudjac.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ponudjacs : get all the ponudjacs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ponudjacs in body
     */
    @GetMapping("/ponudjacs")
    @Timed
    public List<Ponudjac> getAllPonudjacs() {
        log.debug("REST request to get all Ponudjacs");
        return ponudjacRepository.findAll();
        }

    /**
     * GET  /ponudjacs/:id : get the "id" ponudjac.
     *
     * @param id the id of the ponudjac to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ponudjac, or with status 404 (Not Found)
     */
    @GetMapping("/ponudjacs/{id}")
    @Timed
    public ResponseEntity<Ponudjac> getPonudjac(@PathVariable Long id) {
        log.debug("REST request to get Ponudjac : {}", id);
        Ponudjac ponudjac = ponudjacRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ponudjac));
    }

    /**
     * DELETE  /ponudjacs/:id : delete the "id" ponudjac.
     *
     * @param id the id of the ponudjac to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ponudjacs/{id}")
    @Timed
    public ResponseEntity<Void> deletePonudjac(@PathVariable Long id) {
        log.debug("REST request to delete Ponudjac : {}", id);
        ponudjacRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
