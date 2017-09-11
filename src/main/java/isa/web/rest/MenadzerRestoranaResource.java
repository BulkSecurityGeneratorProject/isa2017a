package isa.web.rest;

import com.codahale.metrics.annotation.Timed;
import isa.domain.Authority;
import isa.domain.MenadzerRestorana;

import isa.domain.User;
import isa.repository.AuthorityRepository;
import isa.repository.MenadzerRestoranaRepository;
import isa.repository.UserRepository;
import isa.security.AuthoritiesConstants;
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
 * REST controller for managing MenadzerRestorana.
 */
@RestController
@RequestMapping("/api")
public class MenadzerRestoranaResource {

    private final Logger log = LoggerFactory.getLogger(MenadzerRestoranaResource.class);

    private static final String ENTITY_NAME = "menadzerRestorana";

    private final MenadzerRestoranaRepository menadzerRestoranaRepository;

    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    public MenadzerRestoranaResource(PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, UserRepository userRepository,MenadzerRestoranaRepository menadzerRestoranaRepository) {
        this.menadzerRestoranaRepository = menadzerRestoranaRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * POST  /menadzer-restoranas : Create a new menadzerRestorana.
     *
     * @param menadzerRestorana the menadzerRestorana to create
     * @return the ResponseEntity with status 201 (Created) and with body the new menadzerRestorana, or with status 400 (Bad Request) if the menadzerRestorana has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/menadzer-restoranas")
    @Timed
    public ResponseEntity<MenadzerRestorana> createMenadzerRestorana(@RequestBody MenadzerRestorana menadzerRestorana) throws URISyntaxException {
        log.debug("REST request to save MenadzerRestorana : {}", menadzerRestorana);
        if (menadzerRestorana.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new menadzerRestorana cannot already have an ID")).body(null);
        }
        User newUser = new User();
        Authority authority = authorityRepository.findOne(AuthoritiesConstants.MENADZER_RESTORANA);
        Set<Authority> authorities = new HashSet<>();
        String encryptedPassword = passwordEncoder.encode("menadzersistema");
        newUser.setLogin(menadzerRestorana.getLogin());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(menadzerRestorana.getIme());
        newUser.setLastName(menadzerRestorana.getPrezime());
        newUser.setEmail(menadzerRestorana.getEmail());
        newUser.setLangKey("en");

        newUser.setImageUrl("");

        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        User user = userRepository.save(newUser);
        menadzerRestorana.setUserID(user);


        MenadzerRestorana result = menadzerRestoranaRepository.save(menadzerRestorana);
        return ResponseEntity.created(new URI("/api/menadzer-restoranas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /menadzer-restoranas : Updates an existing menadzerRestorana.
     *
     * @param menadzerRestorana the menadzerRestorana to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated menadzerRestorana,
     * or with status 400 (Bad Request) if the menadzerRestorana is not valid,
     * or with status 500 (Internal Server Error) if the menadzerRestorana couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/menadzer-restoranas")
    @Timed
    public ResponseEntity<MenadzerRestorana> updateMenadzerRestorana(@RequestBody MenadzerRestorana menadzerRestorana) throws URISyntaxException {
        log.debug("REST request to update MenadzerRestorana : {}", menadzerRestorana);
        if (menadzerRestorana.getId() == null) {
            return createMenadzerRestorana(menadzerRestorana);
        }
        MenadzerRestorana result = menadzerRestoranaRepository.save(menadzerRestorana);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, menadzerRestorana.getId().toString()))
            .body(result);
    }

    /**
     * GET  /menadzer-restoranas : get all the menadzerRestoranas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of menadzerRestoranas in body
     */
    @GetMapping("/menadzer-restoranas")
    @Timed
    public List<MenadzerRestorana> getAllMenadzerRestoranas() {
        log.debug("REST request to get all MenadzerRestoranas");
        return menadzerRestoranaRepository.findAll();
        }

    /**
     * GET  /menadzer-restoranas/:id : get the "id" menadzerRestorana.
     *
     * @param id the id of the menadzerRestorana to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the menadzerRestorana, or with status 404 (Not Found)
     */
    @GetMapping("/menadzer-restoranas/{id}")
    @Timed
    public ResponseEntity<MenadzerRestorana> getMenadzerRestorana(@PathVariable Long id) {
        log.debug("REST request to get MenadzerRestorana : {}", id);
        MenadzerRestorana menadzerRestorana = menadzerRestoranaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(menadzerRestorana));
    }

    /**
     * DELETE  /menadzer-restoranas/:id : delete the "id" menadzerRestorana.
     *
     * @param id the id of the menadzerRestorana to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/menadzer-restoranas/{id}")
    @Timed
    public ResponseEntity<Void> deleteMenadzerRestorana(@PathVariable Long id) {
        log.debug("REST request to delete MenadzerRestorana : {}", id);
        menadzerRestoranaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
