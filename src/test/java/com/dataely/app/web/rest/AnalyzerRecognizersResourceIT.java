package com.dataely.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dataely.app.IntegrationTest;
import com.dataely.app.domain.AnalyzerRecognizers;
import com.dataely.app.repository.AnalyzerRecognizersRepository;
import com.dataely.app.service.dto.AnalyzerRecognizersDTO;
import com.dataely.app.service.mapper.AnalyzerRecognizersMapper;
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
 * Integration tests for the {@link AnalyzerRecognizersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnalyzerRecognizersResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final String DEFAULT_RECOGNIZER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RECOGNIZER_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/analyzer-recognizers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnalyzerRecognizersRepository analyzerRecognizersRepository;

    @Autowired
    private AnalyzerRecognizersMapper analyzerRecognizersMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnalyzerRecognizersMockMvc;

    private AnalyzerRecognizers analyzerRecognizers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnalyzerRecognizers createEntity(EntityManager em) {
        AnalyzerRecognizers analyzerRecognizers = new AnalyzerRecognizers()
            .name(DEFAULT_NAME)
            .detail(DEFAULT_DETAIL)
            .recognizerName(DEFAULT_RECOGNIZER_NAME)
            .creationDate(DEFAULT_CREATION_DATE)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return analyzerRecognizers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnalyzerRecognizers createUpdatedEntity(EntityManager em) {
        AnalyzerRecognizers analyzerRecognizers = new AnalyzerRecognizers()
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .recognizerName(UPDATED_RECOGNIZER_NAME)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return analyzerRecognizers;
    }

    @BeforeEach
    public void initTest() {
        analyzerRecognizers = createEntity(em);
    }

    @Test
    @Transactional
    void createAnalyzerRecognizers() throws Exception {
        int databaseSizeBeforeCreate = analyzerRecognizersRepository.findAll().size();
        // Create the AnalyzerRecognizers
        AnalyzerRecognizersDTO analyzerRecognizersDTO = analyzerRecognizersMapper.toDto(analyzerRecognizers);
        restAnalyzerRecognizersMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerRecognizersDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AnalyzerRecognizers in the database
        List<AnalyzerRecognizers> analyzerRecognizersList = analyzerRecognizersRepository.findAll();
        assertThat(analyzerRecognizersList).hasSize(databaseSizeBeforeCreate + 1);
        AnalyzerRecognizers testAnalyzerRecognizers = analyzerRecognizersList.get(analyzerRecognizersList.size() - 1);
        assertThat(testAnalyzerRecognizers.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAnalyzerRecognizers.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testAnalyzerRecognizers.getRecognizerName()).isEqualTo(DEFAULT_RECOGNIZER_NAME);
        assertThat(testAnalyzerRecognizers.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAnalyzerRecognizers.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void createAnalyzerRecognizersWithExistingId() throws Exception {
        // Create the AnalyzerRecognizers with an existing ID
        analyzerRecognizers.setId(1L);
        AnalyzerRecognizersDTO analyzerRecognizersDTO = analyzerRecognizersMapper.toDto(analyzerRecognizers);

        int databaseSizeBeforeCreate = analyzerRecognizersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnalyzerRecognizersMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerRecognizersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerRecognizers in the database
        List<AnalyzerRecognizers> analyzerRecognizersList = analyzerRecognizersRepository.findAll();
        assertThat(analyzerRecognizersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = analyzerRecognizersRepository.findAll().size();
        // set the field null
        analyzerRecognizers.setName(null);

        // Create the AnalyzerRecognizers, which fails.
        AnalyzerRecognizersDTO analyzerRecognizersDTO = analyzerRecognizersMapper.toDto(analyzerRecognizers);

        restAnalyzerRecognizersMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerRecognizersDTO))
            )
            .andExpect(status().isBadRequest());

        List<AnalyzerRecognizers> analyzerRecognizersList = analyzerRecognizersRepository.findAll();
        assertThat(analyzerRecognizersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAnalyzerRecognizers() throws Exception {
        // Initialize the database
        analyzerRecognizersRepository.saveAndFlush(analyzerRecognizers);

        // Get all the analyzerRecognizersList
        restAnalyzerRecognizersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(analyzerRecognizers.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].recognizerName").value(hasItem(DEFAULT_RECOGNIZER_NAME)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getAnalyzerRecognizers() throws Exception {
        // Initialize the database
        analyzerRecognizersRepository.saveAndFlush(analyzerRecognizers);

        // Get the analyzerRecognizers
        restAnalyzerRecognizersMockMvc
            .perform(get(ENTITY_API_URL_ID, analyzerRecognizers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(analyzerRecognizers.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL))
            .andExpect(jsonPath("$.recognizerName").value(DEFAULT_RECOGNIZER_NAME))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAnalyzerRecognizers() throws Exception {
        // Get the analyzerRecognizers
        restAnalyzerRecognizersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAnalyzerRecognizers() throws Exception {
        // Initialize the database
        analyzerRecognizersRepository.saveAndFlush(analyzerRecognizers);

        int databaseSizeBeforeUpdate = analyzerRecognizersRepository.findAll().size();

        // Update the analyzerRecognizers
        AnalyzerRecognizers updatedAnalyzerRecognizers = analyzerRecognizersRepository.findById(analyzerRecognizers.getId()).get();
        // Disconnect from session so that the updates on updatedAnalyzerRecognizers are not directly saved in db
        em.detach(updatedAnalyzerRecognizers);
        updatedAnalyzerRecognizers
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .recognizerName(UPDATED_RECOGNIZER_NAME)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        AnalyzerRecognizersDTO analyzerRecognizersDTO = analyzerRecognizersMapper.toDto(updatedAnalyzerRecognizers);

        restAnalyzerRecognizersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, analyzerRecognizersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerRecognizersDTO))
            )
            .andExpect(status().isOk());

        // Validate the AnalyzerRecognizers in the database
        List<AnalyzerRecognizers> analyzerRecognizersList = analyzerRecognizersRepository.findAll();
        assertThat(analyzerRecognizersList).hasSize(databaseSizeBeforeUpdate);
        AnalyzerRecognizers testAnalyzerRecognizers = analyzerRecognizersList.get(analyzerRecognizersList.size() - 1);
        assertThat(testAnalyzerRecognizers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAnalyzerRecognizers.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testAnalyzerRecognizers.getRecognizerName()).isEqualTo(UPDATED_RECOGNIZER_NAME);
        assertThat(testAnalyzerRecognizers.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAnalyzerRecognizers.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingAnalyzerRecognizers() throws Exception {
        int databaseSizeBeforeUpdate = analyzerRecognizersRepository.findAll().size();
        analyzerRecognizers.setId(count.incrementAndGet());

        // Create the AnalyzerRecognizers
        AnalyzerRecognizersDTO analyzerRecognizersDTO = analyzerRecognizersMapper.toDto(analyzerRecognizers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalyzerRecognizersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, analyzerRecognizersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerRecognizersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerRecognizers in the database
        List<AnalyzerRecognizers> analyzerRecognizersList = analyzerRecognizersRepository.findAll();
        assertThat(analyzerRecognizersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnalyzerRecognizers() throws Exception {
        int databaseSizeBeforeUpdate = analyzerRecognizersRepository.findAll().size();
        analyzerRecognizers.setId(count.incrementAndGet());

        // Create the AnalyzerRecognizers
        AnalyzerRecognizersDTO analyzerRecognizersDTO = analyzerRecognizersMapper.toDto(analyzerRecognizers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyzerRecognizersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerRecognizersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerRecognizers in the database
        List<AnalyzerRecognizers> analyzerRecognizersList = analyzerRecognizersRepository.findAll();
        assertThat(analyzerRecognizersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnalyzerRecognizers() throws Exception {
        int databaseSizeBeforeUpdate = analyzerRecognizersRepository.findAll().size();
        analyzerRecognizers.setId(count.incrementAndGet());

        // Create the AnalyzerRecognizers
        AnalyzerRecognizersDTO analyzerRecognizersDTO = analyzerRecognizersMapper.toDto(analyzerRecognizers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyzerRecognizersMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerRecognizersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnalyzerRecognizers in the database
        List<AnalyzerRecognizers> analyzerRecognizersList = analyzerRecognizersRepository.findAll();
        assertThat(analyzerRecognizersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnalyzerRecognizersWithPatch() throws Exception {
        // Initialize the database
        analyzerRecognizersRepository.saveAndFlush(analyzerRecognizers);

        int databaseSizeBeforeUpdate = analyzerRecognizersRepository.findAll().size();

        // Update the analyzerRecognizers using partial update
        AnalyzerRecognizers partialUpdatedAnalyzerRecognizers = new AnalyzerRecognizers();
        partialUpdatedAnalyzerRecognizers.setId(analyzerRecognizers.getId());

        partialUpdatedAnalyzerRecognizers.name(UPDATED_NAME).detail(UPDATED_DETAIL).lastUpdated(UPDATED_LAST_UPDATED);

        restAnalyzerRecognizersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnalyzerRecognizers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnalyzerRecognizers))
            )
            .andExpect(status().isOk());

        // Validate the AnalyzerRecognizers in the database
        List<AnalyzerRecognizers> analyzerRecognizersList = analyzerRecognizersRepository.findAll();
        assertThat(analyzerRecognizersList).hasSize(databaseSizeBeforeUpdate);
        AnalyzerRecognizers testAnalyzerRecognizers = analyzerRecognizersList.get(analyzerRecognizersList.size() - 1);
        assertThat(testAnalyzerRecognizers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAnalyzerRecognizers.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testAnalyzerRecognizers.getRecognizerName()).isEqualTo(DEFAULT_RECOGNIZER_NAME);
        assertThat(testAnalyzerRecognizers.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAnalyzerRecognizers.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateAnalyzerRecognizersWithPatch() throws Exception {
        // Initialize the database
        analyzerRecognizersRepository.saveAndFlush(analyzerRecognizers);

        int databaseSizeBeforeUpdate = analyzerRecognizersRepository.findAll().size();

        // Update the analyzerRecognizers using partial update
        AnalyzerRecognizers partialUpdatedAnalyzerRecognizers = new AnalyzerRecognizers();
        partialUpdatedAnalyzerRecognizers.setId(analyzerRecognizers.getId());

        partialUpdatedAnalyzerRecognizers
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .recognizerName(UPDATED_RECOGNIZER_NAME)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restAnalyzerRecognizersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnalyzerRecognizers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnalyzerRecognizers))
            )
            .andExpect(status().isOk());

        // Validate the AnalyzerRecognizers in the database
        List<AnalyzerRecognizers> analyzerRecognizersList = analyzerRecognizersRepository.findAll();
        assertThat(analyzerRecognizersList).hasSize(databaseSizeBeforeUpdate);
        AnalyzerRecognizers testAnalyzerRecognizers = analyzerRecognizersList.get(analyzerRecognizersList.size() - 1);
        assertThat(testAnalyzerRecognizers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAnalyzerRecognizers.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testAnalyzerRecognizers.getRecognizerName()).isEqualTo(UPDATED_RECOGNIZER_NAME);
        assertThat(testAnalyzerRecognizers.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAnalyzerRecognizers.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingAnalyzerRecognizers() throws Exception {
        int databaseSizeBeforeUpdate = analyzerRecognizersRepository.findAll().size();
        analyzerRecognizers.setId(count.incrementAndGet());

        // Create the AnalyzerRecognizers
        AnalyzerRecognizersDTO analyzerRecognizersDTO = analyzerRecognizersMapper.toDto(analyzerRecognizers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalyzerRecognizersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, analyzerRecognizersDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analyzerRecognizersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerRecognizers in the database
        List<AnalyzerRecognizers> analyzerRecognizersList = analyzerRecognizersRepository.findAll();
        assertThat(analyzerRecognizersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnalyzerRecognizers() throws Exception {
        int databaseSizeBeforeUpdate = analyzerRecognizersRepository.findAll().size();
        analyzerRecognizers.setId(count.incrementAndGet());

        // Create the AnalyzerRecognizers
        AnalyzerRecognizersDTO analyzerRecognizersDTO = analyzerRecognizersMapper.toDto(analyzerRecognizers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyzerRecognizersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analyzerRecognizersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerRecognizers in the database
        List<AnalyzerRecognizers> analyzerRecognizersList = analyzerRecognizersRepository.findAll();
        assertThat(analyzerRecognizersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnalyzerRecognizers() throws Exception {
        int databaseSizeBeforeUpdate = analyzerRecognizersRepository.findAll().size();
        analyzerRecognizers.setId(count.incrementAndGet());

        // Create the AnalyzerRecognizers
        AnalyzerRecognizersDTO analyzerRecognizersDTO = analyzerRecognizersMapper.toDto(analyzerRecognizers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyzerRecognizersMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analyzerRecognizersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnalyzerRecognizers in the database
        List<AnalyzerRecognizers> analyzerRecognizersList = analyzerRecognizersRepository.findAll();
        assertThat(analyzerRecognizersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnalyzerRecognizers() throws Exception {
        // Initialize the database
        analyzerRecognizersRepository.saveAndFlush(analyzerRecognizers);

        int databaseSizeBeforeDelete = analyzerRecognizersRepository.findAll().size();

        // Delete the analyzerRecognizers
        restAnalyzerRecognizersMockMvc
            .perform(delete(ENTITY_API_URL_ID, analyzerRecognizers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnalyzerRecognizers> analyzerRecognizersList = analyzerRecognizersRepository.findAll();
        assertThat(analyzerRecognizersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
