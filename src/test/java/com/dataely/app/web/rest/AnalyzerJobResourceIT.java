package com.dataely.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dataely.app.IntegrationTest;
import com.dataely.app.domain.AnalyzerJob;
import com.dataely.app.domain.User;
import com.dataely.app.domain.enumeration.EJobStatus;
import com.dataely.app.repository.AnalyzerJobRepository;
import com.dataely.app.service.dto.AnalyzerJobDTO;
import com.dataely.app.service.mapper.AnalyzerJobMapper;
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
 * Integration tests for the {@link AnalyzerJobResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnalyzerJobResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final EJobStatus DEFAULT_STATUS = EJobStatus.SUCCEEDED;
    private static final EJobStatus UPDATED_STATUS = EJobStatus.FAILED;

    private static final String DEFAULT_PREVIOUS_RUN_TIME = "AAAAAAAAAA";
    private static final String UPDATED_PREVIOUS_RUN_TIME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/analyzer-jobs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnalyzerJobRepository analyzerJobRepository;

    @Autowired
    private AnalyzerJobMapper analyzerJobMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnalyzerJobMockMvc;

    private AnalyzerJob analyzerJob;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnalyzerJob createEntity(EntityManager em) {
        AnalyzerJob analyzerJob = new AnalyzerJob()
            .name(DEFAULT_NAME)
            .detail(DEFAULT_DETAIL)
            .endTime(DEFAULT_END_TIME)
            .startTime(DEFAULT_START_TIME)
            .status(DEFAULT_STATUS)
            .previousRunTime(DEFAULT_PREVIOUS_RUN_TIME)
            .creationDate(DEFAULT_CREATION_DATE)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        analyzerJob.setUser(user);
        return analyzerJob;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnalyzerJob createUpdatedEntity(EntityManager em) {
        AnalyzerJob analyzerJob = new AnalyzerJob()
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .endTime(UPDATED_END_TIME)
            .startTime(UPDATED_START_TIME)
            .status(UPDATED_STATUS)
            .previousRunTime(UPDATED_PREVIOUS_RUN_TIME)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        // Add required entity
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        analyzerJob.setUser(user);
        return analyzerJob;
    }

    @BeforeEach
    public void initTest() {
        analyzerJob = createEntity(em);
    }

    @Test
    @Transactional
    void createAnalyzerJob() throws Exception {
        int databaseSizeBeforeCreate = analyzerJobRepository.findAll().size();
        // Create the AnalyzerJob
        AnalyzerJobDTO analyzerJobDTO = analyzerJobMapper.toDto(analyzerJob);
        restAnalyzerJobMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analyzerJobDTO))
            )
            .andExpect(status().isCreated());

        // Validate the AnalyzerJob in the database
        List<AnalyzerJob> analyzerJobList = analyzerJobRepository.findAll();
        assertThat(analyzerJobList).hasSize(databaseSizeBeforeCreate + 1);
        AnalyzerJob testAnalyzerJob = analyzerJobList.get(analyzerJobList.size() - 1);
        assertThat(testAnalyzerJob.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAnalyzerJob.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testAnalyzerJob.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testAnalyzerJob.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testAnalyzerJob.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAnalyzerJob.getPreviousRunTime()).isEqualTo(DEFAULT_PREVIOUS_RUN_TIME);
        assertThat(testAnalyzerJob.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAnalyzerJob.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void createAnalyzerJobWithExistingId() throws Exception {
        // Create the AnalyzerJob with an existing ID
        analyzerJob.setId(1L);
        AnalyzerJobDTO analyzerJobDTO = analyzerJobMapper.toDto(analyzerJob);

        int databaseSizeBeforeCreate = analyzerJobRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnalyzerJobMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analyzerJobDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerJob in the database
        List<AnalyzerJob> analyzerJobList = analyzerJobRepository.findAll();
        assertThat(analyzerJobList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = analyzerJobRepository.findAll().size();
        // set the field null
        analyzerJob.setName(null);

        // Create the AnalyzerJob, which fails.
        AnalyzerJobDTO analyzerJobDTO = analyzerJobMapper.toDto(analyzerJob);

        restAnalyzerJobMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analyzerJobDTO))
            )
            .andExpect(status().isBadRequest());

        List<AnalyzerJob> analyzerJobList = analyzerJobRepository.findAll();
        assertThat(analyzerJobList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAnalyzerJobs() throws Exception {
        // Initialize the database
        analyzerJobRepository.saveAndFlush(analyzerJob);

        // Get all the analyzerJobList
        restAnalyzerJobMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(analyzerJob.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].previousRunTime").value(hasItem(DEFAULT_PREVIOUS_RUN_TIME)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getAnalyzerJob() throws Exception {
        // Initialize the database
        analyzerJobRepository.saveAndFlush(analyzerJob);

        // Get the analyzerJob
        restAnalyzerJobMockMvc
            .perform(get(ENTITY_API_URL_ID, analyzerJob.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(analyzerJob.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.previousRunTime").value(DEFAULT_PREVIOUS_RUN_TIME))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAnalyzerJob() throws Exception {
        // Get the analyzerJob
        restAnalyzerJobMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAnalyzerJob() throws Exception {
        // Initialize the database
        analyzerJobRepository.saveAndFlush(analyzerJob);

        int databaseSizeBeforeUpdate = analyzerJobRepository.findAll().size();

        // Update the analyzerJob
        AnalyzerJob updatedAnalyzerJob = analyzerJobRepository.findById(analyzerJob.getId()).get();
        // Disconnect from session so that the updates on updatedAnalyzerJob are not directly saved in db
        em.detach(updatedAnalyzerJob);
        updatedAnalyzerJob
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .endTime(UPDATED_END_TIME)
            .startTime(UPDATED_START_TIME)
            .status(UPDATED_STATUS)
            .previousRunTime(UPDATED_PREVIOUS_RUN_TIME)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        AnalyzerJobDTO analyzerJobDTO = analyzerJobMapper.toDto(updatedAnalyzerJob);

        restAnalyzerJobMockMvc
            .perform(
                put(ENTITY_API_URL_ID, analyzerJobDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerJobDTO))
            )
            .andExpect(status().isOk());

        // Validate the AnalyzerJob in the database
        List<AnalyzerJob> analyzerJobList = analyzerJobRepository.findAll();
        assertThat(analyzerJobList).hasSize(databaseSizeBeforeUpdate);
        AnalyzerJob testAnalyzerJob = analyzerJobList.get(analyzerJobList.size() - 1);
        assertThat(testAnalyzerJob.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAnalyzerJob.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testAnalyzerJob.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testAnalyzerJob.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testAnalyzerJob.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAnalyzerJob.getPreviousRunTime()).isEqualTo(UPDATED_PREVIOUS_RUN_TIME);
        assertThat(testAnalyzerJob.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAnalyzerJob.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingAnalyzerJob() throws Exception {
        int databaseSizeBeforeUpdate = analyzerJobRepository.findAll().size();
        analyzerJob.setId(count.incrementAndGet());

        // Create the AnalyzerJob
        AnalyzerJobDTO analyzerJobDTO = analyzerJobMapper.toDto(analyzerJob);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalyzerJobMockMvc
            .perform(
                put(ENTITY_API_URL_ID, analyzerJobDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerJobDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerJob in the database
        List<AnalyzerJob> analyzerJobList = analyzerJobRepository.findAll();
        assertThat(analyzerJobList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnalyzerJob() throws Exception {
        int databaseSizeBeforeUpdate = analyzerJobRepository.findAll().size();
        analyzerJob.setId(count.incrementAndGet());

        // Create the AnalyzerJob
        AnalyzerJobDTO analyzerJobDTO = analyzerJobMapper.toDto(analyzerJob);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyzerJobMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(analyzerJobDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerJob in the database
        List<AnalyzerJob> analyzerJobList = analyzerJobRepository.findAll();
        assertThat(analyzerJobList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnalyzerJob() throws Exception {
        int databaseSizeBeforeUpdate = analyzerJobRepository.findAll().size();
        analyzerJob.setId(count.incrementAndGet());

        // Create the AnalyzerJob
        AnalyzerJobDTO analyzerJobDTO = analyzerJobMapper.toDto(analyzerJob);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyzerJobMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(analyzerJobDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnalyzerJob in the database
        List<AnalyzerJob> analyzerJobList = analyzerJobRepository.findAll();
        assertThat(analyzerJobList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnalyzerJobWithPatch() throws Exception {
        // Initialize the database
        analyzerJobRepository.saveAndFlush(analyzerJob);

        int databaseSizeBeforeUpdate = analyzerJobRepository.findAll().size();

        // Update the analyzerJob using partial update
        AnalyzerJob partialUpdatedAnalyzerJob = new AnalyzerJob();
        partialUpdatedAnalyzerJob.setId(analyzerJob.getId());

        partialUpdatedAnalyzerJob.name(UPDATED_NAME).detail(UPDATED_DETAIL).startTime(UPDATED_START_TIME);

        restAnalyzerJobMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnalyzerJob.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnalyzerJob))
            )
            .andExpect(status().isOk());

        // Validate the AnalyzerJob in the database
        List<AnalyzerJob> analyzerJobList = analyzerJobRepository.findAll();
        assertThat(analyzerJobList).hasSize(databaseSizeBeforeUpdate);
        AnalyzerJob testAnalyzerJob = analyzerJobList.get(analyzerJobList.size() - 1);
        assertThat(testAnalyzerJob.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAnalyzerJob.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testAnalyzerJob.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testAnalyzerJob.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testAnalyzerJob.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAnalyzerJob.getPreviousRunTime()).isEqualTo(DEFAULT_PREVIOUS_RUN_TIME);
        assertThat(testAnalyzerJob.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testAnalyzerJob.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateAnalyzerJobWithPatch() throws Exception {
        // Initialize the database
        analyzerJobRepository.saveAndFlush(analyzerJob);

        int databaseSizeBeforeUpdate = analyzerJobRepository.findAll().size();

        // Update the analyzerJob using partial update
        AnalyzerJob partialUpdatedAnalyzerJob = new AnalyzerJob();
        partialUpdatedAnalyzerJob.setId(analyzerJob.getId());

        partialUpdatedAnalyzerJob
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .endTime(UPDATED_END_TIME)
            .startTime(UPDATED_START_TIME)
            .status(UPDATED_STATUS)
            .previousRunTime(UPDATED_PREVIOUS_RUN_TIME)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restAnalyzerJobMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnalyzerJob.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnalyzerJob))
            )
            .andExpect(status().isOk());

        // Validate the AnalyzerJob in the database
        List<AnalyzerJob> analyzerJobList = analyzerJobRepository.findAll();
        assertThat(analyzerJobList).hasSize(databaseSizeBeforeUpdate);
        AnalyzerJob testAnalyzerJob = analyzerJobList.get(analyzerJobList.size() - 1);
        assertThat(testAnalyzerJob.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAnalyzerJob.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testAnalyzerJob.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testAnalyzerJob.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testAnalyzerJob.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAnalyzerJob.getPreviousRunTime()).isEqualTo(UPDATED_PREVIOUS_RUN_TIME);
        assertThat(testAnalyzerJob.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testAnalyzerJob.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingAnalyzerJob() throws Exception {
        int databaseSizeBeforeUpdate = analyzerJobRepository.findAll().size();
        analyzerJob.setId(count.incrementAndGet());

        // Create the AnalyzerJob
        AnalyzerJobDTO analyzerJobDTO = analyzerJobMapper.toDto(analyzerJob);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnalyzerJobMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, analyzerJobDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analyzerJobDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerJob in the database
        List<AnalyzerJob> analyzerJobList = analyzerJobRepository.findAll();
        assertThat(analyzerJobList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnalyzerJob() throws Exception {
        int databaseSizeBeforeUpdate = analyzerJobRepository.findAll().size();
        analyzerJob.setId(count.incrementAndGet());

        // Create the AnalyzerJob
        AnalyzerJobDTO analyzerJobDTO = analyzerJobMapper.toDto(analyzerJob);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyzerJobMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(analyzerJobDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AnalyzerJob in the database
        List<AnalyzerJob> analyzerJobList = analyzerJobRepository.findAll();
        assertThat(analyzerJobList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnalyzerJob() throws Exception {
        int databaseSizeBeforeUpdate = analyzerJobRepository.findAll().size();
        analyzerJob.setId(count.incrementAndGet());

        // Create the AnalyzerJob
        AnalyzerJobDTO analyzerJobDTO = analyzerJobMapper.toDto(analyzerJob);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnalyzerJobMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(analyzerJobDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AnalyzerJob in the database
        List<AnalyzerJob> analyzerJobList = analyzerJobRepository.findAll();
        assertThat(analyzerJobList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnalyzerJob() throws Exception {
        // Initialize the database
        analyzerJobRepository.saveAndFlush(analyzerJob);

        int databaseSizeBeforeDelete = analyzerJobRepository.findAll().size();

        // Delete the analyzerJob
        restAnalyzerJobMockMvc
            .perform(delete(ENTITY_API_URL_ID, analyzerJob.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnalyzerJob> analyzerJobList = analyzerJobRepository.findAll();
        assertThat(analyzerJobList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
