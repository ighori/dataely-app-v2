package com.dataely.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dataely.app.IntegrationTest;
import com.dataely.app.domain.AnalyzerAdHocRecognizers;
import com.dataely.app.repository.AnalyzerAdHocRecognizersRepository;
import com.dataely.app.service.dto.AnalyzerAdHocRecognizersDTO;
import com.dataely.app.service.mapper.AnalyzerAdHocRecognizersMapper;
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
 * Integration tests for the {@link AnalyzerAdHocRecognizersResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnalyzerAdHocRecognizersResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final String DEFAULT_RECOGNIZER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RECOGNIZER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPORTED_LANG = "AAAAAAAAAA";
    private static final String UPDATED_SUPPORTED_LANG = "BBBBBBBBBB";

    private static final String DEFAULT_PATTERN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PATTERN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REGEX = "AAAAAAAAAA";
    private static final String UPDATED_REGEX = "BBBBBBBBBB";

    private static final Float DEFAULT_SCORE = 1F;
    private static final Float UPDATED_SCORE = 2F;

    private static final String DEFAULT_CONTEXT = "AAAAAAAAAA";
    private static final String UPDATED_CONTEXT = "BBBBBBBBBB";

    private static final String DEFAULT_DENY_LIST = "AAAAAAAAAA";
    private static final String UPDATED_DENY_LIST = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPORTED_ENTITY = "AAAAAAAAAA";
    private static final String UPDATED_SUPPORTED_ENTITY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/analyzer-ad-hoc-recognizers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnalyzerAdHocRecognizersRepository analyzerAdHocRecognizersRepository;

    @Autowired
    private AnalyzerAdHocRecognizersMapper analyzerAdHocRecognizersMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnalyzerAdHocRecognizersMockMvc;

    private AnalyzerAdHocRecognizers analyzerAdHocRecognizers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnalyzerAdHocRecognizers createEntity(EntityManager em) {
        AnalyzerAdHocRecognizers analyzerAdHocRecognizers = new AnalyzerAdHocRecognizers()
            .name(DEFAULT_NAME)
            .detail(DEFAULT_DETAIL)
            .recognizerName(DEFAULT_RECOGNIZER_NAME)
            .supportedLang(DEFAULT_SUPPORTED_LANG)
            .patternName(DEFAULT_PATTERN_NAME)
            .regex(DEFAULT_REGEX)
            .score(DEFAULT_SCORE)
            .context(DEFAULT_CONTEXT)
            .denyList(DEFAULT_DENY_LIST)
            .supportedEntity(DEFAULT_SUPPORTED_ENTITY)
            .creationDate(DEFAULT_CREATION_DATE)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return analyzerAdHocRecognizers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnalyzerAdHocRecognizers createUpdatedEntity(EntityManager em) {
        AnalyzerAdHocRecognizers analyzerAdHocRecognizers = new AnalyzerAdHocRecognizers()
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .recognizerName(UPDATED_RECOGNIZER_NAME)
            .supportedLang(UPDATED_SUPPORTED_LANG)
            .patternName(UPDATED_PATTERN_NAME)
            .regex(UPDATED_REGEX)
            .score(UPDATED_SCORE)
            .context(UPDATED_CONTEXT)
            .denyList(UPDATED_DENY_LIST)
            .supportedEntity(UPDATED_SUPPORTED_ENTITY)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return analyzerAdHocRecognizers;
    }

    @BeforeEach
    public void initTest() {
        analyzerAdHocRecognizers = createEntity(em);
    }

    @Test
    @Transactional
    void createAnalyzerAdHocRecognizers() throws Exception {
        int databaseSizeBeforeCreate = analyzerAdHocRecognizersRepository.findAll().size();
        // Create the AnalyzerAdHocRecognizers
        AnalyzerAdHocRecognizersDTO analyzerAdHocRecognizersDTO = analyzerAdHocRecognizersMapper.toDto(analyzerAdHocRecognizers);
        restAnalyzerAdHocRecognizersMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerAdHocRecognizersDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AnalyzerAdHocRecognizers in the database
        List<AnalyzerAdHocRecognizers> analyzerAdHocRecognizersList = analyzerAdHocRecognizersRepository.findAll();
        assertThat(analyzerAdHocRecognizersList).hasSize(databaseSizeBeforeCreate + 1);
        AnalyzerAdHocRecognizers testAnalyzerAdHocRecognizers = analyzerAdHocRecognizersList.get(analyzerAdHocRecognizersList.size() - 1);
        assertThat(testAnalyzerAdHocRecognizers.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAnalyzerAdHocRecognizers.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testAnalyzerAdHocRecognizers.getRecognizerName()).isEqualTo(DEFAULT_RECOGNIZER_NAME);
        assertThat(testAnalyzerAdHocRecognizers.getSupportedLang()).isEqualTo(DEFAULT_SUPPORTED_LANG);
        assertThat(testAnalyzerAdHocRecognizers.getPatternName()).isEqualTo(DEFAULT_PATTERN_NAME);
        assertThat(testAnalyzerAdHocRecognizers.getRegex()).isEqualTo(DEFAULT_REGEX);
        assertThat(testAnalyzerAdHocRecognizers.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testAnalyzerAdHocRecognizers.getContext()).isEqualTo(DEFAULT_CONTEXT);
        assertThat(testAnalyzerAdHocRecognizers.getDenyList()).isEqualTo(DEFAULT_DENY_LIST);
        assertThat(testAnalyzerAdHocRecognizers.getSupportedEntity()).isEqualTo(DEFAULT_SUPPORTED_ENTITY);
        assertThat(testAnalyzerAdHocRecognizers.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAnalyzerAdHocRecognizers.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void createAnalyzerAdHocRecognizersWithExistingId() throws Exception {
        // Create the AnalyzerAdHocRecognizers with an existing ID
        analyzerAdHocRecognizers.setId(1L);
        AnalyzerAdHocRecognizersDTO analyzerAdHocRecognizersDTO = analyzerAdHocRecognizersMapper.toDto(analyzerAdHocRecognizers);

        int databaseSizeBeforeCreate = analyzerAdHocRecognizersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnalyzerAdHocRecognizersMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerAdHocRecognizersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerAdHocRecognizers in the database
        List<AnalyzerAdHocRecognizers> analyzerAdHocRecognizersList = analyzerAdHocRecognizersRepository.findAll();
        assertThat(analyzerAdHocRecognizersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = analyzerAdHocRecognizersRepository.findAll().size();
        // set the field null
        analyzerAdHocRecognizers.setName(null);

        // Create the AnalyzerAdHocRecognizers, which fails.
        AnalyzerAdHocRecognizersDTO analyzerAdHocRecognizersDTO = analyzerAdHocRecognizersMapper.toDto(analyzerAdHocRecognizers);

        restAnalyzerAdHocRecognizersMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerAdHocRecognizersDTO))
            )
            .andExpect(status().isBadRequest());

        List<AnalyzerAdHocRecognizers> analyzerAdHocRecognizersList = analyzerAdHocRecognizersRepository.findAll();
        assertThat(analyzerAdHocRecognizersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAnalyzerAdHocRecognizers() throws Exception {
        // Initialize the database
        analyzerAdHocRecognizersRepository.saveAndFlush(analyzerAdHocRecognizers);

        // Get all the analyzerAdHocRecognizersList
        restAnalyzerAdHocRecognizersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(analyzerAdHocRecognizers.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].recognizerName").value(hasItem(DEFAULT_RECOGNIZER_NAME)))
            .andExpect(jsonPath("$.[*].supportedLang").value(hasItem(DEFAULT_SUPPORTED_LANG)))
            .andExpect(jsonPath("$.[*].patternName").value(hasItem(DEFAULT_PATTERN_NAME)))
            .andExpect(jsonPath("$.[*].regex").value(hasItem(DEFAULT_REGEX)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].context").value(hasItem(DEFAULT_CONTEXT)))
            .andExpect(jsonPath("$.[*].denyList").value(hasItem(DEFAULT_DENY_LIST)))
            .andExpect(jsonPath("$.[*].supportedEntity").value(hasItem(DEFAULT_SUPPORTED_ENTITY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getAnalyzerAdHocRecognizers() throws Exception {
        // Initialize the database
        analyzerAdHocRecognizersRepository.saveAndFlush(analyzerAdHocRecognizers);

        // Get the analyzerAdHocRecognizers
        restAnalyzerAdHocRecognizersMockMvc
            .perform(get(ENTITY_API_URL_ID, analyzerAdHocRecognizers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(analyzerAdHocRecognizers.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL))
            .andExpect(jsonPath("$.recognizerName").value(DEFAULT_RECOGNIZER_NAME))
            .andExpect(jsonPath("$.supportedLang").value(DEFAULT_SUPPORTED_LANG))
            .andExpect(jsonPath("$.patternName").value(DEFAULT_PATTERN_NAME))
            .andExpect(jsonPath("$.regex").value(DEFAULT_REGEX))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.doubleValue()))
            .andExpect(jsonPath("$.context").value(DEFAULT_CONTEXT))
            .andExpect(jsonPath("$.denyList").value(DEFAULT_DENY_LIST))
            .andExpect(jsonPath("$.supportedEntity").value(DEFAULT_SUPPORTED_ENTITY))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAnalyzerAdHocRecognizers() throws Exception {
        // Get the analyzerAdHocRecognizers
        restAnalyzerAdHocRecognizersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAnalyzerAdHocRecognizers() throws Exception {
        // Initialize the database
        analyzerAdHocRecognizersRepository.saveAndFlush(analyzerAdHocRecognizers);

        int databaseSizeBeforeUpdate = analyzerAdHocRecognizersRepository.findAll().size();

        // Update the analyzerAdHocRecognizers
        AnalyzerAdHocRecognizers updatedAnalyzerAdHocRecognizers = analyzerAdHocRecognizersRepository
            .findById(analyzerAdHocRecognizers.getId())
            .get();
        // Disconnect from session so that the updates on updatedAnalyzerAdHocRecognizers are not directly saved in db
        em.detach(updatedAnalyzerAdHocRecognizers);
        updatedAnalyzerAdHocRecognizers
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .recognizerName(UPDATED_RECOGNIZER_NAME)
            .supportedLang(UPDATED_SUPPORTED_LANG)
            .patternName(UPDATED_PATTERN_NAME)
            .regex(UPDATED_REGEX)
            .score(UPDATED_SCORE)
            .context(UPDATED_CONTEXT)
            .denyList(UPDATED_DENY_LIST)
            .supportedEntity(UPDATED_SUPPORTED_ENTITY)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        AnalyzerAdHocRecognizersDTO analyzerAdHocRecognizersDTO = analyzerAdHocRecognizersMapper.toDto(updatedAnalyzerAdHocRecognizers);

        restAnalyzerAdHocRecognizersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, analyzerAdHocRecognizersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerAdHocRecognizersDTO))
            )
            .andExpect(status().isOk());

        // Validate the AnalyzerAdHocRecognizers in the database
        List<AnalyzerAdHocRecognizers> analyzerAdHocRecognizersList = analyzerAdHocRecognizersRepository.findAll();
        assertThat(analyzerAdHocRecognizersList).hasSize(databaseSizeBeforeUpdate);
        AnalyzerAdHocRecognizers testAnalyzerAdHocRecognizers = analyzerAdHocRecognizersList.get(analyzerAdHocRecognizersList.size() - 1);
        assertThat(testAnalyzerAdHocRecognizers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAnalyzerAdHocRecognizers.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testAnalyzerAdHocRecognizers.getRecognizerName()).isEqualTo(UPDATED_RECOGNIZER_NAME);
        assertThat(testAnalyzerAdHocRecognizers.getSupportedLang()).isEqualTo(UPDATED_SUPPORTED_LANG);
        assertThat(testAnalyzerAdHocRecognizers.getPatternName()).isEqualTo(UPDATED_PATTERN_NAME);
        assertThat(testAnalyzerAdHocRecognizers.getRegex()).isEqualTo(UPDATED_REGEX);
        assertThat(testAnalyzerAdHocRecognizers.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testAnalyzerAdHocRecognizers.getContext()).isEqualTo(UPDATED_CONTEXT);
        assertThat(testAnalyzerAdHocRecognizers.getDenyList()).isEqualTo(UPDATED_DENY_LIST);
        assertThat(testAnalyzerAdHocRecognizers.getSupportedEntity()).isEqualTo(UPDATED_SUPPORTED_ENTITY);
        assertThat(testAnalyzerAdHocRecognizers.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAnalyzerAdHocRecognizers.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingAnalyzerAdHocRecognizers() throws Exception {
        int databaseSizeBeforeUpdate = analyzerAdHocRecognizersRepository.findAll().size();
        analyzerAdHocRecognizers.setId(count.incrementAndGet());

        // Create the AnalyzerAdHocRecognizers
        AnalyzerAdHocRecognizersDTO analyzerAdHocRecognizersDTO = analyzerAdHocRecognizersMapper.toDto(analyzerAdHocRecognizers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalyzerAdHocRecognizersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, analyzerAdHocRecognizersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerAdHocRecognizersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerAdHocRecognizers in the database
        List<AnalyzerAdHocRecognizers> analyzerAdHocRecognizersList = analyzerAdHocRecognizersRepository.findAll();
        assertThat(analyzerAdHocRecognizersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnalyzerAdHocRecognizers() throws Exception {
        int databaseSizeBeforeUpdate = analyzerAdHocRecognizersRepository.findAll().size();
        analyzerAdHocRecognizers.setId(count.incrementAndGet());

        // Create the AnalyzerAdHocRecognizers
        AnalyzerAdHocRecognizersDTO analyzerAdHocRecognizersDTO = analyzerAdHocRecognizersMapper.toDto(analyzerAdHocRecognizers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyzerAdHocRecognizersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerAdHocRecognizersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerAdHocRecognizers in the database
        List<AnalyzerAdHocRecognizers> analyzerAdHocRecognizersList = analyzerAdHocRecognizersRepository.findAll();
        assertThat(analyzerAdHocRecognizersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnalyzerAdHocRecognizers() throws Exception {
        int databaseSizeBeforeUpdate = analyzerAdHocRecognizersRepository.findAll().size();
        analyzerAdHocRecognizers.setId(count.incrementAndGet());

        // Create the AnalyzerAdHocRecognizers
        AnalyzerAdHocRecognizersDTO analyzerAdHocRecognizersDTO = analyzerAdHocRecognizersMapper.toDto(analyzerAdHocRecognizers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyzerAdHocRecognizersMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerAdHocRecognizersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnalyzerAdHocRecognizers in the database
        List<AnalyzerAdHocRecognizers> analyzerAdHocRecognizersList = analyzerAdHocRecognizersRepository.findAll();
        assertThat(analyzerAdHocRecognizersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnalyzerAdHocRecognizersWithPatch() throws Exception {
        // Initialize the database
        analyzerAdHocRecognizersRepository.saveAndFlush(analyzerAdHocRecognizers);

        int databaseSizeBeforeUpdate = analyzerAdHocRecognizersRepository.findAll().size();

        // Update the analyzerAdHocRecognizers using partial update
        AnalyzerAdHocRecognizers partialUpdatedAnalyzerAdHocRecognizers = new AnalyzerAdHocRecognizers();
        partialUpdatedAnalyzerAdHocRecognizers.setId(analyzerAdHocRecognizers.getId());

        partialUpdatedAnalyzerAdHocRecognizers
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .recognizerName(UPDATED_RECOGNIZER_NAME)
            .supportedLang(UPDATED_SUPPORTED_LANG)
            .denyList(UPDATED_DENY_LIST)
            .supportedEntity(UPDATED_SUPPORTED_ENTITY)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restAnalyzerAdHocRecognizersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnalyzerAdHocRecognizers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnalyzerAdHocRecognizers))
            )
            .andExpect(status().isOk());

        // Validate the AnalyzerAdHocRecognizers in the database
        List<AnalyzerAdHocRecognizers> analyzerAdHocRecognizersList = analyzerAdHocRecognizersRepository.findAll();
        assertThat(analyzerAdHocRecognizersList).hasSize(databaseSizeBeforeUpdate);
        AnalyzerAdHocRecognizers testAnalyzerAdHocRecognizers = analyzerAdHocRecognizersList.get(analyzerAdHocRecognizersList.size() - 1);
        assertThat(testAnalyzerAdHocRecognizers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAnalyzerAdHocRecognizers.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testAnalyzerAdHocRecognizers.getRecognizerName()).isEqualTo(UPDATED_RECOGNIZER_NAME);
        assertThat(testAnalyzerAdHocRecognizers.getSupportedLang()).isEqualTo(UPDATED_SUPPORTED_LANG);
        assertThat(testAnalyzerAdHocRecognizers.getPatternName()).isEqualTo(DEFAULT_PATTERN_NAME);
        assertThat(testAnalyzerAdHocRecognizers.getRegex()).isEqualTo(DEFAULT_REGEX);
        assertThat(testAnalyzerAdHocRecognizers.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testAnalyzerAdHocRecognizers.getContext()).isEqualTo(DEFAULT_CONTEXT);
        assertThat(testAnalyzerAdHocRecognizers.getDenyList()).isEqualTo(UPDATED_DENY_LIST);
        assertThat(testAnalyzerAdHocRecognizers.getSupportedEntity()).isEqualTo(UPDATED_SUPPORTED_ENTITY);
        assertThat(testAnalyzerAdHocRecognizers.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAnalyzerAdHocRecognizers.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateAnalyzerAdHocRecognizersWithPatch() throws Exception {
        // Initialize the database
        analyzerAdHocRecognizersRepository.saveAndFlush(analyzerAdHocRecognizers);

        int databaseSizeBeforeUpdate = analyzerAdHocRecognizersRepository.findAll().size();

        // Update the analyzerAdHocRecognizers using partial update
        AnalyzerAdHocRecognizers partialUpdatedAnalyzerAdHocRecognizers = new AnalyzerAdHocRecognizers();
        partialUpdatedAnalyzerAdHocRecognizers.setId(analyzerAdHocRecognizers.getId());

        partialUpdatedAnalyzerAdHocRecognizers
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .recognizerName(UPDATED_RECOGNIZER_NAME)
            .supportedLang(UPDATED_SUPPORTED_LANG)
            .patternName(UPDATED_PATTERN_NAME)
            .regex(UPDATED_REGEX)
            .score(UPDATED_SCORE)
            .context(UPDATED_CONTEXT)
            .denyList(UPDATED_DENY_LIST)
            .supportedEntity(UPDATED_SUPPORTED_ENTITY)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restAnalyzerAdHocRecognizersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnalyzerAdHocRecognizers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnalyzerAdHocRecognizers))
            )
            .andExpect(status().isOk());

        // Validate the AnalyzerAdHocRecognizers in the database
        List<AnalyzerAdHocRecognizers> analyzerAdHocRecognizersList = analyzerAdHocRecognizersRepository.findAll();
        assertThat(analyzerAdHocRecognizersList).hasSize(databaseSizeBeforeUpdate);
        AnalyzerAdHocRecognizers testAnalyzerAdHocRecognizers = analyzerAdHocRecognizersList.get(analyzerAdHocRecognizersList.size() - 1);
        assertThat(testAnalyzerAdHocRecognizers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAnalyzerAdHocRecognizers.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testAnalyzerAdHocRecognizers.getRecognizerName()).isEqualTo(UPDATED_RECOGNIZER_NAME);
        assertThat(testAnalyzerAdHocRecognizers.getSupportedLang()).isEqualTo(UPDATED_SUPPORTED_LANG);
        assertThat(testAnalyzerAdHocRecognizers.getPatternName()).isEqualTo(UPDATED_PATTERN_NAME);
        assertThat(testAnalyzerAdHocRecognizers.getRegex()).isEqualTo(UPDATED_REGEX);
        assertThat(testAnalyzerAdHocRecognizers.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testAnalyzerAdHocRecognizers.getContext()).isEqualTo(UPDATED_CONTEXT);
        assertThat(testAnalyzerAdHocRecognizers.getDenyList()).isEqualTo(UPDATED_DENY_LIST);
        assertThat(testAnalyzerAdHocRecognizers.getSupportedEntity()).isEqualTo(UPDATED_SUPPORTED_ENTITY);
        assertThat(testAnalyzerAdHocRecognizers.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAnalyzerAdHocRecognizers.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingAnalyzerAdHocRecognizers() throws Exception {
        int databaseSizeBeforeUpdate = analyzerAdHocRecognizersRepository.findAll().size();
        analyzerAdHocRecognizers.setId(count.incrementAndGet());

        // Create the AnalyzerAdHocRecognizers
        AnalyzerAdHocRecognizersDTO analyzerAdHocRecognizersDTO = analyzerAdHocRecognizersMapper.toDto(analyzerAdHocRecognizers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalyzerAdHocRecognizersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, analyzerAdHocRecognizersDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analyzerAdHocRecognizersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerAdHocRecognizers in the database
        List<AnalyzerAdHocRecognizers> analyzerAdHocRecognizersList = analyzerAdHocRecognizersRepository.findAll();
        assertThat(analyzerAdHocRecognizersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnalyzerAdHocRecognizers() throws Exception {
        int databaseSizeBeforeUpdate = analyzerAdHocRecognizersRepository.findAll().size();
        analyzerAdHocRecognizers.setId(count.incrementAndGet());

        // Create the AnalyzerAdHocRecognizers
        AnalyzerAdHocRecognizersDTO analyzerAdHocRecognizersDTO = analyzerAdHocRecognizersMapper.toDto(analyzerAdHocRecognizers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyzerAdHocRecognizersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analyzerAdHocRecognizersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerAdHocRecognizers in the database
        List<AnalyzerAdHocRecognizers> analyzerAdHocRecognizersList = analyzerAdHocRecognizersRepository.findAll();
        assertThat(analyzerAdHocRecognizersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnalyzerAdHocRecognizers() throws Exception {
        int databaseSizeBeforeUpdate = analyzerAdHocRecognizersRepository.findAll().size();
        analyzerAdHocRecognizers.setId(count.incrementAndGet());

        // Create the AnalyzerAdHocRecognizers
        AnalyzerAdHocRecognizersDTO analyzerAdHocRecognizersDTO = analyzerAdHocRecognizersMapper.toDto(analyzerAdHocRecognizers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyzerAdHocRecognizersMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analyzerAdHocRecognizersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnalyzerAdHocRecognizers in the database
        List<AnalyzerAdHocRecognizers> analyzerAdHocRecognizersList = analyzerAdHocRecognizersRepository.findAll();
        assertThat(analyzerAdHocRecognizersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnalyzerAdHocRecognizers() throws Exception {
        // Initialize the database
        analyzerAdHocRecognizersRepository.saveAndFlush(analyzerAdHocRecognizers);

        int databaseSizeBeforeDelete = analyzerAdHocRecognizersRepository.findAll().size();

        // Delete the analyzerAdHocRecognizers
        restAnalyzerAdHocRecognizersMockMvc
            .perform(delete(ENTITY_API_URL_ID, analyzerAdHocRecognizers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnalyzerAdHocRecognizers> analyzerAdHocRecognizersList = analyzerAdHocRecognizersRepository.findAll();
        assertThat(analyzerAdHocRecognizersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
