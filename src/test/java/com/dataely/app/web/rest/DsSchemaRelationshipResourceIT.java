package com.dataely.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dataely.app.IntegrationTest;
import com.dataely.app.domain.DsSchemaRelationship;
import com.dataely.app.repository.DsSchemaRelationshipRepository;
import com.dataely.app.service.dto.DsSchemaRelationshipDTO;
import com.dataely.app.service.mapper.DsSchemaRelationshipMapper;
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
 * Integration tests for the {@link DsSchemaRelationshipResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DsSchemaRelationshipResourceIT {

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET = "AAAAAAAAAA";
    private static final String UPDATED_TARGET = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/ds-schema-relationships";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DsSchemaRelationshipRepository dsSchemaRelationshipRepository;

    @Autowired
    private DsSchemaRelationshipMapper dsSchemaRelationshipMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDsSchemaRelationshipMockMvc;

    private DsSchemaRelationship dsSchemaRelationship;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DsSchemaRelationship createEntity(EntityManager em) {
        DsSchemaRelationship dsSchemaRelationship = new DsSchemaRelationship()
            .source(DEFAULT_SOURCE)
            .target(DEFAULT_TARGET)
            .creationDate(DEFAULT_CREATION_DATE)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return dsSchemaRelationship;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DsSchemaRelationship createUpdatedEntity(EntityManager em) {
        DsSchemaRelationship dsSchemaRelationship = new DsSchemaRelationship()
            .source(UPDATED_SOURCE)
            .target(UPDATED_TARGET)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return dsSchemaRelationship;
    }

    @BeforeEach
    public void initTest() {
        dsSchemaRelationship = createEntity(em);
    }

    @Test
    @Transactional
    void createDsSchemaRelationship() throws Exception {
        int databaseSizeBeforeCreate = dsSchemaRelationshipRepository.findAll().size();
        // Create the DsSchemaRelationship
        DsSchemaRelationshipDTO dsSchemaRelationshipDTO = dsSchemaRelationshipMapper.toDto(dsSchemaRelationship);
        restDsSchemaRelationshipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dsSchemaRelationshipDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DsSchemaRelationship in the database
        List<DsSchemaRelationship> dsSchemaRelationshipList = dsSchemaRelationshipRepository.findAll();
        assertThat(dsSchemaRelationshipList).hasSize(databaseSizeBeforeCreate + 1);
        DsSchemaRelationship testDsSchemaRelationship = dsSchemaRelationshipList.get(dsSchemaRelationshipList.size() - 1);
        assertThat(testDsSchemaRelationship.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testDsSchemaRelationship.getTarget()).isEqualTo(DEFAULT_TARGET);
        assertThat(testDsSchemaRelationship.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testDsSchemaRelationship.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void createDsSchemaRelationshipWithExistingId() throws Exception {
        // Create the DsSchemaRelationship with an existing ID
        dsSchemaRelationship.setId(1L);
        DsSchemaRelationshipDTO dsSchemaRelationshipDTO = dsSchemaRelationshipMapper.toDto(dsSchemaRelationship);

        int databaseSizeBeforeCreate = dsSchemaRelationshipRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDsSchemaRelationshipMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dsSchemaRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DsSchemaRelationship in the database
        List<DsSchemaRelationship> dsSchemaRelationshipList = dsSchemaRelationshipRepository.findAll();
        assertThat(dsSchemaRelationshipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDsSchemaRelationships() throws Exception {
        // Initialize the database
        dsSchemaRelationshipRepository.saveAndFlush(dsSchemaRelationship);

        // Get all the dsSchemaRelationshipList
        restDsSchemaRelationshipMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dsSchemaRelationship.getId().intValue())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].target").value(hasItem(DEFAULT_TARGET)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getDsSchemaRelationship() throws Exception {
        // Initialize the database
        dsSchemaRelationshipRepository.saveAndFlush(dsSchemaRelationship);

        // Get the dsSchemaRelationship
        restDsSchemaRelationshipMockMvc
            .perform(get(ENTITY_API_URL_ID, dsSchemaRelationship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dsSchemaRelationship.getId().intValue()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.target").value(DEFAULT_TARGET))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDsSchemaRelationship() throws Exception {
        // Get the dsSchemaRelationship
        restDsSchemaRelationshipMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDsSchemaRelationship() throws Exception {
        // Initialize the database
        dsSchemaRelationshipRepository.saveAndFlush(dsSchemaRelationship);

        int databaseSizeBeforeUpdate = dsSchemaRelationshipRepository.findAll().size();

        // Update the dsSchemaRelationship
        DsSchemaRelationship updatedDsSchemaRelationship = dsSchemaRelationshipRepository.findById(dsSchemaRelationship.getId()).get();
        // Disconnect from session so that the updates on updatedDsSchemaRelationship are not directly saved in db
        em.detach(updatedDsSchemaRelationship);
        updatedDsSchemaRelationship
            .source(UPDATED_SOURCE)
            .target(UPDATED_TARGET)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        DsSchemaRelationshipDTO dsSchemaRelationshipDTO = dsSchemaRelationshipMapper.toDto(updatedDsSchemaRelationship);

        restDsSchemaRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dsSchemaRelationshipDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dsSchemaRelationshipDTO))
            )
            .andExpect(status().isOk());

        // Validate the DsSchemaRelationship in the database
        List<DsSchemaRelationship> dsSchemaRelationshipList = dsSchemaRelationshipRepository.findAll();
        assertThat(dsSchemaRelationshipList).hasSize(databaseSizeBeforeUpdate);
        DsSchemaRelationship testDsSchemaRelationship = dsSchemaRelationshipList.get(dsSchemaRelationshipList.size() - 1);
        assertThat(testDsSchemaRelationship.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testDsSchemaRelationship.getTarget()).isEqualTo(UPDATED_TARGET);
        assertThat(testDsSchemaRelationship.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testDsSchemaRelationship.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingDsSchemaRelationship() throws Exception {
        int databaseSizeBeforeUpdate = dsSchemaRelationshipRepository.findAll().size();
        dsSchemaRelationship.setId(count.incrementAndGet());

        // Create the DsSchemaRelationship
        DsSchemaRelationshipDTO dsSchemaRelationshipDTO = dsSchemaRelationshipMapper.toDto(dsSchemaRelationship);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDsSchemaRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dsSchemaRelationshipDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dsSchemaRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DsSchemaRelationship in the database
        List<DsSchemaRelationship> dsSchemaRelationshipList = dsSchemaRelationshipRepository.findAll();
        assertThat(dsSchemaRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDsSchemaRelationship() throws Exception {
        int databaseSizeBeforeUpdate = dsSchemaRelationshipRepository.findAll().size();
        dsSchemaRelationship.setId(count.incrementAndGet());

        // Create the DsSchemaRelationship
        DsSchemaRelationshipDTO dsSchemaRelationshipDTO = dsSchemaRelationshipMapper.toDto(dsSchemaRelationship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDsSchemaRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dsSchemaRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DsSchemaRelationship in the database
        List<DsSchemaRelationship> dsSchemaRelationshipList = dsSchemaRelationshipRepository.findAll();
        assertThat(dsSchemaRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDsSchemaRelationship() throws Exception {
        int databaseSizeBeforeUpdate = dsSchemaRelationshipRepository.findAll().size();
        dsSchemaRelationship.setId(count.incrementAndGet());

        // Create the DsSchemaRelationship
        DsSchemaRelationshipDTO dsSchemaRelationshipDTO = dsSchemaRelationshipMapper.toDto(dsSchemaRelationship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDsSchemaRelationshipMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dsSchemaRelationshipDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DsSchemaRelationship in the database
        List<DsSchemaRelationship> dsSchemaRelationshipList = dsSchemaRelationshipRepository.findAll();
        assertThat(dsSchemaRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDsSchemaRelationshipWithPatch() throws Exception {
        // Initialize the database
        dsSchemaRelationshipRepository.saveAndFlush(dsSchemaRelationship);

        int databaseSizeBeforeUpdate = dsSchemaRelationshipRepository.findAll().size();

        // Update the dsSchemaRelationship using partial update
        DsSchemaRelationship partialUpdatedDsSchemaRelationship = new DsSchemaRelationship();
        partialUpdatedDsSchemaRelationship.setId(dsSchemaRelationship.getId());

        partialUpdatedDsSchemaRelationship.target(UPDATED_TARGET).creationDate(UPDATED_CREATION_DATE).lastUpdated(UPDATED_LAST_UPDATED);

        restDsSchemaRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDsSchemaRelationship.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDsSchemaRelationship))
            )
            .andExpect(status().isOk());

        // Validate the DsSchemaRelationship in the database
        List<DsSchemaRelationship> dsSchemaRelationshipList = dsSchemaRelationshipRepository.findAll();
        assertThat(dsSchemaRelationshipList).hasSize(databaseSizeBeforeUpdate);
        DsSchemaRelationship testDsSchemaRelationship = dsSchemaRelationshipList.get(dsSchemaRelationshipList.size() - 1);
        assertThat(testDsSchemaRelationship.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testDsSchemaRelationship.getTarget()).isEqualTo(UPDATED_TARGET);
        assertThat(testDsSchemaRelationship.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testDsSchemaRelationship.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateDsSchemaRelationshipWithPatch() throws Exception {
        // Initialize the database
        dsSchemaRelationshipRepository.saveAndFlush(dsSchemaRelationship);

        int databaseSizeBeforeUpdate = dsSchemaRelationshipRepository.findAll().size();

        // Update the dsSchemaRelationship using partial update
        DsSchemaRelationship partialUpdatedDsSchemaRelationship = new DsSchemaRelationship();
        partialUpdatedDsSchemaRelationship.setId(dsSchemaRelationship.getId());

        partialUpdatedDsSchemaRelationship
            .source(UPDATED_SOURCE)
            .target(UPDATED_TARGET)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restDsSchemaRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDsSchemaRelationship.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDsSchemaRelationship))
            )
            .andExpect(status().isOk());

        // Validate the DsSchemaRelationship in the database
        List<DsSchemaRelationship> dsSchemaRelationshipList = dsSchemaRelationshipRepository.findAll();
        assertThat(dsSchemaRelationshipList).hasSize(databaseSizeBeforeUpdate);
        DsSchemaRelationship testDsSchemaRelationship = dsSchemaRelationshipList.get(dsSchemaRelationshipList.size() - 1);
        assertThat(testDsSchemaRelationship.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testDsSchemaRelationship.getTarget()).isEqualTo(UPDATED_TARGET);
        assertThat(testDsSchemaRelationship.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testDsSchemaRelationship.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingDsSchemaRelationship() throws Exception {
        int databaseSizeBeforeUpdate = dsSchemaRelationshipRepository.findAll().size();
        dsSchemaRelationship.setId(count.incrementAndGet());

        // Create the DsSchemaRelationship
        DsSchemaRelationshipDTO dsSchemaRelationshipDTO = dsSchemaRelationshipMapper.toDto(dsSchemaRelationship);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDsSchemaRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dsSchemaRelationshipDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dsSchemaRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DsSchemaRelationship in the database
        List<DsSchemaRelationship> dsSchemaRelationshipList = dsSchemaRelationshipRepository.findAll();
        assertThat(dsSchemaRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDsSchemaRelationship() throws Exception {
        int databaseSizeBeforeUpdate = dsSchemaRelationshipRepository.findAll().size();
        dsSchemaRelationship.setId(count.incrementAndGet());

        // Create the DsSchemaRelationship
        DsSchemaRelationshipDTO dsSchemaRelationshipDTO = dsSchemaRelationshipMapper.toDto(dsSchemaRelationship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDsSchemaRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dsSchemaRelationshipDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DsSchemaRelationship in the database
        List<DsSchemaRelationship> dsSchemaRelationshipList = dsSchemaRelationshipRepository.findAll();
        assertThat(dsSchemaRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDsSchemaRelationship() throws Exception {
        int databaseSizeBeforeUpdate = dsSchemaRelationshipRepository.findAll().size();
        dsSchemaRelationship.setId(count.incrementAndGet());

        // Create the DsSchemaRelationship
        DsSchemaRelationshipDTO dsSchemaRelationshipDTO = dsSchemaRelationshipMapper.toDto(dsSchemaRelationship);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDsSchemaRelationshipMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dsSchemaRelationshipDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DsSchemaRelationship in the database
        List<DsSchemaRelationship> dsSchemaRelationshipList = dsSchemaRelationshipRepository.findAll();
        assertThat(dsSchemaRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDsSchemaRelationship() throws Exception {
        // Initialize the database
        dsSchemaRelationshipRepository.saveAndFlush(dsSchemaRelationship);

        int databaseSizeBeforeDelete = dsSchemaRelationshipRepository.findAll().size();

        // Delete the dsSchemaRelationship
        restDsSchemaRelationshipMockMvc
            .perform(delete(ENTITY_API_URL_ID, dsSchemaRelationship.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DsSchemaRelationship> dsSchemaRelationshipList = dsSchemaRelationshipRepository.findAll();
        assertThat(dsSchemaRelationshipList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
