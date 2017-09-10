package isa.web.rest;

import isa.IsaApp;

import isa.domain.MenadzerSistema;
import isa.repository.MenadzerSistemaRepository;
import isa.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MenadzerSistemaResource REST controller.
 *
 * @see MenadzerSistemaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IsaApp.class)
public class MenadzerSistemaResourceIntTest {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_IME = "AAAAAAAAAA";
    private static final String UPDATED_IME = "BBBBBBBBBB";

    private static final String DEFAULT_PREZIME = "AAAAAAAAAA";
    private static final String UPDATED_PREZIME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private MenadzerSistemaRepository menadzerSistemaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMenadzerSistemaMockMvc;

    private MenadzerSistema menadzerSistema;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MenadzerSistemaResource menadzerSistemaResource = new MenadzerSistemaResource(menadzerSistemaRepository);
        this.restMenadzerSistemaMockMvc = MockMvcBuilders.standaloneSetup(menadzerSistemaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MenadzerSistema createEntity(EntityManager em) {
        MenadzerSistema menadzerSistema = new MenadzerSistema()
            .login(DEFAULT_LOGIN)
            .password(DEFAULT_PASSWORD)
            .ime(DEFAULT_IME)
            .prezime(DEFAULT_PREZIME)
            .email(DEFAULT_EMAIL);
        return menadzerSistema;
    }

    @Before
    public void initTest() {
        menadzerSistema = createEntity(em);
    }

    @Test
    @Transactional
    public void createMenadzerSistema() throws Exception {
        int databaseSizeBeforeCreate = menadzerSistemaRepository.findAll().size();

        // Create the MenadzerSistema
        restMenadzerSistemaMockMvc.perform(post("/api/menadzer-sistemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menadzerSistema)))
            .andExpect(status().isCreated());

        // Validate the MenadzerSistema in the database
        List<MenadzerSistema> menadzerSistemaList = menadzerSistemaRepository.findAll();
        assertThat(menadzerSistemaList).hasSize(databaseSizeBeforeCreate + 1);
        MenadzerSistema testMenadzerSistema = menadzerSistemaList.get(menadzerSistemaList.size() - 1);
        assertThat(testMenadzerSistema.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testMenadzerSistema.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testMenadzerSistema.getIme()).isEqualTo(DEFAULT_IME);
        assertThat(testMenadzerSistema.getPrezime()).isEqualTo(DEFAULT_PREZIME);
        assertThat(testMenadzerSistema.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createMenadzerSistemaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = menadzerSistemaRepository.findAll().size();

        // Create the MenadzerSistema with an existing ID
        menadzerSistema.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenadzerSistemaMockMvc.perform(post("/api/menadzer-sistemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menadzerSistema)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<MenadzerSistema> menadzerSistemaList = menadzerSistemaRepository.findAll();
        assertThat(menadzerSistemaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMenadzerSistemas() throws Exception {
        // Initialize the database
        menadzerSistemaRepository.saveAndFlush(menadzerSistema);

        // Get all the menadzerSistemaList
        restMenadzerSistemaMockMvc.perform(get("/api/menadzer-sistemas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menadzerSistema.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].ime").value(hasItem(DEFAULT_IME.toString())))
            .andExpect(jsonPath("$.[*].prezime").value(hasItem(DEFAULT_PREZIME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getMenadzerSistema() throws Exception {
        // Initialize the database
        menadzerSistemaRepository.saveAndFlush(menadzerSistema);

        // Get the menadzerSistema
        restMenadzerSistemaMockMvc.perform(get("/api/menadzer-sistemas/{id}", menadzerSistema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(menadzerSistema.getId().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.ime").value(DEFAULT_IME.toString()))
            .andExpect(jsonPath("$.prezime").value(DEFAULT_PREZIME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMenadzerSistema() throws Exception {
        // Get the menadzerSistema
        restMenadzerSistemaMockMvc.perform(get("/api/menadzer-sistemas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMenadzerSistema() throws Exception {
        // Initialize the database
        menadzerSistemaRepository.saveAndFlush(menadzerSistema);
        int databaseSizeBeforeUpdate = menadzerSistemaRepository.findAll().size();

        // Update the menadzerSistema
        MenadzerSistema updatedMenadzerSistema = menadzerSistemaRepository.findOne(menadzerSistema.getId());
        updatedMenadzerSistema
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .ime(UPDATED_IME)
            .prezime(UPDATED_PREZIME)
            .email(UPDATED_EMAIL);

        restMenadzerSistemaMockMvc.perform(put("/api/menadzer-sistemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMenadzerSistema)))
            .andExpect(status().isOk());

        // Validate the MenadzerSistema in the database
        List<MenadzerSistema> menadzerSistemaList = menadzerSistemaRepository.findAll();
        assertThat(menadzerSistemaList).hasSize(databaseSizeBeforeUpdate);
        MenadzerSistema testMenadzerSistema = menadzerSistemaList.get(menadzerSistemaList.size() - 1);
        assertThat(testMenadzerSistema.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testMenadzerSistema.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testMenadzerSistema.getIme()).isEqualTo(UPDATED_IME);
        assertThat(testMenadzerSistema.getPrezime()).isEqualTo(UPDATED_PREZIME);
        assertThat(testMenadzerSistema.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingMenadzerSistema() throws Exception {
        int databaseSizeBeforeUpdate = menadzerSistemaRepository.findAll().size();

        // Create the MenadzerSistema

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMenadzerSistemaMockMvc.perform(put("/api/menadzer-sistemas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menadzerSistema)))
            .andExpect(status().isCreated());

        // Validate the MenadzerSistema in the database
        List<MenadzerSistema> menadzerSistemaList = menadzerSistemaRepository.findAll();
        assertThat(menadzerSistemaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMenadzerSistema() throws Exception {
        // Initialize the database
        menadzerSistemaRepository.saveAndFlush(menadzerSistema);
        int databaseSizeBeforeDelete = menadzerSistemaRepository.findAll().size();

        // Get the menadzerSistema
        restMenadzerSistemaMockMvc.perform(delete("/api/menadzer-sistemas/{id}", menadzerSistema.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MenadzerSistema> menadzerSistemaList = menadzerSistemaRepository.findAll();
        assertThat(menadzerSistemaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MenadzerSistema.class);
        MenadzerSistema menadzerSistema1 = new MenadzerSistema();
        menadzerSistema1.setId(1L);
        MenadzerSistema menadzerSistema2 = new MenadzerSistema();
        menadzerSistema2.setId(menadzerSistema1.getId());
        assertThat(menadzerSistema1).isEqualTo(menadzerSistema2);
        menadzerSistema2.setId(2L);
        assertThat(menadzerSistema1).isNotEqualTo(menadzerSistema2);
        menadzerSistema1.setId(null);
        assertThat(menadzerSistema1).isNotEqualTo(menadzerSistema2);
    }
}
