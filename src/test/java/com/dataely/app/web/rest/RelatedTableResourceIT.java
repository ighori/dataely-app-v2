package com.dataely.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dataely.app.IntegrationTest;
import com.dataely.app.domain.RelatedTable;
import com.dataely.app.repository.RelatedTableRepository;
import com.dataely.app.service.dto.RelatedTableDTO;
import com.dataely.app.service.mapper.RelatedTableMapper;
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
 * Integration tests for the {@link RelatedTableResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RelatedTableResourceIT {

    private static final String DEFAULT_TABLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TABLE_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/related-tables";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RelatedTableRepository relatedTableRepository;

    @Autowired
    private RelatedTableMapper relatedTableMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRelatedTableMockMvc;

    private RelatedTable relatedTable;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelatedTable createEntity(EntityManager em) {
        RelatedTable relatedTable = new RelatedTable()
            .tableName(DEFAULT_TABLE_NAME)
            .creationDate(DEFAULT_CREATION_DATE)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return relatedTable;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelatedTable createUpdatedEntity(EntityManager em) {
        RelatedTable relatedTable = new RelatedTable()
            .tableName(UPDATED_TABLE_NAME)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return relatedTable;
    }

    @BeforeEach
    public void initTest() {
        relatedTable = createEntity(em);
    }

    @Test
    @Transactional
    void createRelatedTable() throws Exception {
        int databaseSizeBeforeCreate = relatedTableRepository.findAll().size();
        // Create the RelatedTable
        RelatedTableDTO relatedTableDTO = relatedTableMapper.toDto(relatedTable);
        restRelatedTableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relatedTableDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RelatedTable in the database
        List<RelatedTable> relatedTableList = relatedTableRepository.findAll();
        assertThat(relatedTableList).hasSize(databaseSizeBeforeCreate + 1);
        RelatedTable testRelatedTable = relatedTableList.get(relatedTableList.size() - 1);
        assertThat(testRelatedTable.getTableName()).isEqualTo(DEFAULT_TABLE_NAME);
        assertThat(testRelatedTable.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testRelatedTable.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void createRelatedTableWithExistingId() throws Exception {
        // Create the RelatedTable with an existing ID
        relatedTable.setId(1L);
        RelatedTableDTO relatedTableDTO = relatedTableMapper.toDto(relatedTable);

        int databaseSizeBeforeCreate = relatedTableRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelatedTableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relatedTableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedTable in the database
        List<RelatedTable> relatedTableList = relatedTableRepository.findAll();
        assertThat(relatedTableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTableNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = relatedTableRepository.findAll().size();
        // set the field null
        relatedTable.setTableName(null);

        // Create the RelatedTable, which fails.
        RelatedTableDTO relatedTableDTO = relatedTableMapper.toDto(relatedTable);

        restRelatedTableMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relatedTableDTO))
            )
            .andExpect(status().isBadRequest());

        List<RelatedTable> relatedTableList = relatedTableRepository.findAll();
        assertThat(relatedTableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRelatedTables() throws Exception {
        // Initialize the database
        relatedTableRepository.saveAndFlush(relatedTable);

        // Get all the relatedTableList
        restRelatedTableMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relatedTable.getId().intValue())))
            .andExpect(jsonPath("$.[*].tableName").value(hasItem(DEFAULT_TABLE_NAME)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getRelatedTable() throws Exception {
        // Initialize the database
        relatedTableRepository.saveAndFlush(relatedTable);

        // Get the relatedTable
        restRelatedTableMockMvc
            .perform(get(ENTITY_API_URL_ID, relatedTable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(relatedTable.getId().intValue()))
            .andExpect(jsonPath("$.tableName").value(DEFAULT_TABLE_NAME))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRelatedTable() throws Exception {
        // Get the relatedTable
        restRelatedTableMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRelatedTable() throws Exception {
        // Initialize the database
        relatedTableRepository.saveAndFlush(relatedTable);

        int databaseSizeBeforeUpdate = relatedTableRepository.findAll().size();

        // Update the relatedTable
        RelatedTable updatedRelatedTable = relatedTableRepository.findById(relatedTable.getId()).get();
        // Disconnect from session so that the updates on updatedRelatedTable are not directly saved in db
        em.detach(updatedRelatedTable);
        updatedRelatedTable.tableName(UPDATED_TABLE_NAME).creationDate(UPDATED_CREATION_DATE).lastUpdated(UPDATED_LAST_UPDATED);
        RelatedTableDTO relatedTableDTO = relatedTableMapper.toDto(updatedRelatedTable);

        restRelatedTableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, relatedTableDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedTableDTO))
            )
            .andExpect(status().isOk());

        // Validate the RelatedTable in the database
        List<RelatedTable> relatedTableList = relatedTableRepository.findAll();
        assertThat(relatedTableList).hasSize(databaseSizeBeforeUpdate);
        RelatedTable testRelatedTable = relatedTableList.get(relatedTableList.size() - 1);
        assertThat(testRelatedTable.getTableName()).isEqualTo(UPDATED_TABLE_NAME);
        assertThat(testRelatedTable.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testRelatedTable.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingRelatedTable() throws Exception {
        int databaseSizeBeforeUpdate = relatedTableRepository.findAll().size();
        relatedTable.setId(count.incrementAndGet());

        // Create the RelatedTable
        RelatedTableDTO relatedTableDTO = relatedTableMapper.toDto(relatedTable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelatedTableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, relatedTableDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedTableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedTable in the database
        List<RelatedTable> relatedTableList = relatedTableRepository.findAll();
        assertThat(relatedTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRelatedTable() throws Exception {
        int databaseSizeBeforeUpdate = relatedTableRepository.findAll().size();
        relatedTable.setId(count.incrementAndGet());

        // Create the RelatedTable
        RelatedTableDTO relatedTableDTO = relatedTableMapper.toDto(relatedTable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedTableMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedTableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedTable in the database
        List<RelatedTable> relatedTableList = relatedTableRepository.findAll();
        assertThat(relatedTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRelatedTable() throws Exception {
        int databaseSizeBeforeUpdate = relatedTableRepository.findAll().size();
        relatedTable.setId(count.incrementAndGet());

        // Create the RelatedTable
        RelatedTableDTO relatedTableDTO = relatedTableMapper.toDto(relatedTable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedTableMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(relatedTableDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RelatedTable in the database
        List<RelatedTable> relatedTableList = relatedTableRepository.findAll();
        assertThat(relatedTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRelatedTableWithPatch() throws Exception {
        // Initialize the database
        relatedTableRepository.saveAndFlush(relatedTable);

        int databaseSizeBeforeUpdate = relatedTableRepository.findAll().size();

        // Update the relatedTable using partial update
        RelatedTable partialUpdatedRelatedTable = new RelatedTable();
        partialUpdatedRelatedTable.setId(relatedTable.getId());

        partialUpdatedRelatedTable.lastUpdated(UPDATED_LAST_UPDATED);

        restRelatedTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelatedTable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelatedTable))
            )
            .andExpect(status().isOk());

        // Validate the RelatedTable in the database
        List<RelatedTable> relatedTableList = relatedTableRepository.findAll();
        assertThat(relatedTableList).hasSize(databaseSizeBeforeUpdate);
        RelatedTable testRelatedTable = relatedTableList.get(relatedTableList.size() - 1);
        assertThat(testRelatedTable.getTableName()).isEqualTo(DEFAULT_TABLE_NAME);
        assertThat(testRelatedTable.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testRelatedTable.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateRelatedTableWithPatch() throws Exception {
        // Initialize the database
        relatedTableRepository.saveAndFlush(relatedTable);

        int databaseSizeBeforeUpdate = relatedTableRepository.findAll().size();

        // Update the relatedTable using partial update
        RelatedTable partialUpdatedRelatedTable = new RelatedTable();
        partialUpdatedRelatedTable.setId(relatedTable.getId());

        partialUpdatedRelatedTable.tableName(UPDATED_TABLE_NAME).creationDate(UPDATED_CREATION_DATE).lastUpdated(UPDATED_LAST_UPDATED);

        restRelatedTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelatedTable.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelatedTable))
            )
            .andExpect(status().isOk());

        // Validate the RelatedTable in the database
        List<RelatedTable> relatedTableList = relatedTableRepository.findAll();
        assertThat(relatedTableList).hasSize(databaseSizeBeforeUpdate);
        RelatedTable testRelatedTable = relatedTableList.get(relatedTableList.size() - 1);
        assertThat(testRelatedTable.getTableName()).isEqualTo(UPDATED_TABLE_NAME);
        assertThat(testRelatedTable.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testRelatedTable.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingRelatedTable() throws Exception {
        int databaseSizeBeforeUpdate = relatedTableRepository.findAll().size();
        relatedTable.setId(count.incrementAndGet());

        // Create the RelatedTable
        RelatedTableDTO relatedTableDTO = relatedTableMapper.toDto(relatedTable);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelatedTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, relatedTableDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relatedTableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedTable in the database
        List<RelatedTable> relatedTableList = relatedTableRepository.findAll();
        assertThat(relatedTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRelatedTable() throws Exception {
        int databaseSizeBeforeUpdate = relatedTableRepository.findAll().size();
        relatedTable.setId(count.incrementAndGet());

        // Create the RelatedTable
        RelatedTableDTO relatedTableDTO = relatedTableMapper.toDto(relatedTable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedTableMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relatedTableDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedTable in the database
        List<RelatedTable> relatedTableList = relatedTableRepository.findAll();
        assertThat(relatedTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRelatedTable() throws Exception {
        int databaseSizeBeforeUpdate = relatedTableRepository.findAll().size();
        relatedTable.setId(count.incrementAndGet());

        // Create the RelatedTable
        RelatedTableDTO relatedTableDTO = relatedTableMapper.toDto(relatedTable);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedTableMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relatedTableDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RelatedTable in the database
        List<RelatedTable> relatedTableList = relatedTableRepository.findAll();
        assertThat(relatedTableList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRelatedTable() throws Exception {
        // Initialize the database
        relatedTableRepository.saveAndFlush(relatedTable);

        int databaseSizeBeforeDelete = relatedTableRepository.findAll().size();

        // Delete the relatedTable
        restRelatedTableMockMvc
            .perform(delete(ENTITY_API_URL_ID, relatedTable.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RelatedTable> relatedTableList = relatedTableRepository.findAll();
        assertThat(relatedTableList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
