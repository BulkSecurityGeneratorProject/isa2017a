package isa.web.rest;

import isa.IsaApp;

import isa.domain.Ponudjac;
import isa.repository.PonudjacRepository;
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
 * Test class for the PonudjacResource REST controller.
 *
 * @see PonudjacResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IsaApp.class)
public class PonudjacResourceIntTest {

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
    private PonudjacRepository ponudjacRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPonudjacMockMvc;

    private Ponudjac ponudjac;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PonudjacResource ponudjacResource = new PonudjacResource(ponudjacRepository);
        this.restPonudjacMockMvc = MockMvcBuilders.standaloneSetup(ponudjacResource)
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
    public static Ponudjac createEntity(EntityManager em) {
        Ponudjac ponudjac = new Ponudjac()
            .login(DEFAULT_LOGIN)
            .password(DEFAULT_PASSWORD)
            .ime(DEFAULT_IME)
            .prezime(DEFAULT_PREZIME)
            .email(DEFAULT_EMAIL);
        return ponudjac;
    }

    @Before
    public void initTest() {
        ponudjac = createEntity(em);
    }

    @Test
    @Transactional
    public void createPonudjac() throws Exception {
        int databaseSizeBeforeCreate = ponudjacRepository.findAll().size();

        // Create the Ponudjac
        restPonudjacMockMvc.perform(post("/api/ponudjacs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ponudjac)))
            .andExpect(status().isCreated());

        // Validate the Ponudjac in the database
        List<Ponudjac> ponudjacList = ponudjacRepository.findAll();
        assertThat(ponudjacList).hasSize(databaseSizeBeforeCreate + 1);
        Ponudjac testPonudjac = ponudjacList.get(ponudjacList.size() - 1);
        assertThat(testPonudjac.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testPonudjac.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testPonudjac.getIme()).isEqualTo(DEFAULT_IME);
        assertThat(testPonudjac.getPrezime()).isEqualTo(DEFAULT_PREZIME);
        assertThat(testPonudjac.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createPonudjacWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ponudjacRepository.findAll().size();

        // Create the Ponudjac with an existing ID
        ponudjac.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPonudjacMockMvc.perform(post("/api/ponudjacs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ponudjac)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Ponudjac> ponudjacList = ponudjacRepository.findAll();
        assertThat(ponudjacList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPonudjacs() throws Exception {
        // Initialize the database
        ponudjacRepository.saveAndFlush(ponudjac);

        // Get all the ponudjacList
        restPonudjacMockMvc.perform(get("/api/ponudjacs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ponudjac.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].ime").value(hasItem(DEFAULT_IME.toString())))
            .andExpect(jsonPath("$.[*].prezime").value(hasItem(DEFAULT_PREZIME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getPonudjac() throws Exception {
        // Initialize the database
        ponudjacRepository.saveAndFlush(ponudjac);

        // Get the ponudjac
        restPonudjacMockMvc.perform(get("/api/ponudjacs/{id}", ponudjac.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ponudjac.getId().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.ime").value(DEFAULT_IME.toString()))
            .andExpect(jsonPath("$.prezime").value(DEFAULT_PREZIME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPonudjac() throws Exception {
        // Get the ponudjac
        restPonudjacMockMvc.perform(get("/api/ponudjacs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePonudjac() throws Exception {
        // Initialize the database
        ponudjacRepository.saveAndFlush(ponudjac);
        int databaseSizeBeforeUpdate = ponudjacRepository.findAll().size();

        // Update the ponudjac
        Ponudjac updatedPonudjac = ponudjacRepository.findOne(ponudjac.getId());
        updatedPonudjac
            .login(UPDATED_LOGIN)
            .password(UPDATED_PASSWORD)
            .ime(UPDATED_IME)
            .prezime(UPDATED_PREZIME)
            .email(UPDATED_EMAIL);

        restPonudjacMockMvc.perform(put("/api/ponudjacs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPonudjac)))
            .andExpect(status().isOk());

        // Validate the Ponudjac in the database
        List<Ponudjac> ponudjacList = ponudjacRepository.findAll();
        assertThat(ponudjacList).hasSize(databaseSizeBeforeUpdate);
        Ponudjac testPonudjac = ponudjacList.get(ponudjacList.size() - 1);
        assertThat(testPonudjac.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testPonudjac.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testPonudjac.getIme()).isEqualTo(UPDATED_IME);
        assertThat(testPonudjac.getPrezime()).isEqualTo(UPDATED_PREZIME);
        assertThat(testPonudjac.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingPonudjac() throws Exception {
        int databaseSizeBeforeUpdate = ponudjacRepository.findAll().size();

        // Create the Ponudjac

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPonudjacMockMvc.perform(put("/api/ponudjacs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ponudjac)))
            .andExpect(status().isCreated());

        // Validate the Ponudjac in the database
        List<Ponudjac> ponudjacList = ponudjacRepository.findAll();
        assertThat(ponudjacList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePonudjac() throws Exception {
        // Initialize the database
        ponudjacRepository.saveAndFlush(ponudjac);
        int databaseSizeBeforeDelete = ponudjacRepository.findAll().size();

        // Get the ponudjac
        restPonudjacMockMvc.perform(delete("/api/ponudjacs/{id}", ponudjac.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ponudjac> ponudjacList = ponudjacRepository.findAll();
        assertThat(ponudjacList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ponudjac.class);
        Ponudjac ponudjac1 = new Ponudjac();
        ponudjac1.setId(1L);
        Ponudjac ponudjac2 = new Ponudjac();
        ponudjac2.setId(ponudjac1.getId());
        assertThat(ponudjac1).isEqualTo(ponudjac2);
        ponudjac2.setId(2L);
        assertThat(ponudjac1).isNotEqualTo(ponudjac2);
        ponudjac1.setId(null);
        assertThat(ponudjac1).isNotEqualTo(ponudjac2);
    }
}
