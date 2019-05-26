package com.accenture.bikecity.web.rest;

import com.accenture.bikecity.HackathonApp;
import com.accenture.bikecity.domain.Bicycle;
import com.accenture.bikecity.repository.BicycleRepository;
import com.accenture.bikecity.service.BicycleService;
import com.accenture.bikecity.service.dto.BicycleDTO;
import com.accenture.bikecity.service.mapper.BicycleMapper;
import com.accenture.bikecity.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.accenture.bikecity.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link BicycleResource} REST controller.
 */
@SpringBootTest(classes = HackathonApp.class)
public class BicycleResourceIT {

    private static final Long DEFAULT_PARKING_ID = 1L;
    private static final Long UPDATED_PARKING_ID = 2L;

    @Autowired
    private BicycleRepository bicycleRepository;

    @Autowired
    private BicycleMapper bicycleMapper;

    @Autowired
    private BicycleService bicycleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restBicycleMockMvc;

    private Bicycle bicycle;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BicycleResource bicycleResource = new BicycleResource(bicycleService);
        this.restBicycleMockMvc = MockMvcBuilders.standaloneSetup(bicycleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bicycle createEntity(EntityManager em) {
        Bicycle bicycle = new Bicycle()
            .parkingId(DEFAULT_PARKING_ID);
        return bicycle;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bicycle createUpdatedEntity(EntityManager em) {
        Bicycle bicycle = new Bicycle()
            .parkingId(UPDATED_PARKING_ID);
        return bicycle;
    }

    @BeforeEach
    public void initTest() {
        bicycle = createEntity(em);
    }

    @Test
    @Transactional
    public void createBicycle() throws Exception {
        int databaseSizeBeforeCreate = bicycleRepository.findAll().size();

        // Create the Bicycle
        BicycleDTO bicycleDTO = bicycleMapper.toDto(bicycle);
        restBicycleMockMvc.perform(post("/api/bicycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bicycleDTO)))
            .andExpect(status().isCreated());

        // Validate the Bicycle in the database
        List<Bicycle> bicycleList = bicycleRepository.findAll();
        assertThat(bicycleList).hasSize(databaseSizeBeforeCreate + 1);
        Bicycle testBicycle = bicycleList.get(bicycleList.size() - 1);
        assertThat(testBicycle.getParkingId()).isEqualTo(DEFAULT_PARKING_ID);
    }

    @Test
    @Transactional
    public void createBicycleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bicycleRepository.findAll().size();

        // Create the Bicycle with an existing ID
        bicycle.setId(1L);
        BicycleDTO bicycleDTO = bicycleMapper.toDto(bicycle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBicycleMockMvc.perform(post("/api/bicycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bicycleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bicycle in the database
        List<Bicycle> bicycleList = bicycleRepository.findAll();
        assertThat(bicycleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBicycles() throws Exception {
        // Initialize the database
        bicycleRepository.saveAndFlush(bicycle);

        // Get all the bicycleList
        restBicycleMockMvc.perform(get("/api/bicycles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bicycle.getId().intValue())))
            .andExpect(jsonPath("$.[*].parkingId").value(hasItem(DEFAULT_PARKING_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getBicycle() throws Exception {
        // Initialize the database
        bicycleRepository.saveAndFlush(bicycle);

        // Get the bicycle
        restBicycleMockMvc.perform(get("/api/bicycles/{id}", bicycle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bicycle.getId().intValue()))
            .andExpect(jsonPath("$.parkingId").value(DEFAULT_PARKING_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBicycle() throws Exception {
        // Get the bicycle
        restBicycleMockMvc.perform(get("/api/bicycles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBicycle() throws Exception {
        // Initialize the database
        bicycleRepository.saveAndFlush(bicycle);

        int databaseSizeBeforeUpdate = bicycleRepository.findAll().size();

        // Update the bicycle
        Bicycle updatedBicycle = bicycleRepository.findById(bicycle.getId()).get();
        // Disconnect from session so that the updates on updatedBicycle are not directly saved in db
        em.detach(updatedBicycle);
        updatedBicycle
            .parkingId(UPDATED_PARKING_ID);
        BicycleDTO bicycleDTO = bicycleMapper.toDto(updatedBicycle);

        restBicycleMockMvc.perform(put("/api/bicycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bicycleDTO)))
            .andExpect(status().isOk());

        // Validate the Bicycle in the database
        List<Bicycle> bicycleList = bicycleRepository.findAll();
        assertThat(bicycleList).hasSize(databaseSizeBeforeUpdate);
        Bicycle testBicycle = bicycleList.get(bicycleList.size() - 1);
        assertThat(testBicycle.getParkingId()).isEqualTo(UPDATED_PARKING_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingBicycle() throws Exception {
        int databaseSizeBeforeUpdate = bicycleRepository.findAll().size();

        // Create the Bicycle
        BicycleDTO bicycleDTO = bicycleMapper.toDto(bicycle);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBicycleMockMvc.perform(put("/api/bicycles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bicycleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bicycle in the database
        List<Bicycle> bicycleList = bicycleRepository.findAll();
        assertThat(bicycleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBicycle() throws Exception {
        // Initialize the database
        bicycleRepository.saveAndFlush(bicycle);

        int databaseSizeBeforeDelete = bicycleRepository.findAll().size();

        // Delete the bicycle
        restBicycleMockMvc.perform(delete("/api/bicycles/{id}", bicycle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Bicycle> bicycleList = bicycleRepository.findAll();
        assertThat(bicycleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bicycle.class);
        Bicycle bicycle1 = new Bicycle();
        bicycle1.setId(1L);
        Bicycle bicycle2 = new Bicycle();
        bicycle2.setId(bicycle1.getId());
        assertThat(bicycle1).isEqualTo(bicycle2);
        bicycle2.setId(2L);
        assertThat(bicycle1).isNotEqualTo(bicycle2);
        bicycle1.setId(null);
        assertThat(bicycle1).isNotEqualTo(bicycle2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BicycleDTO.class);
        BicycleDTO bicycleDTO1 = new BicycleDTO();
        bicycleDTO1.setId(1L);
        BicycleDTO bicycleDTO2 = new BicycleDTO();
        assertThat(bicycleDTO1).isNotEqualTo(bicycleDTO2);
        bicycleDTO2.setId(bicycleDTO1.getId());
        assertThat(bicycleDTO1).isEqualTo(bicycleDTO2);
        bicycleDTO2.setId(2L);
        assertThat(bicycleDTO1).isNotEqualTo(bicycleDTO2);
        bicycleDTO1.setId(null);
        assertThat(bicycleDTO1).isNotEqualTo(bicycleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bicycleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bicycleMapper.fromId(null)).isNull();
    }
}
