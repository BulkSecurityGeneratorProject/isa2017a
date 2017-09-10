package isa.web.rest;

import isa.IsaApp;

import isa.domain.Prijatelji;
import isa.repository.PrijateljiRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PrijateljiResource REST controller.
 *
 * @see PrijateljiResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IsaApp.class)
public class PrijateljiResourceIntTest {

    private static final Long DEFAULT_ID_GOSTA_1 = 1L;
    private static final Long UPDATED_ID_GOSTA_1 = 2L;

    private static final Long DEFAULT_ID_GOSTA_2 = 1L;
    private static final Long UPDATED_ID_GOSTA_2 = 2L;

    private static final LocalDate DEFAULT_POSTALAN_ZAHTEV = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_POSTALAN_ZAHTEV = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PRIHVACEN_ZAHTEV = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRIHVACEN_ZAHTEV = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private PrijateljiRepository prijateljiRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrijateljiMockMvc;

    private Prijatelji prijatelji;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrijateljiResource prijateljiResource = new PrijateljiResource(prijateljiRepository);
        this.restPrijateljiMockMvc = MockMvcBuilders.standaloneSetup(prijateljiResource)
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
    public static Prijatelji createEntity(EntityManager em) {
        Prijatelji prijatelji = new Prijatelji()
            .idGosta1(DEFAULT_ID_GOSTA_1)
            .idGosta2(DEFAULT_ID_GOSTA_2)
            .postalanZahtev(DEFAULT_POSTALAN_ZAHTEV)
            .prihvacenZahtev(DEFAULT_PRIHVACEN_ZAHTEV);
        return prijatelji;
    }

    @Before
    public void initTest() {
        prijatelji = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrijatelji() throws Exception {
        int databaseSizeBeforeCreate = prijateljiRepository.findAll().size();

        // Create the Prijatelji
        restPrijateljiMockMvc.perform(post("/api/prijateljis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prijatelji)))
            .andExpect(status().isCreated());

        // Validate the Prijatelji in the database
        List<Prijatelji> prijateljiList = prijateljiRepository.findAll();
        assertThat(prijateljiList).hasSize(databaseSizeBeforeCreate + 1);
        Prijatelji testPrijatelji = prijateljiList.get(prijateljiList.size() - 1);
        assertThat(testPrijatelji.getIdGosta1()).isEqualTo(DEFAULT_ID_GOSTA_1);
        assertThat(testPrijatelji.getIdGosta2()).isEqualTo(DEFAULT_ID_GOSTA_2);
        assertThat(testPrijatelji.getPostalanZahtev()).isEqualTo(DEFAULT_POSTALAN_ZAHTEV);
        assertThat(testPrijatelji.getPrihvacenZahtev()).isEqualTo(DEFAULT_PRIHVACEN_ZAHTEV);
    }

    @Test
    @Transactional
    public void createPrijateljiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prijateljiRepository.findAll().size();

        // Create the Prijatelji with an existing ID
        prijatelji.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrijateljiMockMvc.perform(post("/api/prijateljis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prijatelji)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Prijatelji> prijateljiList = prijateljiRepository.findAll();
        assertThat(prijateljiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrijateljis() throws Exception {
        // Initialize the database
        prijateljiRepository.saveAndFlush(prijatelji);

        // Get all the prijateljiList
        restPrijateljiMockMvc.perform(get("/api/prijateljis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prijatelji.getId().intValue())))
            .andExpect(jsonPath("$.[*].idGosta1").value(hasItem(DEFAULT_ID_GOSTA_1.intValue())))
            .andExpect(jsonPath("$.[*].idGosta2").value(hasItem(DEFAULT_ID_GOSTA_2.intValue())))
            .andExpect(jsonPath("$.[*].postalanZahtev").value(hasItem(DEFAULT_POSTALAN_ZAHTEV.toString())))
            .andExpect(jsonPath("$.[*].prihvacenZahtev").value(hasItem(DEFAULT_PRIHVACEN_ZAHTEV.toString())));
    }

    @Test
    @Transactional
    public void getPrijatelji() throws Exception {
        // Initialize the database
        prijateljiRepository.saveAndFlush(prijatelji);

        // Get the prijatelji
        restPrijateljiMockMvc.perform(get("/api/prijateljis/{id}", prijatelji.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prijatelji.getId().intValue()))
            .andExpect(jsonPath("$.idGosta1").value(DEFAULT_ID_GOSTA_1.intValue()))
            .andExpect(jsonPath("$.idGosta2").value(DEFAULT_ID_GOSTA_2.intValue()))
            .andExpect(jsonPath("$.postalanZahtev").value(DEFAULT_POSTALAN_ZAHTEV.toString()))
            .andExpect(jsonPath("$.prihvacenZahtev").value(DEFAULT_PRIHVACEN_ZAHTEV.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrijatelji() throws Exception {
        // Get the prijatelji
        restPrijateljiMockMvc.perform(get("/api/prijateljis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrijatelji() throws Exception {
        // Initialize the database
        prijateljiRepository.saveAndFlush(prijatelji);
        int databaseSizeBeforeUpdate = prijateljiRepository.findAll().size();

        // Update the prijatelji
        Prijatelji updatedPrijatelji = prijateljiRepository.findOne(prijatelji.getId());
        updatedPrijatelji
            .idGosta1(UPDATED_ID_GOSTA_1)
            .idGosta2(UPDATED_ID_GOSTA_2)
            .postalanZahtev(UPDATED_POSTALAN_ZAHTEV)
            .prihvacenZahtev(UPDATED_PRIHVACEN_ZAHTEV);

        restPrijateljiMockMvc.perform(put("/api/prijateljis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrijatelji)))
            .andExpect(status().isOk());

        // Validate the Prijatelji in the database
        List<Prijatelji> prijateljiList = prijateljiRepository.findAll();
        assertThat(prijateljiList).hasSize(databaseSizeBeforeUpdate);
        Prijatelji testPrijatelji = prijateljiList.get(prijateljiList.size() - 1);
        assertThat(testPrijatelji.getIdGosta1()).isEqualTo(UPDATED_ID_GOSTA_1);
        assertThat(testPrijatelji.getIdGosta2()).isEqualTo(UPDATED_ID_GOSTA_2);
        assertThat(testPrijatelji.getPostalanZahtev()).isEqualTo(UPDATED_POSTALAN_ZAHTEV);
        assertThat(testPrijatelji.getPrihvacenZahtev()).isEqualTo(UPDATED_PRIHVACEN_ZAHTEV);
    }

    @Test
    @Transactional
    public void updateNonExistingPrijatelji() throws Exception {
        int databaseSizeBeforeUpdate = prijateljiRepository.findAll().size();

        // Create the Prijatelji

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrijateljiMockMvc.perform(put("/api/prijateljis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prijatelji)))
            .andExpect(status().isCreated());

        // Validate the Prijatelji in the database
        List<Prijatelji> prijateljiList = prijateljiRepository.findAll();
        assertThat(prijateljiList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrijatelji() throws Exception {
        // Initialize the database
        prijateljiRepository.saveAndFlush(prijatelji);
        int databaseSizeBeforeDelete = prijateljiRepository.findAll().size();

        // Get the prijatelji
        restPrijateljiMockMvc.perform(delete("/api/prijateljis/{id}", prijatelji.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Prijatelji> prijateljiList = prijateljiRepository.findAll();
        assertThat(prijateljiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prijatelji.class);
        Prijatelji prijatelji1 = new Prijatelji();
        prijatelji1.setId(1L);
        Prijatelji prijatelji2 = new Prijatelji();
        prijatelji2.setId(prijatelji1.getId());
        assertThat(prijatelji1).isEqualTo(prijatelji2);
        prijatelji2.setId(2L);
        assertThat(prijatelji1).isNotEqualTo(prijatelji2);
        prijatelji1.setId(null);
        assertThat(prijatelji1).isNotEqualTo(prijatelji2);
    }
}
