package com.dataely.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dataely.app.IntegrationTest;
import com.dataely.app.domain.AnalyzerEntities;
import com.dataely.app.repository.AnalyzerEntitiesRepository;
import com.dataely.app.service.dto.AnalyzerEntitiesDTO;
import com.dataely.app.service.mapper.AnalyzerEntitiesMapper;
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
 * Integration tests for the {@link AnalyzerEntitiesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnalyzerEntitiesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ENTITY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/analyzer-entities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnalyzerEntitiesRepository analyzerEntitiesRepository;

    @Autowired
    private AnalyzerEntitiesMapper analyzerEntitiesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnalyzerEntitiesMockMvc;

    private AnalyzerEntities analyzerEntities;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnalyzerEntities createEntity(EntityManager em) {
        AnalyzerEntities analyzerEntities = new AnalyzerEntities()
            .name(DEFAULT_NAME)
            .detail(DEFAULT_DETAIL)
            .entityName(DEFAULT_ENTITY_NAME)
            .creationDate(DEFAULT_CREATION_DATE)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return analyzerEntities;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnalyzerEntities createUpdatedEntity(EntityManager em) {
        AnalyzerEntities analyzerEntities = new AnalyzerEntities()
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .entityName(UPDATED_ENTITY_NAME)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return analyzerEntities;
    }

    @BeforeEach
    public void initTest() {
        analyzerEntities = createEntity(em);
    }

    @Test
    @Transactional
    void createAnalyzerEntities() throws Exception {
        int databaseSizeBeforeCreate = analyzerEntitiesRepository.findAll().size();
        // Create the AnalyzerEntities
        AnalyzerEntitiesDTO analyzerEntitiesDTO = analyzerEntitiesMapper.toDto(analyzerEntities);
        restAnalyzerEntitiesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analyzerEntitiesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AnalyzerEntities in the database
        List<AnalyzerEntities> analyzerEntitiesList = analyzerEntitiesRepository.findAll();
        assertThat(analyzerEntitiesList).hasSize(databaseSizeBeforeCreate + 1);
        AnalyzerEntities testAnalyzerEntities = analyzerEntitiesList.get(analyzerEntitiesList.size() - 1);
        assertThat(testAnalyzerEntities.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAnalyzerEntities.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testAnalyzerEntities.getEntityName()).isEqualTo(DEFAULT_ENTITY_NAME);
        assertThat(testAnalyzerEntities.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAnalyzerEntities.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void createAnalyzerEntitiesWithExistingId() throws Exception {
        // Create the AnalyzerEntities with an existing ID
        analyzerEntities.setId(1L);
        AnalyzerEntitiesDTO analyzerEntitiesDTO = analyzerEntitiesMapper.toDto(analyzerEntities);

        int databaseSizeBeforeCreate = analyzerEntitiesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnalyzerEntitiesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analyzerEntitiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerEntities in the database
        List<AnalyzerEntities> analyzerEntitiesList = analyzerEntitiesRepository.findAll();
        assertThat(analyzerEntitiesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = analyzerEntitiesRepository.findAll().size();
        // set the field null
        analyzerEntities.setName(null);

        // Create the AnalyzerEntities, which fails.
        AnalyzerEntitiesDTO analyzerEntitiesDTO = analyzerEntitiesMapper.toDto(analyzerEntities);

        restAnalyzerEntitiesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analyzerEntitiesDTO))
            )
            .andExpect(status().isBadRequest());

        List<AnalyzerEntities> analyzerEntitiesList = analyzerEntitiesRepository.findAll();
        assertThat(analyzerEntitiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAnalyzerEntities() throws Exception {
        // Initialize the database
        analyzerEntitiesRepository.saveAndFlush(analyzerEntities);

        // Get all the analyzerEntitiesList
        restAnalyzerEntitiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(analyzerEntities.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].entityName").value(hasItem(DEFAULT_ENTITY_NAME)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getAnalyzerEntities() throws Exception {
        // Initialize the database
        analyzerEntitiesRepository.saveAndFlush(analyzerEntities);

        // Get the analyzerEntities
        restAnalyzerEntitiesMockMvc
            .perform(get(ENTITY_API_URL_ID, analyzerEntities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(analyzerEntities.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL))
            .andExpect(jsonPath("$.entityName").value(DEFAULT_ENTITY_NAME))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAnalyzerEntities() throws Exception {
        // Get the analyzerEntities
        restAnalyzerEntitiesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAnalyzerEntities() throws Exception {
        // Initialize the database
        analyzerEntitiesRepository.saveAndFlush(analyzerEntities);

        int databaseSizeBeforeUpdate = analyzerEntitiesRepository.findAll().size();

        // Update the analyzerEntities
        AnalyzerEntities updatedAnalyzerEntities = analyzerEntitiesRepository.findById(analyzerEntities.getId()).get();
        // Disconnect from session so that the updates on updatedAnalyzerEntities are not directly saved in db
        em.detach(updatedAnalyzerEntities);
        updatedAnalyzerEntities
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .entityName(UPDATED_ENTITY_NAME)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        AnalyzerEntitiesDTO analyzerEntitiesDTO = analyzerEntitiesMapper.toDto(updatedAnalyzerEntities);

        restAnalyzerEntitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, analyzerEntitiesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerEntitiesDTO))
            )
            .andExpect(status().isOk());

        // Validate the AnalyzerEntities in the database
        List<AnalyzerEntities> analyzerEntitiesList = analyzerEntitiesRepository.findAll();
        assertThat(analyzerEntitiesList).hasSize(databaseSizeBeforeUpdate);
        AnalyzerEntities testAnalyzerEntities = analyzerEntitiesList.get(analyzerEntitiesList.size() - 1);
        assertThat(testAnalyzerEntities.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAnalyzerEntities.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testAnalyzerEntities.getEntityName()).isEqualTo(UPDATED_ENTITY_NAME);
        assertThat(testAnalyzerEntities.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAnalyzerEntities.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingAnalyzerEntities() throws Exception {
        int databaseSizeBeforeUpdate = analyzerEntitiesRepository.findAll().size();
        analyzerEntities.setId(count.incrementAndGet());

        // Create the AnalyzerEntities
        AnalyzerEntitiesDTO analyzerEntitiesDTO = analyzerEntitiesMapper.toDto(analyzerEntities);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalyzerEntitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, analyzerEntitiesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerEntitiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerEntities in the database
        List<AnalyzerEntities> analyzerEntitiesList = analyzerEntitiesRepository.findAll();
        assertThat(analyzerEntitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnalyzerEntities() throws Exception {
        int databaseSizeBeforeUpdate = analyzerEntitiesRepository.findAll().size();
        analyzerEntities.setId(count.incrementAndGet());

        // Create the AnalyzerEntities
        AnalyzerEntitiesDTO analyzerEntitiesDTO = analyzerEntitiesMapper.toDto(analyzerEntities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyzerEntitiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerEntitiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerEntities in the database
        List<AnalyzerEntities> analyzerEntitiesList = analyzerEntitiesRepository.findAll();
        assertThat(analyzerEntitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnalyzerEntities() throws Exception {
        int databaseSizeBeforeUpdate = analyzerEntitiesRepository.findAll().size();
        analyzerEntities.setId(count.incrementAndGet());

        // Create the AnalyzerEntities
        AnalyzerEntitiesDTO analyzerEntitiesDTO = analyzerEntitiesMapper.toDto(analyzerEntities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyzerEntitiesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analyzerEntitiesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnalyzerEntities in the database
        List<AnalyzerEntities> analyzerEntitiesList = analyzerEntitiesRepository.findAll();
        assertThat(analyzerEntitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnalyzerEntitiesWithPatch() throws Exception {
        // Initialize the database
        analyzerEntitiesRepository.saveAndFlush(analyzerEntities);

        int databaseSizeBeforeUpdate = analyzerEntitiesRepository.findAll().size();

        // Update the analyzerEntities using partial update
        AnalyzerEntities partialUpdatedAnalyzerEntities = new AnalyzerEntities();
        partialUpdatedAnalyzerEntities.setId(analyzerEntities.getId());

        partialUpdatedAnalyzerEntities.name(UPDATED_NAME).lastUpdated(UPDATED_LAST_UPDATED);

        restAnalyzerEntitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnalyzerEntities.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnalyzerEntities))
            )
            .andExpect(status().isOk());

        // Validate the AnalyzerEntities in the database
        List<AnalyzerEntities> analyzerEntitiesList = analyzerEntitiesRepository.findAll();
        assertThat(analyzerEntitiesList).hasSize(databaseSizeBeforeUpdate);
        AnalyzerEntities testAnalyzerEntities = analyzerEntitiesList.get(analyzerEntitiesList.size() - 1);
        assertThat(testAnalyzerEntities.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAnalyzerEntities.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testAnalyzerEntities.getEntityName()).isEqualTo(DEFAULT_ENTITY_NAME);
        assertThat(testAnalyzerEntities.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAnalyzerEntities.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateAnalyzerEntitiesWithPatch() throws Exception {
        // Initialize the database
        analyzerEntitiesRepository.saveAndFlush(analyzerEntities);

        int databaseSizeBeforeUpdate = analyzerEntitiesRepository.findAll().size();

        // Update the analyzerEntities using partial update
        AnalyzerEntities partialUpdatedAnalyzerEntities = new AnalyzerEntities();
        partialUpdatedAnalyzerEntities.setId(analyzerEntities.getId());

        partialUpdatedAnalyzerEntities
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .entityName(UPDATED_ENTITY_NAME)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restAnalyzerEntitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnalyzerEntities.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnalyzerEntities))
            )
            .andExpect(status().isOk());

        // Validate the AnalyzerEntities in the database
        List<AnalyzerEntities> analyzerEntitiesList = analyzerEntitiesRepository.findAll();
        assertThat(analyzerEntitiesList).hasSize(databaseSizeBeforeUpdate);
        AnalyzerEntities testAnalyzerEntities = analyzerEntitiesList.get(analyzerEntitiesList.size() - 1);
        assertThat(testAnalyzerEntities.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAnalyzerEntities.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testAnalyzerEntities.getEntityName()).isEqualTo(UPDATED_ENTITY_NAME);
        assertThat(testAnalyzerEntities.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAnalyzerEntities.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingAnalyzerEntities() throws Exception {
        int databaseSizeBeforeUpdate = analyzerEntitiesRepository.findAll().size();
        analyzerEntities.setId(count.incrementAndGet());

        // Create the AnalyzerEntities
        AnalyzerEntitiesDTO analyzerEntitiesDTO = analyzerEntitiesMapper.toDto(analyzerEntities);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalyzerEntitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, analyzerEntitiesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analyzerEntitiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerEntities in the database
        List<AnalyzerEntities> analyzerEntitiesList = analyzerEntitiesRepository.findAll();
        assertThat(analyzerEntitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnalyzerEntities() throws Exception {
        int databaseSizeBeforeUpdate = analyzerEntitiesRepository.findAll().size();
        analyzerEntities.setId(count.incrementAndGet());

        // Create the AnalyzerEntities
        AnalyzerEntitiesDTO analyzerEntitiesDTO = analyzerEntitiesMapper.toDto(analyzerEntities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyzerEntitiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analyzerEntitiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerEntities in the database
        List<AnalyzerEntities> analyzerEntitiesList = analyzerEntitiesRepository.findAll();
        assertThat(analyzerEntitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnalyzerEntities() throws Exception {
        int databaseSizeBeforeUpdate = analyzerEntitiesRepository.findAll().size();
        analyzerEntities.setId(count.incrementAndGet());

        // Create the AnalyzerEntities
        AnalyzerEntitiesDTO analyzerEntitiesDTO = analyzerEntitiesMapper.toDto(analyzerEntities);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyzerEntitiesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analyzerEntitiesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnalyzerEntities in the database
        List<AnalyzerEntities> analyzerEntitiesList = analyzerEntitiesRepository.findAll();
        assertThat(analyzerEntitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnalyzerEntities() throws Exception {
        // Initialize the database
        analyzerEntitiesRepository.saveAndFlush(analyzerEntities);

        int databaseSizeBeforeDelete = analyzerEntitiesRepository.findAll().size();

        // Delete the analyzerEntities
        restAnalyzerEntitiesMockMvc
            .perform(delete(ENTITY_API_URL_ID, analyzerEntities.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnalyzerEntities> analyzerEntitiesList = analyzerEntitiesRepository.findAll();
        assertThat(analyzerEntitiesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
