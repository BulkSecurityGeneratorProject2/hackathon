package com.accenture.bikecity.web.rest;

import com.accenture.bikecity.HackathonApp;
import com.accenture.bikecity.domain.BikeTransaction;
import com.accenture.bikecity.repository.BikeTransactionRepository;
import com.accenture.bikecity.service.BikeTransactionService;
import com.accenture.bikecity.service.dto.BikeTransactionDTO;
import com.accenture.bikecity.service.mapper.BikeTransactionMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.accenture.bikecity.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link BikeTransactionResource} REST controller.
 */
@SpringBootTest(classes = HackathonApp.class)
public class BikeTransactionResourceIT {

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_BIKE_ID = 1L;
    private static final Long UPDATED_BIKE_ID = 2L;

    private static final Long DEFAULT_START_PARKING_ID = 1L;
    private static final Long UPDATED_START_PARKING_ID = 2L;

    private static final Long DEFAULT_END_PARKING_ID = 1L;
    private static final Long UPDATED_END_PARKING_ID = 2L;

    @Autowired
    private BikeTransactionRepository bikeTransactionRepository;

    @Autowired
    private BikeTransactionMapper bikeTransactionMapper;

    @Autowired
    private BikeTransactionService bikeTransactionService;

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

    private MockMvc restBikeTransactionMockMvc;

    private BikeTransaction bikeTransaction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BikeTransactionResource bikeTransactionResource = new BikeTransactionResource(bikeTransactionService);
        this.restBikeTransactionMockMvc = MockMvcBuilders.standaloneSetup(bikeTransactionResource)
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
    public static BikeTransaction createEntity(EntityManager em) {
        BikeTransaction bikeTransaction = new BikeTransaction()
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .userId(DEFAULT_USER_ID)
            .bikeId(DEFAULT_BIKE_ID)
            .startParkingId(DEFAULT_START_PARKING_ID)
            .endParkingId(DEFAULT_END_PARKING_ID);
        return bikeTransaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BikeTransaction createUpdatedEntity(EntityManager em) {
        BikeTransaction bikeTransaction = new BikeTransaction()
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .userId(UPDATED_USER_ID)
            .bikeId(UPDATED_BIKE_ID)
            .startParkingId(UPDATED_START_PARKING_ID)
            .endParkingId(UPDATED_END_PARKING_ID);
        return bikeTransaction;
    }

    @BeforeEach
    public void initTest() {
        bikeTransaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createBikeTransaction() throws Exception {
        int databaseSizeBeforeCreate = bikeTransactionRepository.findAll().size();

        // Create the BikeTransaction
        BikeTransactionDTO bikeTransactionDTO = bikeTransactionMapper.toDto(bikeTransaction);
        restBikeTransactionMockMvc.perform(post("/api/bike-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bikeTransactionDTO)))
            .andExpect(status().isCreated());

        // Validate the BikeTransaction in the database
        List<BikeTransaction> bikeTransactionList = bikeTransactionRepository.findAll();
        assertThat(bikeTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        BikeTransaction testBikeTransaction = bikeTransactionList.get(bikeTransactionList.size() - 1);
        assertThat(testBikeTransaction.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testBikeTransaction.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testBikeTransaction.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testBikeTransaction.getBikeId()).isEqualTo(DEFAULT_BIKE_ID);
        assertThat(testBikeTransaction.getStartParkingId()).isEqualTo(DEFAULT_START_PARKING_ID);
        assertThat(testBikeTransaction.getEndParkingId()).isEqualTo(DEFAULT_END_PARKING_ID);
    }

    @Test
    @Transactional
    public void createBikeTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bikeTransactionRepository.findAll().size();

        // Create the BikeTransaction with an existing ID
        bikeTransaction.setId(1L);
        BikeTransactionDTO bikeTransactionDTO = bikeTransactionMapper.toDto(bikeTransaction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBikeTransactionMockMvc.perform(post("/api/bike-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bikeTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BikeTransaction in the database
        List<BikeTransaction> bikeTransactionList = bikeTransactionRepository.findAll();
        assertThat(bikeTransactionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBikeTransactions() throws Exception {
        // Initialize the database
        bikeTransactionRepository.saveAndFlush(bikeTransaction);

        // Get all the bikeTransactionList
        restBikeTransactionMockMvc.perform(get("/api/bike-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bikeTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].bikeId").value(hasItem(DEFAULT_BIKE_ID.intValue())))
            .andExpect(jsonPath("$.[*].startParkingId").value(hasItem(DEFAULT_START_PARKING_ID.intValue())))
            .andExpect(jsonPath("$.[*].endParkingId").value(hasItem(DEFAULT_END_PARKING_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getBikeTransaction() throws Exception {
        // Initialize the database
        bikeTransactionRepository.saveAndFlush(bikeTransaction);

        // Get the bikeTransaction
        restBikeTransactionMockMvc.perform(get("/api/bike-transactions/{id}", bikeTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bikeTransaction.getId().intValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.bikeId").value(DEFAULT_BIKE_ID.intValue()))
            .andExpect(jsonPath("$.startParkingId").value(DEFAULT_START_PARKING_ID.intValue()))
            .andExpect(jsonPath("$.endParkingId").value(DEFAULT_END_PARKING_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBikeTransaction() throws Exception {
        // Get the bikeTransaction
        restBikeTransactionMockMvc.perform(get("/api/bike-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBikeTransaction() throws Exception {
        // Initialize the database
        bikeTransactionRepository.saveAndFlush(bikeTransaction);

        int databaseSizeBeforeUpdate = bikeTransactionRepository.findAll().size();

        // Update the bikeTransaction
        BikeTransaction updatedBikeTransaction = bikeTransactionRepository.findById(bikeTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedBikeTransaction are not directly saved in db
        em.detach(updatedBikeTransaction);
        updatedBikeTransaction
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .userId(UPDATED_USER_ID)
            .bikeId(UPDATED_BIKE_ID)
            .startParkingId(UPDATED_START_PARKING_ID)
            .endParkingId(UPDATED_END_PARKING_ID);
        BikeTransactionDTO bikeTransactionDTO = bikeTransactionMapper.toDto(updatedBikeTransaction);

        restBikeTransactionMockMvc.perform(put("/api/bike-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bikeTransactionDTO)))
            .andExpect(status().isOk());

        // Validate the BikeTransaction in the database
        List<BikeTransaction> bikeTransactionList = bikeTransactionRepository.findAll();
        assertThat(bikeTransactionList).hasSize(databaseSizeBeforeUpdate);
        BikeTransaction testBikeTransaction = bikeTransactionList.get(bikeTransactionList.size() - 1);
        assertThat(testBikeTransaction.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testBikeTransaction.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testBikeTransaction.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testBikeTransaction.getBikeId()).isEqualTo(UPDATED_BIKE_ID);
        assertThat(testBikeTransaction.getStartParkingId()).isEqualTo(UPDATED_START_PARKING_ID);
        assertThat(testBikeTransaction.getEndParkingId()).isEqualTo(UPDATED_END_PARKING_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingBikeTransaction() throws Exception {
        int databaseSizeBeforeUpdate = bikeTransactionRepository.findAll().size();

        // Create the BikeTransaction
        BikeTransactionDTO bikeTransactionDTO = bikeTransactionMapper.toDto(bikeTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBikeTransactionMockMvc.perform(put("/api/bike-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bikeTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BikeTransaction in the database
        List<BikeTransaction> bikeTransactionList = bikeTransactionRepository.findAll();
        assertThat(bikeTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBikeTransaction() throws Exception {
        // Initialize the database
        bikeTransactionRepository.saveAndFlush(bikeTransaction);

        int databaseSizeBeforeDelete = bikeTransactionRepository.findAll().size();

        // Delete the bikeTransaction
        restBikeTransactionMockMvc.perform(delete("/api/bike-transactions/{id}", bikeTransaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<BikeTransaction> bikeTransactionList = bikeTransactionRepository.findAll();
        assertThat(bikeTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BikeTransaction.class);
        BikeTransaction bikeTransaction1 = new BikeTransaction();
        bikeTransaction1.setId(1L);
        BikeTransaction bikeTransaction2 = new BikeTransaction();
        bikeTransaction2.setId(bikeTransaction1.getId());
        assertThat(bikeTransaction1).isEqualTo(bikeTransaction2);
        bikeTransaction2.setId(2L);
        assertThat(bikeTransaction1).isNotEqualTo(bikeTransaction2);
        bikeTransaction1.setId(null);
        assertThat(bikeTransaction1).isNotEqualTo(bikeTransaction2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BikeTransactionDTO.class);
        BikeTransactionDTO bikeTransactionDTO1 = new BikeTransactionDTO();
        bikeTransactionDTO1.setId(1L);
        BikeTransactionDTO bikeTransactionDTO2 = new BikeTransactionDTO();
        assertThat(bikeTransactionDTO1).isNotEqualTo(bikeTransactionDTO2);
        bikeTransactionDTO2.setId(bikeTransactionDTO1.getId());
        assertThat(bikeTransactionDTO1).isEqualTo(bikeTransactionDTO2);
        bikeTransactionDTO2.setId(2L);
        assertThat(bikeTransactionDTO1).isNotEqualTo(bikeTransactionDTO2);
        bikeTransactionDTO1.setId(null);
        assertThat(bikeTransactionDTO1).isNotEqualTo(bikeTransactionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(bikeTransactionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(bikeTransactionMapper.fromId(null)).isNull();
    }
}
