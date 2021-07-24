package com.dataely.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dataely.app.IntegrationTest;
import com.dataely.app.domain.TableColumn;
import com.dataely.app.repository.TableColumnRepository;
import com.dataely.app.service.dto.TableColumnDTO;
import com.dataely.app.service.mapper.TableColumnMapper;
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
 * Integration tests for the {@link TableColumnResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TableColumnResourceIT {

    private static final String DEFAULT_COLUMN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COLUMN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COLUMN_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_COLUMN_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_COLUMN_SIZE = 1L;
    private static final Long UPDATED_COLUMN_SIZE = 2L;

    private static final Boolean DEFAULT_IS_NULLABLE = false;
    private static final Boolean UPDATED_IS_NULLABLE = true;

    private static final Boolean DEFAULT_IS_PRIMARY_KEY = false;
    private static final Boolean UPDATED_IS_PRIMARY_KEY = true;

    private static final Boolean DEFAULT_IS_FOREIGN_KEY = false;
    private static final Boolean UPDATED_IS_FOREIGN_KEY = true;

    private static final Boolean DEFAULT_IS_INDEXED = false;
    private static final Boolean UPDATED_IS_INDEXED = true;

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/table-columns";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TableColumnRepository tableColumnRepository;

    @Autowired
    private TableColumnMapper tableColumnMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTableColumnMockMvc;

    private TableColumn tableColumn;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TableColumn createEntity(EntityManager em) {
        TableColumn tableColumn = new TableColumn()
            .columnName(DEFAULT_COLUMN_NAME)
            .columnType(DEFAULT_COLUMN_TYPE)
            .columnSize(DEFAULT_COLUMN_SIZE)
            .isNullable(DEFAULT_IS_NULLABLE)
            .isPrimaryKey(DEFAULT_IS_PRIMARY_KEY)
            .isForeignKey(DEFAULT_IS_FOREIGN_KEY)
            .isIndexed(DEFAULT_IS_INDEXED)
            .creationDate(DEFAULT_CREATION_DATE)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return tableColumn;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TableColumn createUpdatedEntity(EntityManager em) {
        TableColumn tableColumn = new TableColumn()
            .columnName(UPDATED_COLUMN_NAME)
            .columnType(UPDATED_COLUMN_TYPE)
            .columnSize(UPDATED_COLUMN_SIZE)
            .isNullable(UPDATED_IS_NULLABLE)
            .isPrimaryKey(UPDATED_IS_PRIMARY_KEY)
            .isForeignKey(UPDATED_IS_FOREIGN_KEY)
            .isIndexed(UPDATED_IS_INDEXED)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return tableColumn;
    }

    @BeforeEach
    public void initTest() {
        tableColumn = createEntity(em);
    }

    @Test
    @Transactional
    void createTableColumn() throws Exception {
        int databaseSizeBeforeCreate = tableColumnRepository.findAll().size();
        // Create the TableColumn
        TableColumnDTO tableColumnDTO = tableColumnMapper.toDto(tableColumn);
        restTableColumnMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tableColumnDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TableColumn in the database
        List<TableColumn> tableColumnList = tableColumnRepository.findAll();
        assertThat(tableColumnList).hasSize(databaseSizeBeforeCreate + 1);
        TableColumn testTableColumn = tableColumnList.get(tableColumnList.size() - 1);
        assertThat(testTableColumn.getColumnName()).isEqualTo(DEFAULT_COLUMN_NAME);
        assertThat(testTableColumn.getColumnType()).isEqualTo(DEFAULT_COLUMN_TYPE);
        assertThat(testTableColumn.getColumnSize()).isEqualTo(DEFAULT_COLUMN_SIZE);
        assertThat(testTableColumn.getIsNullable()).isEqualTo(DEFAULT_IS_NULLABLE);
        assertThat(testTableColumn.getIsPrimaryKey()).isEqualTo(DEFAULT_IS_PRIMARY_KEY);
        assertThat(testTableColumn.getIsForeignKey()).isEqualTo(DEFAULT_IS_FOREIGN_KEY);
        assertThat(testTableColumn.getIsIndexed()).isEqualTo(DEFAULT_IS_INDEXED);
        assertThat(testTableColumn.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testTableColumn.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void createTableColumnWithExistingId() throws Exception {
        // Create the TableColumn with an existing ID
        tableColumn.setId(1L);
        TableColumnDTO tableColumnDTO = tableColumnMapper.toDto(tableColumn);

        int databaseSizeBeforeCreate = tableColumnRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTableColumnMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tableColumnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TableColumn in the database
        List<TableColumn> tableColumnList = tableColumnRepository.findAll();
        assertThat(tableColumnList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkColumnNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tableColumnRepository.findAll().size();
        // set the field null
        tableColumn.setColumnName(null);

        // Create the TableColumn, which fails.
        TableColumnDTO tableColumnDTO = tableColumnMapper.toDto(tableColumn);

        restTableColumnMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tableColumnDTO))
            )
            .andExpect(status().isBadRequest());

        List<TableColumn> tableColumnList = tableColumnRepository.findAll();
        assertThat(tableColumnList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTableColumns() throws Exception {
        // Initialize the database
        tableColumnRepository.saveAndFlush(tableColumn);

        // Get all the tableColumnList
        restTableColumnMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tableColumn.getId().intValue())))
            .andExpect(jsonPath("$.[*].columnName").value(hasItem(DEFAULT_COLUMN_NAME)))
            .andExpect(jsonPath("$.[*].columnType").value(hasItem(DEFAULT_COLUMN_TYPE)))
            .andExpect(jsonPath("$.[*].columnSize").value(hasItem(DEFAULT_COLUMN_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].isNullable").value(hasItem(DEFAULT_IS_NULLABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].isPrimaryKey").value(hasItem(DEFAULT_IS_PRIMARY_KEY.booleanValue())))
            .andExpect(jsonPath("$.[*].isForeignKey").value(hasItem(DEFAULT_IS_FOREIGN_KEY.booleanValue())))
            .andExpect(jsonPath("$.[*].isIndexed").value(hasItem(DEFAULT_IS_INDEXED.booleanValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getTableColumn() throws Exception {
        // Initialize the database
        tableColumnRepository.saveAndFlush(tableColumn);

        // Get the tableColumn
        restTableColumnMockMvc
            .perform(get(ENTITY_API_URL_ID, tableColumn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tableColumn.getId().intValue()))
            .andExpect(jsonPath("$.columnName").value(DEFAULT_COLUMN_NAME))
            .andExpect(jsonPath("$.columnType").value(DEFAULT_COLUMN_TYPE))
            .andExpect(jsonPath("$.columnSize").value(DEFAULT_COLUMN_SIZE.intValue()))
            .andExpect(jsonPath("$.isNullable").value(DEFAULT_IS_NULLABLE.booleanValue()))
            .andExpect(jsonPath("$.isPrimaryKey").value(DEFAULT_IS_PRIMARY_KEY.booleanValue()))
            .andExpect(jsonPath("$.isForeignKey").value(DEFAULT_IS_FOREIGN_KEY.booleanValue()))
            .andExpect(jsonPath("$.isIndexed").value(DEFAULT_IS_INDEXED.booleanValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTableColumn() throws Exception {
        // Get the tableColumn
        restTableColumnMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTableColumn() throws Exception {
        // Initialize the database
        tableColumnRepository.saveAndFlush(tableColumn);

        int databaseSizeBeforeUpdate = tableColumnRepository.findAll().size();

        // Update the tableColumn
        TableColumn updatedTableColumn = tableColumnRepository.findById(tableColumn.getId()).get();
        // Disconnect from session so that the updates on updatedTableColumn are not directly saved in db
        em.detach(updatedTableColumn);
        updatedTableColumn
            .columnName(UPDATED_COLUMN_NAME)
            .columnType(UPDATED_COLUMN_TYPE)
            .columnSize(UPDATED_COLUMN_SIZE)
            .isNullable(UPDATED_IS_NULLABLE)
            .isPrimaryKey(UPDATED_IS_PRIMARY_KEY)
            .isForeignKey(UPDATED_IS_FOREIGN_KEY)
            .isIndexed(UPDATED_IS_INDEXED)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        TableColumnDTO tableColumnDTO = tableColumnMapper.toDto(updatedTableColumn);

        restTableColumnMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tableColumnDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tableColumnDTO))
            )
            .andExpect(status().isOk());

        // Validate the TableColumn in the database
        List<TableColumn> tableColumnList = tableColumnRepository.findAll();
        assertThat(tableColumnList).hasSize(databaseSizeBeforeUpdate);
        TableColumn testTableColumn = tableColumnList.get(tableColumnList.size() - 1);
        assertThat(testTableColumn.getColumnName()).isEqualTo(UPDATED_COLUMN_NAME);
        assertThat(testTableColumn.getColumnType()).isEqualTo(UPDATED_COLUMN_TYPE);
        assertThat(testTableColumn.getColumnSize()).isEqualTo(UPDATED_COLUMN_SIZE);
        assertThat(testTableColumn.getIsNullable()).isEqualTo(UPDATED_IS_NULLABLE);
        assertThat(testTableColumn.getIsPrimaryKey()).isEqualTo(UPDATED_IS_PRIMARY_KEY);
        assertThat(testTableColumn.getIsForeignKey()).isEqualTo(UPDATED_IS_FOREIGN_KEY);
        assertThat(testTableColumn.getIsIndexed()).isEqualTo(UPDATED_IS_INDEXED);
        assertThat(testTableColumn.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testTableColumn.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingTableColumn() throws Exception {
        int databaseSizeBeforeUpdate = tableColumnRepository.findAll().size();
        tableColumn.setId(count.incrementAndGet());

        // Create the TableColumn
        TableColumnDTO tableColumnDTO = tableColumnMapper.toDto(tableColumn);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTableColumnMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tableColumnDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tableColumnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TableColumn in the database
        List<TableColumn> tableColumnList = tableColumnRepository.findAll();
        assertThat(tableColumnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTableColumn() throws Exception {
        int databaseSizeBeforeUpdate = tableColumnRepository.findAll().size();
        tableColumn.setId(count.incrementAndGet());

        // Create the TableColumn
        TableColumnDTO tableColumnDTO = tableColumnMapper.toDto(tableColumn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTableColumnMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tableColumnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TableColumn in the database
        List<TableColumn> tableColumnList = tableColumnRepository.findAll();
        assertThat(tableColumnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTableColumn() throws Exception {
        int databaseSizeBeforeUpdate = tableColumnRepository.findAll().size();
        tableColumn.setId(count.incrementAndGet());

        // Create the TableColumn
        TableColumnDTO tableColumnDTO = tableColumnMapper.toDto(tableColumn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTableColumnMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tableColumnDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TableColumn in the database
        List<TableColumn> tableColumnList = tableColumnRepository.findAll();
        assertThat(tableColumnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTableColumnWithPatch() throws Exception {
        // Initialize the database
        tableColumnRepository.saveAndFlush(tableColumn);

        int databaseSizeBeforeUpdate = tableColumnRepository.findAll().size();

        // Update the tableColumn using partial update
        TableColumn partialUpdatedTableColumn = new TableColumn();
        partialUpdatedTableColumn.setId(tableColumn.getId());

        partialUpdatedTableColumn
            .columnName(UPDATED_COLUMN_NAME)
            .isNullable(UPDATED_IS_NULLABLE)
            .isPrimaryKey(UPDATED_IS_PRIMARY_KEY)
            .isForeignKey(UPDATED_IS_FOREIGN_KEY)
            .isIndexed(UPDATED_IS_INDEXED);

        restTableColumnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTableColumn.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTableColumn))
            )
            .andExpect(status().isOk());

        // Validate the TableColumn in the database
        List<TableColumn> tableColumnList = tableColumnRepository.findAll();
        assertThat(tableColumnList).hasSize(databaseSizeBeforeUpdate);
        TableColumn testTableColumn = tableColumnList.get(tableColumnList.size() - 1);
        assertThat(testTableColumn.getColumnName()).isEqualTo(UPDATED_COLUMN_NAME);
        assertThat(testTableColumn.getColumnType()).isEqualTo(DEFAULT_COLUMN_TYPE);
        assertThat(testTableColumn.getColumnSize()).isEqualTo(DEFAULT_COLUMN_SIZE);
        assertThat(testTableColumn.getIsNullable()).isEqualTo(UPDATED_IS_NULLABLE);
        assertThat(testTableColumn.getIsPrimaryKey()).isEqualTo(UPDATED_IS_PRIMARY_KEY);
        assertThat(testTableColumn.getIsForeignKey()).isEqualTo(UPDATED_IS_FOREIGN_KEY);
        assertThat(testTableColumn.getIsIndexed()).isEqualTo(UPDATED_IS_INDEXED);
        assertThat(testTableColumn.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testTableColumn.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateTableColumnWithPatch() throws Exception {
        // Initialize the database
        tableColumnRepository.saveAndFlush(tableColumn);

        int databaseSizeBeforeUpdate = tableColumnRepository.findAll().size();

        // Update the tableColumn using partial update
        TableColumn partialUpdatedTableColumn = new TableColumn();
        partialUpdatedTableColumn.setId(tableColumn.getId());

        partialUpdatedTableColumn
            .columnName(UPDATED_COLUMN_NAME)
            .columnType(UPDATED_COLUMN_TYPE)
            .columnSize(UPDATED_COLUMN_SIZE)
            .isNullable(UPDATED_IS_NULLABLE)
            .isPrimaryKey(UPDATED_IS_PRIMARY_KEY)
            .isForeignKey(UPDATED_IS_FOREIGN_KEY)
            .isIndexed(UPDATED_IS_INDEXED)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restTableColumnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTableColumn.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTableColumn))
            )
            .andExpect(status().isOk());

        // Validate the TableColumn in the database
        List<TableColumn> tableColumnList = tableColumnRepository.findAll();
        assertThat(tableColumnList).hasSize(databaseSizeBeforeUpdate);
        TableColumn testTableColumn = tableColumnList.get(tableColumnList.size() - 1);
        assertThat(testTableColumn.getColumnName()).isEqualTo(UPDATED_COLUMN_NAME);
        assertThat(testTableColumn.getColumnType()).isEqualTo(UPDATED_COLUMN_TYPE);
        assertThat(testTableColumn.getColumnSize()).isEqualTo(UPDATED_COLUMN_SIZE);
        assertThat(testTableColumn.getIsNullable()).isEqualTo(UPDATED_IS_NULLABLE);
        assertThat(testTableColumn.getIsPrimaryKey()).isEqualTo(UPDATED_IS_PRIMARY_KEY);
        assertThat(testTableColumn.getIsForeignKey()).isEqualTo(UPDATED_IS_FOREIGN_KEY);
        assertThat(testTableColumn.getIsIndexed()).isEqualTo(UPDATED_IS_INDEXED);
        assertThat(testTableColumn.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testTableColumn.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingTableColumn() throws Exception {
        int databaseSizeBeforeUpdate = tableColumnRepository.findAll().size();
        tableColumn.setId(count.incrementAndGet());

        // Create the TableColumn
        TableColumnDTO tableColumnDTO = tableColumnMapper.toDto(tableColumn);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTableColumnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tableColumnDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tableColumnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TableColumn in the database
        List<TableColumn> tableColumnList = tableColumnRepository.findAll();
        assertThat(tableColumnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTableColumn() throws Exception {
        int databaseSizeBeforeUpdate = tableColumnRepository.findAll().size();
        tableColumn.setId(count.incrementAndGet());

        // Create the TableColumn
        TableColumnDTO tableColumnDTO = tableColumnMapper.toDto(tableColumn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTableColumnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tableColumnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TableColumn in the database
        List<TableColumn> tableColumnList = tableColumnRepository.findAll();
        assertThat(tableColumnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTableColumn() throws Exception {
        int databaseSizeBeforeUpdate = tableColumnRepository.findAll().size();
        tableColumn.setId(count.incrementAndGet());

        // Create the TableColumn
        TableColumnDTO tableColumnDTO = tableColumnMapper.toDto(tableColumn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTableColumnMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tableColumnDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TableColumn in the database
        List<TableColumn> tableColumnList = tableColumnRepository.findAll();
        assertThat(tableColumnList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTableColumn() throws Exception {
        // Initialize the database
        tableColumnRepository.saveAndFlush(tableColumn);

        int databaseSizeBeforeDelete = tableColumnRepository.findAll().size();

        // Delete the tableColumn
        restTableColumnMockMvc
            .perform(delete(ENTITY_API_URL_ID, tableColumn.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TableColumn> tableColumnList = tableColumnRepository.findAll();
        assertThat(tableColumnList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
