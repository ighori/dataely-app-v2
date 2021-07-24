package com.dataely.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dataely.app.IntegrationTest;
import com.dataely.app.domain.AnalyzerResult;
import com.dataely.app.domain.enumeration.EAnalyzerObjectType;
import com.dataely.app.repository.AnalyzerResultRepository;
import com.dataely.app.service.dto.AnalyzerResultDTO;
import com.dataely.app.service.mapper.AnalyzerResultMapper;
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
 * Integration tests for the {@link AnalyzerResultResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnalyzerResultResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_OBJECT_ID = 1;
    private static final Integer UPDATED_OBJECT_ID = 2;

    private static final EAnalyzerObjectType DEFAULT_OBJECT_TYPE = EAnalyzerObjectType.FILE;
    private static final EAnalyzerObjectType UPDATED_OBJECT_TYPE = EAnalyzerObjectType.TABLE;

    private static final Integer DEFAULT_FIELD_ID = 1;
    private static final Integer UPDATED_FIELD_ID = 2;

    private static final String DEFAULT_FIELD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ENTITY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_START = 1;
    private static final Integer UPDATED_START = 2;

    private static final Integer DEFAULT_END = 1;
    private static final Integer UPDATED_END = 2;

    private static final Float DEFAULT_SCORE = 1F;
    private static final Float UPDATED_SCORE = 2F;

    private static final String DEFAULT_RECOGNIZER = "AAAAAAAAAA";
    private static final String UPDATED_RECOGNIZER = "BBBBBBBBBB";

    private static final String DEFAULT_PATTERN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PATTERN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATTERN_EXPR = "AAAAAAAAAA";
    private static final String UPDATED_PATTERN_EXPR = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINAL_SCORE = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_SCORE = "BBBBBBBBBB";

    private static final String DEFAULT_TEXTUAL_EXPLANATION = "AAAAAAAAAA";
    private static final String UPDATED_TEXTUAL_EXPLANATION = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPORTIVE_CONTEXT_WORD = "AAAAAAAAAA";
    private static final String UPDATED_SUPPORTIVE_CONTEXT_WORD = "BBBBBBBBBB";

    private static final String DEFAULT_VALIDATION_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATION_RESULT = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/analyzer-results";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnalyzerResultRepository analyzerResultRepository;

    @Autowired
    private AnalyzerResultMapper analyzerResultMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnalyzerResultMockMvc;

    private AnalyzerResult analyzerResult;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnalyzerResult createEntity(EntityManager em) {
        AnalyzerResult analyzerResult = new AnalyzerResult()
            .name(DEFAULT_NAME)
            .detail(DEFAULT_DETAIL)
            .objectId(DEFAULT_OBJECT_ID)
            .objectType(DEFAULT_OBJECT_TYPE)
            .fieldId(DEFAULT_FIELD_ID)
            .fieldType(DEFAULT_FIELD_TYPE)
            .entityType(DEFAULT_ENTITY_TYPE)
            .start(DEFAULT_START)
            .end(DEFAULT_END)
            .score(DEFAULT_SCORE)
            .recognizer(DEFAULT_RECOGNIZER)
            .patternName(DEFAULT_PATTERN_NAME)
            .patternExpr(DEFAULT_PATTERN_EXPR)
            .originalScore(DEFAULT_ORIGINAL_SCORE)
            .textualExplanation(DEFAULT_TEXTUAL_EXPLANATION)
            .supportiveContextWord(DEFAULT_SUPPORTIVE_CONTEXT_WORD)
            .validationResult(DEFAULT_VALIDATION_RESULT)
            .creationDate(DEFAULT_CREATION_DATE)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return analyzerResult;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnalyzerResult createUpdatedEntity(EntityManager em) {
        AnalyzerResult analyzerResult = new AnalyzerResult()
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .objectId(UPDATED_OBJECT_ID)
            .objectType(UPDATED_OBJECT_TYPE)
            .fieldId(UPDATED_FIELD_ID)
            .fieldType(UPDATED_FIELD_TYPE)
            .entityType(UPDATED_ENTITY_TYPE)
            .start(UPDATED_START)
            .end(UPDATED_END)
            .score(UPDATED_SCORE)
            .recognizer(UPDATED_RECOGNIZER)
            .patternName(UPDATED_PATTERN_NAME)
            .patternExpr(UPDATED_PATTERN_EXPR)
            .originalScore(UPDATED_ORIGINAL_SCORE)
            .textualExplanation(UPDATED_TEXTUAL_EXPLANATION)
            .supportiveContextWord(UPDATED_SUPPORTIVE_CONTEXT_WORD)
            .validationResult(UPDATED_VALIDATION_RESULT)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return analyzerResult;
    }

    @BeforeEach
    public void initTest() {
        analyzerResult = createEntity(em);
    }

    @Test
    @Transactional
    void createAnalyzerResult() throws Exception {
        int databaseSizeBeforeCreate = analyzerResultRepository.findAll().size();
        // Create the AnalyzerResult
        AnalyzerResultDTO analyzerResultDTO = analyzerResultMapper.toDto(analyzerResult);
        restAnalyzerResultMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analyzerResultDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AnalyzerResult in the database
        List<AnalyzerResult> analyzerResultList = analyzerResultRepository.findAll();
        assertThat(analyzerResultList).hasSize(databaseSizeBeforeCreate + 1);
        AnalyzerResult testAnalyzerResult = analyzerResultList.get(analyzerResultList.size() - 1);
        assertThat(testAnalyzerResult.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAnalyzerResult.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testAnalyzerResult.getObjectId()).isEqualTo(DEFAULT_OBJECT_ID);
        assertThat(testAnalyzerResult.getObjectType()).isEqualTo(DEFAULT_OBJECT_TYPE);
        assertThat(testAnalyzerResult.getFieldId()).isEqualTo(DEFAULT_FIELD_ID);
        assertThat(testAnalyzerResult.getFieldType()).isEqualTo(DEFAULT_FIELD_TYPE);
        assertThat(testAnalyzerResult.getEntityType()).isEqualTo(DEFAULT_ENTITY_TYPE);
        assertThat(testAnalyzerResult.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testAnalyzerResult.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testAnalyzerResult.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testAnalyzerResult.getRecognizer()).isEqualTo(DEFAULT_RECOGNIZER);
        assertThat(testAnalyzerResult.getPatternName()).isEqualTo(DEFAULT_PATTERN_NAME);
        assertThat(testAnalyzerResult.getPatternExpr()).isEqualTo(DEFAULT_PATTERN_EXPR);
        assertThat(testAnalyzerResult.getOriginalScore()).isEqualTo(DEFAULT_ORIGINAL_SCORE);
        assertThat(testAnalyzerResult.getTextualExplanation()).isEqualTo(DEFAULT_TEXTUAL_EXPLANATION);
        assertThat(testAnalyzerResult.getSupportiveContextWord()).isEqualTo(DEFAULT_SUPPORTIVE_CONTEXT_WORD);
        assertThat(testAnalyzerResult.getValidationResult()).isEqualTo(DEFAULT_VALIDATION_RESULT);
        assertThat(testAnalyzerResult.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAnalyzerResult.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void createAnalyzerResultWithExistingId() throws Exception {
        // Create the AnalyzerResult with an existing ID
        analyzerResult.setId(1L);
        AnalyzerResultDTO analyzerResultDTO = analyzerResultMapper.toDto(analyzerResult);

        int databaseSizeBeforeCreate = analyzerResultRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnalyzerResultMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analyzerResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerResult in the database
        List<AnalyzerResult> analyzerResultList = analyzerResultRepository.findAll();
        assertThat(analyzerResultList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = analyzerResultRepository.findAll().size();
        // set the field null
        analyzerResult.setName(null);

        // Create the AnalyzerResult, which fails.
        AnalyzerResultDTO analyzerResultDTO = analyzerResultMapper.toDto(analyzerResult);

        restAnalyzerResultMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analyzerResultDTO))
            )
            .andExpect(status().isBadRequest());

        List<AnalyzerResult> analyzerResultList = analyzerResultRepository.findAll();
        assertThat(analyzerResultList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAnalyzerResults() throws Exception {
        // Initialize the database
        analyzerResultRepository.saveAndFlush(analyzerResult);

        // Get all the analyzerResultList
        restAnalyzerResultMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(analyzerResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].objectId").value(hasItem(DEFAULT_OBJECT_ID)))
            .andExpect(jsonPath("$.[*].objectType").value(hasItem(DEFAULT_OBJECT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].fieldId").value(hasItem(DEFAULT_FIELD_ID)))
            .andExpect(jsonPath("$.[*].fieldType").value(hasItem(DEFAULT_FIELD_TYPE)))
            .andExpect(jsonPath("$.[*].entityType").value(hasItem(DEFAULT_ENTITY_TYPE)))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START)))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END)))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].recognizer").value(hasItem(DEFAULT_RECOGNIZER)))
            .andExpect(jsonPath("$.[*].patternName").value(hasItem(DEFAULT_PATTERN_NAME)))
            .andExpect(jsonPath("$.[*].patternExpr").value(hasItem(DEFAULT_PATTERN_EXPR)))
            .andExpect(jsonPath("$.[*].originalScore").value(hasItem(DEFAULT_ORIGINAL_SCORE)))
            .andExpect(jsonPath("$.[*].textualExplanation").value(hasItem(DEFAULT_TEXTUAL_EXPLANATION)))
            .andExpect(jsonPath("$.[*].supportiveContextWord").value(hasItem(DEFAULT_SUPPORTIVE_CONTEXT_WORD)))
            .andExpect(jsonPath("$.[*].validationResult").value(hasItem(DEFAULT_VALIDATION_RESULT)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getAnalyzerResult() throws Exception {
        // Initialize the database
        analyzerResultRepository.saveAndFlush(analyzerResult);

        // Get the analyzerResult
        restAnalyzerResultMockMvc
            .perform(get(ENTITY_API_URL_ID, analyzerResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(analyzerResult.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL))
            .andExpect(jsonPath("$.objectId").value(DEFAULT_OBJECT_ID))
            .andExpect(jsonPath("$.objectType").value(DEFAULT_OBJECT_TYPE.toString()))
            .andExpect(jsonPath("$.fieldId").value(DEFAULT_FIELD_ID))
            .andExpect(jsonPath("$.fieldType").value(DEFAULT_FIELD_TYPE))
            .andExpect(jsonPath("$.entityType").value(DEFAULT_ENTITY_TYPE))
            .andExpect(jsonPath("$.start").value(DEFAULT_START))
            .andExpect(jsonPath("$.end").value(DEFAULT_END))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.doubleValue()))
            .andExpect(jsonPath("$.recognizer").value(DEFAULT_RECOGNIZER))
            .andExpect(jsonPath("$.patternName").value(DEFAULT_PATTERN_NAME))
            .andExpect(jsonPath("$.patternExpr").value(DEFAULT_PATTERN_EXPR))
            .andExpect(jsonPath("$.originalScore").value(DEFAULT_ORIGINAL_SCORE))
            .andExpect(jsonPath("$.textualExplanation").value(DEFAULT_TEXTUAL_EXPLANATION))
            .andExpect(jsonPath("$.supportiveContextWord").value(DEFAULT_SUPPORTIVE_CONTEXT_WORD))
            .andExpect(jsonPath("$.validationResult").value(DEFAULT_VALIDATION_RESULT))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAnalyzerResult() throws Exception {
        // Get the analyzerResult
        restAnalyzerResultMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAnalyzerResult() throws Exception {
        // Initialize the database
        analyzerResultRepository.saveAndFlush(analyzerResult);

        int databaseSizeBeforeUpdate = analyzerResultRepository.findAll().size();

        // Update the analyzerResult
        AnalyzerResult updatedAnalyzerResult = analyzerResultRepository.findById(analyzerResult.getId()).get();
        // Disconnect from session so that the updates on updatedAnalyzerResult are not directly saved in db
        em.detach(updatedAnalyzerResult);
        updatedAnalyzerResult
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .objectId(UPDATED_OBJECT_ID)
            .objectType(UPDATED_OBJECT_TYPE)
            .fieldId(UPDATED_FIELD_ID)
            .fieldType(UPDATED_FIELD_TYPE)
            .entityType(UPDATED_ENTITY_TYPE)
            .start(UPDATED_START)
            .end(UPDATED_END)
            .score(UPDATED_SCORE)
            .recognizer(UPDATED_RECOGNIZER)
            .patternName(UPDATED_PATTERN_NAME)
            .patternExpr(UPDATED_PATTERN_EXPR)
            .originalScore(UPDATED_ORIGINAL_SCORE)
            .textualExplanation(UPDATED_TEXTUAL_EXPLANATION)
            .supportiveContextWord(UPDATED_SUPPORTIVE_CONTEXT_WORD)
            .validationResult(UPDATED_VALIDATION_RESULT)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        AnalyzerResultDTO analyzerResultDTO = analyzerResultMapper.toDto(updatedAnalyzerResult);

        restAnalyzerResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, analyzerResultDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerResultDTO))
            )
            .andExpect(status().isOk());

        // Validate the AnalyzerResult in the database
        List<AnalyzerResult> analyzerResultList = analyzerResultRepository.findAll();
        assertThat(analyzerResultList).hasSize(databaseSizeBeforeUpdate);
        AnalyzerResult testAnalyzerResult = analyzerResultList.get(analyzerResultList.size() - 1);
        assertThat(testAnalyzerResult.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAnalyzerResult.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testAnalyzerResult.getObjectId()).isEqualTo(UPDATED_OBJECT_ID);
        assertThat(testAnalyzerResult.getObjectType()).isEqualTo(UPDATED_OBJECT_TYPE);
        assertThat(testAnalyzerResult.getFieldId()).isEqualTo(UPDATED_FIELD_ID);
        assertThat(testAnalyzerResult.getFieldType()).isEqualTo(UPDATED_FIELD_TYPE);
        assertThat(testAnalyzerResult.getEntityType()).isEqualTo(UPDATED_ENTITY_TYPE);
        assertThat(testAnalyzerResult.getStart()).isEqualTo(UPDATED_START);
        assertThat(testAnalyzerResult.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testAnalyzerResult.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testAnalyzerResult.getRecognizer()).isEqualTo(UPDATED_RECOGNIZER);
        assertThat(testAnalyzerResult.getPatternName()).isEqualTo(UPDATED_PATTERN_NAME);
        assertThat(testAnalyzerResult.getPatternExpr()).isEqualTo(UPDATED_PATTERN_EXPR);
        assertThat(testAnalyzerResult.getOriginalScore()).isEqualTo(UPDATED_ORIGINAL_SCORE);
        assertThat(testAnalyzerResult.getTextualExplanation()).isEqualTo(UPDATED_TEXTUAL_EXPLANATION);
        assertThat(testAnalyzerResult.getSupportiveContextWord()).isEqualTo(UPDATED_SUPPORTIVE_CONTEXT_WORD);
        assertThat(testAnalyzerResult.getValidationResult()).isEqualTo(UPDATED_VALIDATION_RESULT);
        assertThat(testAnalyzerResult.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAnalyzerResult.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingAnalyzerResult() throws Exception {
        int databaseSizeBeforeUpdate = analyzerResultRepository.findAll().size();
        analyzerResult.setId(count.incrementAndGet());

        // Create the AnalyzerResult
        AnalyzerResultDTO analyzerResultDTO = analyzerResultMapper.toDto(analyzerResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalyzerResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, analyzerResultDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerResult in the database
        List<AnalyzerResult> analyzerResultList = analyzerResultRepository.findAll();
        assertThat(analyzerResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnalyzerResult() throws Exception {
        int databaseSizeBeforeUpdate = analyzerResultRepository.findAll().size();
        analyzerResult.setId(count.incrementAndGet());

        // Create the AnalyzerResult
        AnalyzerResultDTO analyzerResultDTO = analyzerResultMapper.toDto(analyzerResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyzerResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerResult in the database
        List<AnalyzerResult> analyzerResultList = analyzerResultRepository.findAll();
        assertThat(analyzerResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnalyzerResult() throws Exception {
        int databaseSizeBeforeUpdate = analyzerResultRepository.findAll().size();
        analyzerResult.setId(count.incrementAndGet());

        // Create the AnalyzerResult
        AnalyzerResultDTO analyzerResultDTO = analyzerResultMapper.toDto(analyzerResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyzerResultMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analyzerResultDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnalyzerResult in the database
        List<AnalyzerResult> analyzerResultList = analyzerResultRepository.findAll();
        assertThat(analyzerResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnalyzerResultWithPatch() throws Exception {
        // Initialize the database
        analyzerResultRepository.saveAndFlush(analyzerResult);

        int databaseSizeBeforeUpdate = analyzerResultRepository.findAll().size();

        // Update the analyzerResult using partial update
        AnalyzerResult partialUpdatedAnalyzerResult = new AnalyzerResult();
        partialUpdatedAnalyzerResult.setId(analyzerResult.getId());

        partialUpdatedAnalyzerResult
            .detail(UPDATED_DETAIL)
            .fieldId(UPDATED_FIELD_ID)
            .fieldType(UPDATED_FIELD_TYPE)
            .start(UPDATED_START)
            .end(UPDATED_END)
            .recognizer(UPDATED_RECOGNIZER)
            .patternName(UPDATED_PATTERN_NAME)
            .patternExpr(UPDATED_PATTERN_EXPR)
            .originalScore(UPDATED_ORIGINAL_SCORE)
            .validationResult(UPDATED_VALIDATION_RESULT)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restAnalyzerResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnalyzerResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnalyzerResult))
            )
            .andExpect(status().isOk());

        // Validate the AnalyzerResult in the database
        List<AnalyzerResult> analyzerResultList = analyzerResultRepository.findAll();
        assertThat(analyzerResultList).hasSize(databaseSizeBeforeUpdate);
        AnalyzerResult testAnalyzerResult = analyzerResultList.get(analyzerResultList.size() - 1);
        assertThat(testAnalyzerResult.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAnalyzerResult.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testAnalyzerResult.getObjectId()).isEqualTo(DEFAULT_OBJECT_ID);
        assertThat(testAnalyzerResult.getObjectType()).isEqualTo(DEFAULT_OBJECT_TYPE);
        assertThat(testAnalyzerResult.getFieldId()).isEqualTo(UPDATED_FIELD_ID);
        assertThat(testAnalyzerResult.getFieldType()).isEqualTo(UPDATED_FIELD_TYPE);
        assertThat(testAnalyzerResult.getEntityType()).isEqualTo(DEFAULT_ENTITY_TYPE);
        assertThat(testAnalyzerResult.getStart()).isEqualTo(UPDATED_START);
        assertThat(testAnalyzerResult.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testAnalyzerResult.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testAnalyzerResult.getRecognizer()).isEqualTo(UPDATED_RECOGNIZER);
        assertThat(testAnalyzerResult.getPatternName()).isEqualTo(UPDATED_PATTERN_NAME);
        assertThat(testAnalyzerResult.getPatternExpr()).isEqualTo(UPDATED_PATTERN_EXPR);
        assertThat(testAnalyzerResult.getOriginalScore()).isEqualTo(UPDATED_ORIGINAL_SCORE);
        assertThat(testAnalyzerResult.getTextualExplanation()).isEqualTo(DEFAULT_TEXTUAL_EXPLANATION);
        assertThat(testAnalyzerResult.getSupportiveContextWord()).isEqualTo(DEFAULT_SUPPORTIVE_CONTEXT_WORD);
        assertThat(testAnalyzerResult.getValidationResult()).isEqualTo(UPDATED_VALIDATION_RESULT);
        assertThat(testAnalyzerResult.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAnalyzerResult.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateAnalyzerResultWithPatch() throws Exception {
        // Initialize the database
        analyzerResultRepository.saveAndFlush(analyzerResult);

        int databaseSizeBeforeUpdate = analyzerResultRepository.findAll().size();

        // Update the analyzerResult using partial update
        AnalyzerResult partialUpdatedAnalyzerResult = new AnalyzerResult();
        partialUpdatedAnalyzerResult.setId(analyzerResult.getId());

        partialUpdatedAnalyzerResult
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .objectId(UPDATED_OBJECT_ID)
            .objectType(UPDATED_OBJECT_TYPE)
            .fieldId(UPDATED_FIELD_ID)
            .fieldType(UPDATED_FIELD_TYPE)
            .entityType(UPDATED_ENTITY_TYPE)
            .start(UPDATED_START)
            .end(UPDATED_END)
            .score(UPDATED_SCORE)
            .recognizer(UPDATED_RECOGNIZER)
            .patternName(UPDATED_PATTERN_NAME)
            .patternExpr(UPDATED_PATTERN_EXPR)
            .originalScore(UPDATED_ORIGINAL_SCORE)
            .textualExplanation(UPDATED_TEXTUAL_EXPLANATION)
            .supportiveContextWord(UPDATED_SUPPORTIVE_CONTEXT_WORD)
            .validationResult(UPDATED_VALIDATION_RESULT)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restAnalyzerResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnalyzerResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnalyzerResult))
            )
            .andExpect(status().isOk());

        // Validate the AnalyzerResult in the database
        List<AnalyzerResult> analyzerResultList = analyzerResultRepository.findAll();
        assertThat(analyzerResultList).hasSize(databaseSizeBeforeUpdate);
        AnalyzerResult testAnalyzerResult = analyzerResultList.get(analyzerResultList.size() - 1);
        assertThat(testAnalyzerResult.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAnalyzerResult.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testAnalyzerResult.getObjectId()).isEqualTo(UPDATED_OBJECT_ID);
        assertThat(testAnalyzerResult.getObjectType()).isEqualTo(UPDATED_OBJECT_TYPE);
        assertThat(testAnalyzerResult.getFieldId()).isEqualTo(UPDATED_FIELD_ID);
        assertThat(testAnalyzerResult.getFieldType()).isEqualTo(UPDATED_FIELD_TYPE);
        assertThat(testAnalyzerResult.getEntityType()).isEqualTo(UPDATED_ENTITY_TYPE);
        assertThat(testAnalyzerResult.getStart()).isEqualTo(UPDATED_START);
        assertThat(testAnalyzerResult.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testAnalyzerResult.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testAnalyzerResult.getRecognizer()).isEqualTo(UPDATED_RECOGNIZER);
        assertThat(testAnalyzerResult.getPatternName()).isEqualTo(UPDATED_PATTERN_NAME);
        assertThat(testAnalyzerResult.getPatternExpr()).isEqualTo(UPDATED_PATTERN_EXPR);
        assertThat(testAnalyzerResult.getOriginalScore()).isEqualTo(UPDATED_ORIGINAL_SCORE);
        assertThat(testAnalyzerResult.getTextualExplanation()).isEqualTo(UPDATED_TEXTUAL_EXPLANATION);
        assertThat(testAnalyzerResult.getSupportiveContextWord()).isEqualTo(UPDATED_SUPPORTIVE_CONTEXT_WORD);
        assertThat(testAnalyzerResult.getValidationResult()).isEqualTo(UPDATED_VALIDATION_RESULT);
        assertThat(testAnalyzerResult.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAnalyzerResult.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingAnalyzerResult() throws Exception {
        int databaseSizeBeforeUpdate = analyzerResultRepository.findAll().size();
        analyzerResult.setId(count.incrementAndGet());

        // Create the AnalyzerResult
        AnalyzerResultDTO analyzerResultDTO = analyzerResultMapper.toDto(analyzerResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalyzerResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, analyzerResultDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analyzerResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerResult in the database
        List<AnalyzerResult> analyzerResultList = analyzerResultRepository.findAll();
        assertThat(analyzerResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnalyzerResult() throws Exception {
        int databaseSizeBeforeUpdate = analyzerResultRepository.findAll().size();
        analyzerResult.setId(count.incrementAndGet());

        // Create the AnalyzerResult
        AnalyzerResultDTO analyzerResultDTO = analyzerResultMapper.toDto(analyzerResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyzerResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analyzerResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerResult in the database
        List<AnalyzerResult> analyzerResultList = analyzerResultRepository.findAll();
        assertThat(analyzerResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnalyzerResult() throws Exception {
        int databaseSizeBeforeUpdate = analyzerResultRepository.findAll().size();
        analyzerResult.setId(count.incrementAndGet());

        // Create the AnalyzerResult
        AnalyzerResultDTO analyzerResultDTO = analyzerResultMapper.toDto(analyzerResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyzerResultMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analyzerResultDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnalyzerResult in the database
        List<AnalyzerResult> analyzerResultList = analyzerResultRepository.findAll();
        assertThat(analyzerResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnalyzerResult() throws Exception {
        // Initialize the database
        analyzerResultRepository.saveAndFlush(analyzerResult);

        int databaseSizeBeforeDelete = analyzerResultRepository.findAll().size();

        // Delete the analyzerResult
        restAnalyzerResultMockMvc
            .perform(delete(ENTITY_API_URL_ID, analyzerResult.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnalyzerResult> analyzerResultList = analyzerResultRepository.findAll();
        assertThat(analyzerResultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
