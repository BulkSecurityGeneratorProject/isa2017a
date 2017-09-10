package isa.web.rest;

import isa.IsaApp;

import isa.domain.Poziv;
import isa.repository.PozivRepository;
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
 * Test class for the PozivResource REST controller.
 *
 * @see PozivResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IsaApp.class)
public class PozivResourceIntTest {

    private static final Boolean DEFAULT_POTVRDJENO = false;
    private static final Boolean UPDATED_POTVRDJENO = true;

    @Autowired
    private PozivRepository pozivRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPozivMockMvc;

    private Poziv poziv;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PozivResource pozivResource = new PozivResource(pozivRepository);
        this.restPozivMockMvc = MockMvcBuilders.standaloneSetup(pozivResource)
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
    public static Poziv createEntity(EntityManager em) {
        Poziv poziv = new Poziv()
            .potvrdjeno(DEFAULT_POTVRDJENO);
        return poziv;
    }

    @Before
    public void initTest() {
        poziv = createEntity(em);
    }

    @Test
    @Transactional
    public void createPoziv() throws Exception {
        int databaseSizeBeforeCreate = pozivRepository.findAll().size();

        // Create the Poziv
        restPozivMockMvc.perform(post("/api/pozivs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poziv)))
            .andExpect(status().isCreated());

        // Validate the Poziv in the database
        List<Poziv> pozivList = pozivRepository.findAll();
        assertThat(pozivList).hasSize(databaseSizeBeforeCreate + 1);
        Poziv testPoziv = pozivList.get(pozivList.size() - 1);
        assertThat(testPoziv.isPotvrdjeno()).isEqualTo(DEFAULT_POTVRDJENO);
    }

    @Test
    @Transactional
    public void createPozivWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pozivRepository.findAll().size();

        // Create the Poziv with an existing ID
        poziv.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPozivMockMvc.perform(post("/api/pozivs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poziv)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Poziv> pozivList = pozivRepository.findAll();
        assertThat(pozivList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPozivs() throws Exception {
        // Initialize the database
        pozivRepository.saveAndFlush(poziv);

        // Get all the pozivList
        restPozivMockMvc.perform(get("/api/pozivs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(poziv.getId().intValue())))
            .andExpect(jsonPath("$.[*].potvrdjeno").value(hasItem(DEFAULT_POTVRDJENO.booleanValue())));
    }

    @Test
    @Transactional
    public void getPoziv() throws Exception {
        // Initialize the database
        pozivRepository.saveAndFlush(poziv);

        // Get the poziv
        restPozivMockMvc.perform(get("/api/pozivs/{id}", poziv.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(poziv.getId().intValue()))
            .andExpect(jsonPath("$.potvrdjeno").value(DEFAULT_POTVRDJENO.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPoziv() throws Exception {
        // Get the poziv
        restPozivMockMvc.perform(get("/api/pozivs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePoziv() throws Exception {
        // Initialize the database
        pozivRepository.saveAndFlush(poziv);
        int databaseSizeBeforeUpdate = pozivRepository.findAll().size();

        // Update the poziv
        Poziv updatedPoziv = pozivRepository.findOne(poziv.getId());
        updatedPoziv
            .potvrdjeno(UPDATED_POTVRDJENO);

        restPozivMockMvc.perform(put("/api/pozivs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPoziv)))
            .andExpect(status().isOk());

        // Validate the Poziv in the database
        List<Poziv> pozivList = pozivRepository.findAll();
        assertThat(pozivList).hasSize(databaseSizeBeforeUpdate);
        Poziv testPoziv = pozivList.get(pozivList.size() - 1);
        assertThat(testPoziv.isPotvrdjeno()).isEqualTo(UPDATED_POTVRDJENO);
    }

    @Test
    @Transactional
    public void updateNonExistingPoziv() throws Exception {
        int databaseSizeBeforeUpdate = pozivRepository.findAll().size();

        // Create the Poziv

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPozivMockMvc.perform(put("/api/pozivs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(poziv)))
            .andExpect(status().isCreated());

        // Validate the Poziv in the database
        List<Poziv> pozivList = pozivRepository.findAll();
        assertThat(pozivList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePoziv() throws Exception {
        // Initialize the database
        pozivRepository.saveAndFlush(poziv);
        int databaseSizeBeforeDelete = pozivRepository.findAll().size();

        // Get the poziv
        restPozivMockMvc.perform(delete("/api/pozivs/{id}", poziv.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Poziv> pozivList = pozivRepository.findAll();
        assertThat(pozivList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Poziv.class);
        Poziv poziv1 = new Poziv();
        poziv1.setId(1L);
        Poziv poziv2 = new Poziv();
        poziv2.setId(poziv1.getId());
        assertThat(poziv1).isEqualTo(poziv2);
        poziv2.setId(2L);
        assertThat(poziv1).isNotEqualTo(poziv2);
        poziv1.setId(null);
        assertThat(poziv1).isNotEqualTo(poziv2);
    }
}
