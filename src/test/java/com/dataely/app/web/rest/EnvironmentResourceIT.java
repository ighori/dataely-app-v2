package com.dataely.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dataely.app.IntegrationTest;
import com.dataely.app.domain.Environment;
import com.dataely.app.domain.enumeration.EEnvPurpose;
import com.dataely.app.domain.enumeration.EEnvType;
import com.dataely.app.repository.EnvironmentRepository;
import com.dataely.app.service.dto.EnvironmentDTO;
import com.dataely.app.service.mapper.EnvironmentMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EnvironmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EnvironmentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final EEnvType DEFAULT_TYPE = EEnvType.STANDALONE;
    private static final EEnvType UPDATED_TYPE = EEnvType.INTEGRATED;

    private static final EEnvPurpose DEFAULT_PURPOSE = EEnvPurpose.PROFILING;
    private static final EEnvPurpose UPDATED_PURPOSE = EEnvPurpose.MASKING;

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/environments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EnvironmentRepository environmentRepository;

    @Autowired
    private EnvironmentMapper environmentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEnvironmentMockMvc;

    private Environment environment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Environment createEntity(EntityManager em) {
        Environment environment = new Environment()
            .name(DEFAULT_NAME)
            .detail(DEFAULT_DETAIL)
            .type(DEFAULT_TYPE)
            .purpose(DEFAULT_PURPOSE)
            .creationDate(DEFAULT_CREATION_DATE)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return environment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Environment createUpdatedEntity(EntityManager em) {
        Environment environment = new Environment()
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .type(UPDATED_TYPE)
            .purpose(UPDATED_PURPOSE)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return environment;
    }

    @BeforeEach
    public void initTest() {
        environment = createEntity(em);
    }

    @Test
    @Transactional
    void createEnvironment() throws Exception {
        int databaseSizeBeforeCreate = environmentRepository.findAll().size();
        // Create the Environment
        EnvironmentDTO environmentDTO = environmentMapper.toDto(environment);
        restEnvironmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(environmentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Environment in the database
        List<Environment> environmentList = environmentRepository.findAll();
        assertThat(environmentList).hasSize(databaseSizeBeforeCreate + 1);
        Environment testEnvironment = environmentList.get(environmentList.size() - 1);
        assertThat(testEnvironment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEnvironment.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testEnvironment.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEnvironment.getPurpose()).isEqualTo(DEFAULT_PURPOSE);
        assertThat(testEnvironment.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testEnvironment.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void createEnvironmentWithExistingId() throws Exception {
        // Create the Environment with an existing ID
        environment.setId(1L);
        EnvironmentDTO environmentDTO = environmentMapper.toDto(environment);

        int databaseSizeBeforeCreate = environmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEnvironmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(environmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Environment in the database
        List<Environment> environmentList = environmentRepository.findAll();
        assertThat(environmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = environmentRepository.findAll().size();
        // set the field null
        environment.setName(null);

        // Create the Environment, which fails.
        EnvironmentDTO environmentDTO = environmentMapper.toDto(environment);

        restEnvironmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(environmentDTO))
            )
            .andExpect(status().isBadRequest());

        List<Environment> environmentList = environmentRepository.findAll();
        assertThat(environmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = environmentRepository.findAll().size();
        // set the field null
        environment.setType(null);

        // Create the Environment, which fails.
        EnvironmentDTO environmentDTO = environmentMapper.toDto(environment);

        restEnvironmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(environmentDTO))
            )
            .andExpect(status().isBadRequest());

        List<Environment> environmentList = environmentRepository.findAll();
        assertThat(environmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPurposeIsRequired() throws Exception {
        int databaseSizeBeforeTest = environmentRepository.findAll().size();
        // set the field null
        environment.setPurpose(null);

        // Create the Environment, which fails.
        EnvironmentDTO environmentDTO = environmentMapper.toDto(environment);

        restEnvironmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(environmentDTO))
            )
            .andExpect(status().isBadRequest());

        List<Environment> environmentList = environmentRepository.findAll();
        assertThat(environmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEnvironments() throws Exception {
        // Initialize the database
        environmentRepository.saveAndFlush(environment);

        // Get all the environmentList
        restEnvironmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(environment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].purpose").value(hasItem(DEFAULT_PURPOSE.toString())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getEnvironment() throws Exception {
        // Initialize the database
        environmentRepository.saveAndFlush(environment);

        // Get the environment
        restEnvironmentMockMvc
            .perform(get(ENTITY_API_URL_ID, environment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(environment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.purpose").value(DEFAULT_PURPOSE.toString()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEnvironment() throws Exception {
        // Get the environment
        restEnvironmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEnvironment() throws Exception {
        // Initialize the database
        environmentRepository.saveAndFlush(environment);

        int databaseSizeBeforeUpdate = environmentRepository.findAll().size();

        // Update the environment
        Environment updatedEnvironment = environmentRepository.findById(environment.getId()).get();
        // Disconnect from session so that the updates on updatedEnvironment are not directly saved in db
        em.detach(updatedEnvironment);
        updatedEnvironment
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .type(UPDATED_TYPE)
            .purpose(UPDATED_PURPOSE)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        EnvironmentDTO environmentDTO = environmentMapper.toDto(updatedEnvironment);

        restEnvironmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, environmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(environmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Environment in the database
        List<Environment> environmentList = environmentRepository.findAll();
        assertThat(environmentList).hasSize(databaseSizeBeforeUpdate);
        Environment testEnvironment = environmentList.get(environmentList.size() - 1);
        assertThat(testEnvironment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEnvironment.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testEnvironment.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEnvironment.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testEnvironment.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testEnvironment.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingEnvironment() throws Exception {
        int databaseSizeBeforeUpdate = environmentRepository.findAll().size();
        environment.setId(count.incrementAndGet());

        // Create the Environment
        EnvironmentDTO environmentDTO = environmentMapper.toDto(environment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnvironmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, environmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(environmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Environment in the database
        List<Environment> environmentList = environmentRepository.findAll();
        assertThat(environmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEnvironment() throws Exception {
        int databaseSizeBeforeUpdate = environmentRepository.findAll().size();
        environment.setId(count.incrementAndGet());

        // Create the Environment
        EnvironmentDTO environmentDTO = environmentMapper.toDto(environment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnvironmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(environmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Environment in the database
        List<Environment> environmentList = environmentRepository.findAll();
        assertThat(environmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEnvironment() throws Exception {
        int databaseSizeBeforeUpdate = environmentRepository.findAll().size();
        environment.setId(count.incrementAndGet());

        // Create the Environment
        EnvironmentDTO environmentDTO = environmentMapper.toDto(environment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnvironmentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(environmentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Environment in the database
        List<Environment> environmentList = environmentRepository.findAll();
        assertThat(environmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEnvironmentWithPatch() throws Exception {
        // Initialize the database
        environmentRepository.saveAndFlush(environment);

        int databaseSizeBeforeUpdate = environmentRepository.findAll().size();

        // Update the environment using partial update
        Environment partialUpdatedEnvironment = new Environment();
        partialUpdatedEnvironment.setId(environment.getId());

        partialUpdatedEnvironment.name(UPDATED_NAME).detail(UPDATED_DETAIL).purpose(UPDATED_PURPOSE).creationDate(UPDATED_CREATION_DATE);

        restEnvironmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnvironment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnvironment))
            )
            .andExpect(status().isOk());

        // Validate the Environment in the database
        List<Environment> environmentList = environmentRepository.findAll();
        assertThat(environmentList).hasSize(databaseSizeBeforeUpdate);
        Environment testEnvironment = environmentList.get(environmentList.size() - 1);
        assertThat(testEnvironment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEnvironment.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testEnvironment.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testEnvironment.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testEnvironment.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testEnvironment.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateEnvironmentWithPatch() throws Exception {
        // Initialize the database
        environmentRepository.saveAndFlush(environment);

        int databaseSizeBeforeUpdate = environmentRepository.findAll().size();

        // Update the environment using partial update
        Environment partialUpdatedEnvironment = new Environment();
        partialUpdatedEnvironment.setId(environment.getId());

        partialUpdatedEnvironment
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .type(UPDATED_TYPE)
            .purpose(UPDATED_PURPOSE)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restEnvironmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEnvironment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEnvironment))
            )
            .andExpect(status().isOk());

        // Validate the Environment in the database
        List<Environment> environmentList = environmentRepository.findAll();
        assertThat(environmentList).hasSize(databaseSizeBeforeUpdate);
        Environment testEnvironment = environmentList.get(environmentList.size() - 1);
        assertThat(testEnvironment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEnvironment.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testEnvironment.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testEnvironment.getPurpose()).isEqualTo(UPDATED_PURPOSE);
        assertThat(testEnvironment.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testEnvironment.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingEnvironment() throws Exception {
        int databaseSizeBeforeUpdate = environmentRepository.findAll().size();
        environment.setId(count.incrementAndGet());

        // Create the Environment
        EnvironmentDTO environmentDTO = environmentMapper.toDto(environment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEnvironmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, environmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(environmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Environment in the database
        List<Environment> environmentList = environmentRepository.findAll();
        assertThat(environmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEnvironment() throws Exception {
        int databaseSizeBeforeUpdate = environmentRepository.findAll().size();
        environment.setId(count.incrementAndGet());

        // Create the Environment
        EnvironmentDTO environmentDTO = environmentMapper.toDto(environment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnvironmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(environmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Environment in the database
        List<Environment> environmentList = environmentRepository.findAll();
        assertThat(environmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEnvironment() throws Exception {
        int databaseSizeBeforeUpdate = environmentRepository.findAll().size();
        environment.setId(count.incrementAndGet());

        // Create the Environment
        EnvironmentDTO environmentDTO = environmentMapper.toDto(environment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEnvironmentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(environmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Environment in the database
        List<Environment> environmentList = environmentRepository.findAll();
        assertThat(environmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEnvironment() throws Exception {
        // Initialize the database
        environmentRepository.saveAndFlush(environment);

        int databaseSizeBeforeDelete = environmentRepository.findAll().size();

        // Delete the environment
        restEnvironmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, environment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Environment> environmentList = environmentRepository.findAll();
        assertThat(environmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
