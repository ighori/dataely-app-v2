package com.dataely.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dataely.app.IntegrationTest;
import com.dataely.app.domain.TableRelationship;
import com.dataely.app.repository.TableRelationshipRepository;
import com.dataely.app.service.dto.TableRelationshipDTO;
import com.dataely.app.service.mapper.TableRelationshipMapper;
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
 * Integration tests for the {@link TableRelationshipResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TableRelationshipResourceIT {

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET = "AAAAAAAAAA";
    private static final String UPDATED_TARGET = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE_KEY = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET_KEY = "AAAAAAAAAA";
    private static final String UPDATED_TARGET_KEY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/table-relationships";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TableRelationshipRepository tableRelationshipRepository;

    @Autowired
    private TableRelationshipMapper tableRelationshipMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTableRelationshipMockMvc;

    private TableRelationship tableRelationship;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TableRelationship createEntity(EntityManager em) {
        TableRelationship tableRelationship = new TableRelationship()
            .source(DEFAULT_SOURCE)
            .target(DEFAULT_TARGET)
            .sourceKey(DEFAULT_SOURCE_KEY)
            .targetKey(DEFAULT_TARGET_KEY)
            .creationDate(DEFAULT_CREATION_DATE)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return tableRelationship;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TableRelationship createUpdatedEntity(EntityManager em) {
        TableRelationship tableRelationship = new TableRelationship()
            .source(UPDATED_SOURCE)
            .target(UPDATED_TARGET)
            .sourceKey(UPDATED_SOURCE_KEY)
            .targetKey(UPDATED_TARGET_KEY)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return tableRelationship;
    }

    @BeforeEach
    public void initTest() {
        tableRelationship = createEntity(em);
    }

    @Test
    @Transactional
    void createTableRelationship() throws Exception {
        int databaseSizeBeforeCreate = tableRelationshipRepository.findAll().size();
        // Create the TableRelationship
        TableRelationshipDTO tableRelationshipDTO = tableRelationshipMapper.toDto(tableRelationship);
        restTableRelationshipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tableRelationshipDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TableRelationship in the database
        List<TableRelationship> tableRelationshipList = tableRelationshipRepository.findAll();
        assertThat(tableRelationshipList).hasSize(databaseSizeBeforeCreate + 1);
        TableRelationship testTableRelationship = tableRelationshipList.get(tableRelationshipList.size() - 1);
        assertThat(testTableRelationship.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testTableRelationship.getTarget()).isEqualTo(DEFAULT_TARGET);
        assertThat(testTableRelationship.getSourceKey()).isEqualTo(DEFAULT_SOURCE_KEY);
        assertThat(testTableRelationship.getTargetKey()).isEqualTo(DEFAULT_TARGET_KEY);
        assertThat(testTableRelationship.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testTableRelationship.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void createTableRelationshipWithExistingId() throws Exception {
        // Create the TableRelationship with an existing ID
        tableRelationship.setId(1L);
        TableRelationshipDTO tableRelationshipDTO = tableRelationshipMapper.toDto(tableRelationship);

        int databaseSizeBeforeCreate = tableRelationshipRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTableRelationshipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tableRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TableRelationship in the database
        List<TableRelationship> tableRelationshipList = tableRelationshipRepository.findAll();
        assertThat(tableRelationshipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTableRelationships() throws Exception {
        // Initialize the database
        tableRelationshipRepository.saveAndFlush(tableRelationship);

        // Get all the tableRelationshipList
        restTableRelationshipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tableRelationship.getId().intValue())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].target").value(hasItem(DEFAULT_TARGET)))
            .andExpect(jsonPath("$.[*].sourceKey").value(hasItem(DEFAULT_SOURCE_KEY)))
            .andExpect(jsonPath("$.[*].targetKey").value(hasItem(DEFAULT_TARGET_KEY)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getTableRelationship() throws Exception {
        // Initialize the database
        tableRelationshipRepository.saveAndFlush(tableRelationship);

        // Get the tableRelationship
        restTableRelationshipMockMvc
            .perform(get(ENTITY_API_URL_ID, tableRelationship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tableRelationship.getId().intValue()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.target").value(DEFAULT_TARGET))
            .andExpect(jsonPath("$.sourceKey").value(DEFAULT_SOURCE_KEY))
            .andExpect(jsonPath("$.targetKey").value(DEFAULT_TARGET_KEY))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTableRelationship() throws Exception {
        // Get the tableRelationship
        restTableRelationshipMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTableRelationship() throws Exception {
        // Initialize the database
        tableRelationshipRepository.saveAndFlush(tableRelationship);

        int databaseSizeBeforeUpdate = tableRelationshipRepository.findAll().size();

        // Update the tableRelationship
        TableRelationship updatedTableRelationship = tableRelationshipRepository.findById(tableRelationship.getId()).get();
        // Disconnect from session so that the updates on updatedTableRelationship are not directly saved in db
        em.detach(updatedTableRelationship);
        updatedTableRelationship
            .source(UPDATED_SOURCE)
            .target(UPDATED_TARGET)
            .sourceKey(UPDATED_SOURCE_KEY)
            .targetKey(UPDATED_TARGET_KEY)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        TableRelationshipDTO tableRelationshipDTO = tableRelationshipMapper.toDto(updatedTableRelationship);

        restTableRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tableRelationshipDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tableRelationshipDTO))
            )
            .andExpect(status().isOk());

        // Validate the TableRelationship in the database
        List<TableRelationship> tableRelationshipList = tableRelationshipRepository.findAll();
        assertThat(tableRelationshipList).hasSize(databaseSizeBeforeUpdate);
        TableRelationship testTableRelationship = tableRelationshipList.get(tableRelationshipList.size() - 1);
        assertThat(testTableRelationship.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testTableRelationship.getTarget()).isEqualTo(UPDATED_TARGET);
        assertThat(testTableRelationship.getSourceKey()).isEqualTo(UPDATED_SOURCE_KEY);
        assertThat(testTableRelationship.getTargetKey()).isEqualTo(UPDATED_TARGET_KEY);
        assertThat(testTableRelationship.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testTableRelationship.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingTableRelationship() throws Exception {
        int databaseSizeBeforeUpdate = tableRelationshipRepository.findAll().size();
        tableRelationship.setId(count.incrementAndGet());

        // Create the TableRelationship
        TableRelationshipDTO tableRelationshipDTO = tableRelationshipMapper.toDto(tableRelationship);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTableRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tableRelationshipDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tableRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TableRelationship in the database
        List<TableRelationship> tableRelationshipList = tableRelationshipRepository.findAll();
        assertThat(tableRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTableRelationship() throws Exception {
        int databaseSizeBeforeUpdate = tableRelationshipRepository.findAll().size();
        tableRelationship.setId(count.incrementAndGet());

        // Create the TableRelationship
        TableRelationshipDTO tableRelationshipDTO = tableRelationshipMapper.toDto(tableRelationship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTableRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tableRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TableRelationship in the database
        List<TableRelationship> tableRelationshipList = tableRelationshipRepository.findAll();
        assertThat(tableRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTableRelationship() throws Exception {
        int databaseSizeBeforeUpdate = tableRelationshipRepository.findAll().size();
        tableRelationship.setId(count.incrementAndGet());

        // Create the TableRelationship
        TableRelationshipDTO tableRelationshipDTO = tableRelationshipMapper.toDto(tableRelationship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTableRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tableRelationshipDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TableRelationship in the database
        List<TableRelationship> tableRelationshipList = tableRelationshipRepository.findAll();
        assertThat(tableRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTableRelationshipWithPatch() throws Exception {
        // Initialize the database
        tableRelationshipRepository.saveAndFlush(tableRelationship);

        int databaseSizeBeforeUpdate = tableRelationshipRepository.findAll().size();

        // Update the tableRelationship using partial update
        TableRelationship partialUpdatedTableRelationship = new TableRelationship();
        partialUpdatedTableRelationship.setId(tableRelationship.getId());

        partialUpdatedTableRelationship
            .source(UPDATED_SOURCE)
            .target(UPDATED_TARGET)
            .sourceKey(UPDATED_SOURCE_KEY)
            .creationDate(UPDATED_CREATION_DATE);

        restTableRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTableRelationship.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTableRelationship))
            )
            .andExpect(status().isOk());

        // Validate the TableRelationship in the database
        List<TableRelationship> tableRelationshipList = tableRelationshipRepository.findAll();
        assertThat(tableRelationshipList).hasSize(databaseSizeBeforeUpdate);
        TableRelationship testTableRelationship = tableRelationshipList.get(tableRelationshipList.size() - 1);
        assertThat(testTableRelationship.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testTableRelationship.getTarget()).isEqualTo(UPDATED_TARGET);
        assertThat(testTableRelationship.getSourceKey()).isEqualTo(UPDATED_SOURCE_KEY);
        assertThat(testTableRelationship.getTargetKey()).isEqualTo(DEFAULT_TARGET_KEY);
        assertThat(testTableRelationship.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testTableRelationship.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateTableRelationshipWithPatch() throws Exception {
        // Initialize the database
        tableRelationshipRepository.saveAndFlush(tableRelationship);

        int databaseSizeBeforeUpdate = tableRelationshipRepository.findAll().size();

        // Update the tableRelationship using partial update
        TableRelationship partialUpdatedTableRelationship = new TableRelationship();
        partialUpdatedTableRelationship.setId(tableRelationship.getId());

        partialUpdatedTableRelationship
            .source(UPDATED_SOURCE)
            .target(UPDATED_TARGET)
            .sourceKey(UPDATED_SOURCE_KEY)
            .targetKey(UPDATED_TARGET_KEY)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restTableRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTableRelationship.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTableRelationship))
            )
            .andExpect(status().isOk());

        // Validate the TableRelationship in the database
        List<TableRelationship> tableRelationshipList = tableRelationshipRepository.findAll();
        assertThat(tableRelationshipList).hasSize(databaseSizeBeforeUpdate);
        TableRelationship testTableRelationship = tableRelationshipList.get(tableRelationshipList.size() - 1);
        assertThat(testTableRelationship.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testTableRelationship.getTarget()).isEqualTo(UPDATED_TARGET);
        assertThat(testTableRelationship.getSourceKey()).isEqualTo(UPDATED_SOURCE_KEY);
        assertThat(testTableRelationship.getTargetKey()).isEqualTo(UPDATED_TARGET_KEY);
        assertThat(testTableRelationship.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testTableRelationship.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingTableRelationship() throws Exception {
        int databaseSizeBeforeUpdate = tableRelationshipRepository.findAll().size();
        tableRelationship.setId(count.incrementAndGet());

        // Create the TableRelationship
        TableRelationshipDTO tableRelationshipDTO = tableRelationshipMapper.toDto(tableRelationship);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTableRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tableRelationshipDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tableRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TableRelationship in the database
        List<TableRelationship> tableRelationshipList = tableRelationshipRepository.findAll();
        assertThat(tableRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTableRelationship() throws Exception {
        int databaseSizeBeforeUpdate = tableRelationshipRepository.findAll().size();
        tableRelationship.setId(count.incrementAndGet());

        // Create the TableRelationship
        TableRelationshipDTO tableRelationshipDTO = tableRelationshipMapper.toDto(tableRelationship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTableRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tableRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TableRelationship in the database
        List<TableRelationship> tableRelationshipList = tableRelationshipRepository.findAll();
        assertThat(tableRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTableRelationship() throws Exception {
        int databaseSizeBeforeUpdate = tableRelationshipRepository.findAll().size();
        tableRelationship.setId(count.incrementAndGet());

        // Create the TableRelationship
        TableRelationshipDTO tableRelationshipDTO = tableRelationshipMapper.toDto(tableRelationship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTableRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tableRelationshipDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TableRelationship in the database
        List<TableRelationship> tableRelationshipList = tableRelationshipRepository.findAll();
        assertThat(tableRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTableRelationship() throws Exception {
        // Initialize the database
        tableRelationshipRepository.saveAndFlush(tableRelationship);

        int databaseSizeBeforeDelete = tableRelationshipRepository.findAll().size();

        // Delete the tableRelationship
        restTableRelationshipMockMvc
            .perform(delete(ENTITY_API_URL_ID, tableRelationship.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TableRelationship> tableRelationshipList = tableRelationshipRepository.findAll();
        assertThat(tableRelationshipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
