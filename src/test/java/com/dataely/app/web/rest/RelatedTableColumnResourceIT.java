package com.dataely.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dataely.app.IntegrationTest;
import com.dataely.app.domain.RelatedTableColumn;
import com.dataely.app.repository.RelatedTableColumnRepository;
import com.dataely.app.service.dto.RelatedTableColumnDTO;
import com.dataely.app.service.mapper.RelatedTableColumnMapper;
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
 * Integration tests for the {@link RelatedTableColumnResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RelatedTableColumnResourceIT {

    private static final String DEFAULT_COLUMN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COLUMN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COLUMN_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_COLUMN_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/related-table-columns";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RelatedTableColumnRepository relatedTableColumnRepository;

    @Autowired
    private RelatedTableColumnMapper relatedTableColumnMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRelatedTableColumnMockMvc;

    private RelatedTableColumn relatedTableColumn;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelatedTableColumn createEntity(EntityManager em) {
        RelatedTableColumn relatedTableColumn = new RelatedTableColumn()
            .columnName(DEFAULT_COLUMN_NAME)
            .columnType(DEFAULT_COLUMN_TYPE)
            .creationDate(DEFAULT_CREATION_DATE)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return relatedTableColumn;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RelatedTableColumn createUpdatedEntity(EntityManager em) {
        RelatedTableColumn relatedTableColumn = new RelatedTableColumn()
            .columnName(UPDATED_COLUMN_NAME)
            .columnType(UPDATED_COLUMN_TYPE)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return relatedTableColumn;
    }

    @BeforeEach
    public void initTest() {
        relatedTableColumn = createEntity(em);
    }

    @Test
    @Transactional
    void createRelatedTableColumn() throws Exception {
        int databaseSizeBeforeCreate = relatedTableColumnRepository.findAll().size();
        // Create the RelatedTableColumn
        RelatedTableColumnDTO relatedTableColumnDTO = relatedTableColumnMapper.toDto(relatedTableColumn);
        restRelatedTableColumnMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedTableColumnDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RelatedTableColumn in the database
        List<RelatedTableColumn> relatedTableColumnList = relatedTableColumnRepository.findAll();
        assertThat(relatedTableColumnList).hasSize(databaseSizeBeforeCreate + 1);
        RelatedTableColumn testRelatedTableColumn = relatedTableColumnList.get(relatedTableColumnList.size() - 1);
        assertThat(testRelatedTableColumn.getColumnName()).isEqualTo(DEFAULT_COLUMN_NAME);
        assertThat(testRelatedTableColumn.getColumnType()).isEqualTo(DEFAULT_COLUMN_TYPE);
        assertThat(testRelatedTableColumn.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testRelatedTableColumn.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void createRelatedTableColumnWithExistingId() throws Exception {
        // Create the RelatedTableColumn with an existing ID
        relatedTableColumn.setId(1L);
        RelatedTableColumnDTO relatedTableColumnDTO = relatedTableColumnMapper.toDto(relatedTableColumn);

        int databaseSizeBeforeCreate = relatedTableColumnRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelatedTableColumnMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedTableColumnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedTableColumn in the database
        List<RelatedTableColumn> relatedTableColumnList = relatedTableColumnRepository.findAll();
        assertThat(relatedTableColumnList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkColumnNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = relatedTableColumnRepository.findAll().size();
        // set the field null
        relatedTableColumn.setColumnName(null);

        // Create the RelatedTableColumn, which fails.
        RelatedTableColumnDTO relatedTableColumnDTO = relatedTableColumnMapper.toDto(relatedTableColumn);

        restRelatedTableColumnMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedTableColumnDTO))
            )
            .andExpect(status().isBadRequest());

        List<RelatedTableColumn> relatedTableColumnList = relatedTableColumnRepository.findAll();
        assertThat(relatedTableColumnList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRelatedTableColumns() throws Exception {
        // Initialize the database
        relatedTableColumnRepository.saveAndFlush(relatedTableColumn);

        // Get all the relatedTableColumnList
        restRelatedTableColumnMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relatedTableColumn.getId().intValue())))
            .andExpect(jsonPath("$.[*].columnName").value(hasItem(DEFAULT_COLUMN_NAME)))
            .andExpect(jsonPath("$.[*].columnType").value(hasItem(DEFAULT_COLUMN_TYPE)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getRelatedTableColumn() throws Exception {
        // Initialize the database
        relatedTableColumnRepository.saveAndFlush(relatedTableColumn);

        // Get the relatedTableColumn
        restRelatedTableColumnMockMvc
            .perform(get(ENTITY_API_URL_ID, relatedTableColumn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(relatedTableColumn.getId().intValue()))
            .andExpect(jsonPath("$.columnName").value(DEFAULT_COLUMN_NAME))
            .andExpect(jsonPath("$.columnType").value(DEFAULT_COLUMN_TYPE))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRelatedTableColumn() throws Exception {
        // Get the relatedTableColumn
        restRelatedTableColumnMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRelatedTableColumn() throws Exception {
        // Initialize the database
        relatedTableColumnRepository.saveAndFlush(relatedTableColumn);

        int databaseSizeBeforeUpdate = relatedTableColumnRepository.findAll().size();

        // Update the relatedTableColumn
        RelatedTableColumn updatedRelatedTableColumn = relatedTableColumnRepository.findById(relatedTableColumn.getId()).get();
        // Disconnect from session so that the updates on updatedRelatedTableColumn are not directly saved in db
        em.detach(updatedRelatedTableColumn);
        updatedRelatedTableColumn
            .columnName(UPDATED_COLUMN_NAME)
            .columnType(UPDATED_COLUMN_TYPE)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        RelatedTableColumnDTO relatedTableColumnDTO = relatedTableColumnMapper.toDto(updatedRelatedTableColumn);

        restRelatedTableColumnMockMvc
            .perform(
                put(ENTITY_API_URL_ID, relatedTableColumnDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedTableColumnDTO))
            )
            .andExpect(status().isOk());

        // Validate the RelatedTableColumn in the database
        List<RelatedTableColumn> relatedTableColumnList = relatedTableColumnRepository.findAll();
        assertThat(relatedTableColumnList).hasSize(databaseSizeBeforeUpdate);
        RelatedTableColumn testRelatedTableColumn = relatedTableColumnList.get(relatedTableColumnList.size() - 1);
        assertThat(testRelatedTableColumn.getColumnName()).isEqualTo(UPDATED_COLUMN_NAME);
        assertThat(testRelatedTableColumn.getColumnType()).isEqualTo(UPDATED_COLUMN_TYPE);
        assertThat(testRelatedTableColumn.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testRelatedTableColumn.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingRelatedTableColumn() throws Exception {
        int databaseSizeBeforeUpdate = relatedTableColumnRepository.findAll().size();
        relatedTableColumn.setId(count.incrementAndGet());

        // Create the RelatedTableColumn
        RelatedTableColumnDTO relatedTableColumnDTO = relatedTableColumnMapper.toDto(relatedTableColumn);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelatedTableColumnMockMvc
            .perform(
                put(ENTITY_API_URL_ID, relatedTableColumnDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedTableColumnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedTableColumn in the database
        List<RelatedTableColumn> relatedTableColumnList = relatedTableColumnRepository.findAll();
        assertThat(relatedTableColumnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRelatedTableColumn() throws Exception {
        int databaseSizeBeforeUpdate = relatedTableColumnRepository.findAll().size();
        relatedTableColumn.setId(count.incrementAndGet());

        // Create the RelatedTableColumn
        RelatedTableColumnDTO relatedTableColumnDTO = relatedTableColumnMapper.toDto(relatedTableColumn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedTableColumnMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedTableColumnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedTableColumn in the database
        List<RelatedTableColumn> relatedTableColumnList = relatedTableColumnRepository.findAll();
        assertThat(relatedTableColumnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRelatedTableColumn() throws Exception {
        int databaseSizeBeforeUpdate = relatedTableColumnRepository.findAll().size();
        relatedTableColumn.setId(count.incrementAndGet());

        // Create the RelatedTableColumn
        RelatedTableColumnDTO relatedTableColumnDTO = relatedTableColumnMapper.toDto(relatedTableColumn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedTableColumnMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(relatedTableColumnDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RelatedTableColumn in the database
        List<RelatedTableColumn> relatedTableColumnList = relatedTableColumnRepository.findAll();
        assertThat(relatedTableColumnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRelatedTableColumnWithPatch() throws Exception {
        // Initialize the database
        relatedTableColumnRepository.saveAndFlush(relatedTableColumn);

        int databaseSizeBeforeUpdate = relatedTableColumnRepository.findAll().size();

        // Update the relatedTableColumn using partial update
        RelatedTableColumn partialUpdatedRelatedTableColumn = new RelatedTableColumn();
        partialUpdatedRelatedTableColumn.setId(relatedTableColumn.getId());

        restRelatedTableColumnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelatedTableColumn.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelatedTableColumn))
            )
            .andExpect(status().isOk());

        // Validate the RelatedTableColumn in the database
        List<RelatedTableColumn> relatedTableColumnList = relatedTableColumnRepository.findAll();
        assertThat(relatedTableColumnList).hasSize(databaseSizeBeforeUpdate);
        RelatedTableColumn testRelatedTableColumn = relatedTableColumnList.get(relatedTableColumnList.size() - 1);
        assertThat(testRelatedTableColumn.getColumnName()).isEqualTo(DEFAULT_COLUMN_NAME);
        assertThat(testRelatedTableColumn.getColumnType()).isEqualTo(DEFAULT_COLUMN_TYPE);
        assertThat(testRelatedTableColumn.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testRelatedTableColumn.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateRelatedTableColumnWithPatch() throws Exception {
        // Initialize the database
        relatedTableColumnRepository.saveAndFlush(relatedTableColumn);

        int databaseSizeBeforeUpdate = relatedTableColumnRepository.findAll().size();

        // Update the relatedTableColumn using partial update
        RelatedTableColumn partialUpdatedRelatedTableColumn = new RelatedTableColumn();
        partialUpdatedRelatedTableColumn.setId(relatedTableColumn.getId());

        partialUpdatedRelatedTableColumn
            .columnName(UPDATED_COLUMN_NAME)
            .columnType(UPDATED_COLUMN_TYPE)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restRelatedTableColumnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRelatedTableColumn.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRelatedTableColumn))
            )
            .andExpect(status().isOk());

        // Validate the RelatedTableColumn in the database
        List<RelatedTableColumn> relatedTableColumnList = relatedTableColumnRepository.findAll();
        assertThat(relatedTableColumnList).hasSize(databaseSizeBeforeUpdate);
        RelatedTableColumn testRelatedTableColumn = relatedTableColumnList.get(relatedTableColumnList.size() - 1);
        assertThat(testRelatedTableColumn.getColumnName()).isEqualTo(UPDATED_COLUMN_NAME);
        assertThat(testRelatedTableColumn.getColumnType()).isEqualTo(UPDATED_COLUMN_TYPE);
        assertThat(testRelatedTableColumn.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testRelatedTableColumn.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingRelatedTableColumn() throws Exception {
        int databaseSizeBeforeUpdate = relatedTableColumnRepository.findAll().size();
        relatedTableColumn.setId(count.incrementAndGet());

        // Create the RelatedTableColumn
        RelatedTableColumnDTO relatedTableColumnDTO = relatedTableColumnMapper.toDto(relatedTableColumn);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelatedTableColumnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, relatedTableColumnDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relatedTableColumnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedTableColumn in the database
        List<RelatedTableColumn> relatedTableColumnList = relatedTableColumnRepository.findAll();
        assertThat(relatedTableColumnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRelatedTableColumn() throws Exception {
        int databaseSizeBeforeUpdate = relatedTableColumnRepository.findAll().size();
        relatedTableColumn.setId(count.incrementAndGet());

        // Create the RelatedTableColumn
        RelatedTableColumnDTO relatedTableColumnDTO = relatedTableColumnMapper.toDto(relatedTableColumn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedTableColumnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relatedTableColumnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RelatedTableColumn in the database
        List<RelatedTableColumn> relatedTableColumnList = relatedTableColumnRepository.findAll();
        assertThat(relatedTableColumnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRelatedTableColumn() throws Exception {
        int databaseSizeBeforeUpdate = relatedTableColumnRepository.findAll().size();
        relatedTableColumn.setId(count.incrementAndGet());

        // Create the RelatedTableColumn
        RelatedTableColumnDTO relatedTableColumnDTO = relatedTableColumnMapper.toDto(relatedTableColumn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRelatedTableColumnMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(relatedTableColumnDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RelatedTableColumn in the database
        List<RelatedTableColumn> relatedTableColumnList = relatedTableColumnRepository.findAll();
        assertThat(relatedTableColumnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRelatedTableColumn() throws Exception {
        // Initialize the database
        relatedTableColumnRepository.saveAndFlush(relatedTableColumn);

        int databaseSizeBeforeDelete = relatedTableColumnRepository.findAll().size();

        // Delete the relatedTableColumn
        restRelatedTableColumnMockMvc
            .perform(delete(ENTITY_API_URL_ID, relatedTableColumn.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RelatedTableColumn> relatedTableColumnList = relatedTableColumnRepository.findAll();
        assertThat(relatedTableColumnList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
