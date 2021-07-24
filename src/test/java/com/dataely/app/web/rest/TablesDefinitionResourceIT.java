package com.dataely.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dataely.app.IntegrationTest;
import com.dataely.app.domain.TablesDefinition;
import com.dataely.app.repository.TablesDefinitionRepository;
import com.dataely.app.service.dto.TablesDefinitionDTO;
import com.dataely.app.service.mapper.TablesDefinitionMapper;
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
 * Integration tests for the {@link TablesDefinitionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TablesDefinitionResourceIT {

    private static final String DEFAULT_TABLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TABLE_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALUE = 1;
    private static final Integer UPDATED_VALUE = 2;

    private static final Integer DEFAULT_SYMBOL_SIZE = 1;
    private static final Integer UPDATED_SYMBOL_SIZE = 2;

    private static final Integer DEFAULT_CATEGORY = 1;
    private static final Integer UPDATED_CATEGORY = 2;

    private static final Integer DEFAULT_COL_CNT = 1;
    private static final Integer UPDATED_COL_CNT = 2;

    private static final Integer DEFAULT_COL_CNT_NBR = 1;
    private static final Integer UPDATED_COL_CNT_NBR = 2;

    private static final Integer DEFAULT_COL_CNT_TB = 1;
    private static final Integer UPDATED_COL_CNT_TB = 2;

    private static final Integer DEFAULT_COL_CNT_STR = 1;
    private static final Integer UPDATED_COL_CNT_STR = 2;

    private static final Integer DEFAULT_COL_CNT_BL = 1;
    private static final Integer UPDATED_COL_CNT_BL = 2;

    private static final Integer DEFAULT_COL_CNT_PK = 1;
    private static final Integer UPDATED_COL_CNT_PK = 2;

    private static final Integer DEFAULT_COL_CNT_FK = 1;
    private static final Integer UPDATED_COL_CNT_FK = 2;

    private static final Integer DEFAULT_COL_CNT_IX = 1;
    private static final Integer UPDATED_COL_CNT_IX = 2;

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/tables-definitions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TablesDefinitionRepository tablesDefinitionRepository;

    @Autowired
    private TablesDefinitionMapper tablesDefinitionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTablesDefinitionMockMvc;

    private TablesDefinition tablesDefinition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TablesDefinition createEntity(EntityManager em) {
        TablesDefinition tablesDefinition = new TablesDefinition()
            .tableName(DEFAULT_TABLE_NAME)
            .value(DEFAULT_VALUE)
            .symbolSize(DEFAULT_SYMBOL_SIZE)
            .category(DEFAULT_CATEGORY)
            .colCnt(DEFAULT_COL_CNT)
            .colCntNbr(DEFAULT_COL_CNT_NBR)
            .colCntTB(DEFAULT_COL_CNT_TB)
            .colCntSTR(DEFAULT_COL_CNT_STR)
            .colCntBL(DEFAULT_COL_CNT_BL)
            .colCntPK(DEFAULT_COL_CNT_PK)
            .colCntFK(DEFAULT_COL_CNT_FK)
            .colCntIX(DEFAULT_COL_CNT_IX)
            .creationDate(DEFAULT_CREATION_DATE)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return tablesDefinition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TablesDefinition createUpdatedEntity(EntityManager em) {
        TablesDefinition tablesDefinition = new TablesDefinition()
            .tableName(UPDATED_TABLE_NAME)
            .value(UPDATED_VALUE)
            .symbolSize(UPDATED_SYMBOL_SIZE)
            .category(UPDATED_CATEGORY)
            .colCnt(UPDATED_COL_CNT)
            .colCntNbr(UPDATED_COL_CNT_NBR)
            .colCntTB(UPDATED_COL_CNT_TB)
            .colCntSTR(UPDATED_COL_CNT_STR)
            .colCntBL(UPDATED_COL_CNT_BL)
            .colCntPK(UPDATED_COL_CNT_PK)
            .colCntFK(UPDATED_COL_CNT_FK)
            .colCntIX(UPDATED_COL_CNT_IX)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return tablesDefinition;
    }

    @BeforeEach
    public void initTest() {
        tablesDefinition = createEntity(em);
    }

    @Test
    @Transactional
    void createTablesDefinition() throws Exception {
        int databaseSizeBeforeCreate = tablesDefinitionRepository.findAll().size();
        // Create the TablesDefinition
        TablesDefinitionDTO tablesDefinitionDTO = tablesDefinitionMapper.toDto(tablesDefinition);
        restTablesDefinitionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tablesDefinitionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TablesDefinition in the database
        List<TablesDefinition> tablesDefinitionList = tablesDefinitionRepository.findAll();
        assertThat(tablesDefinitionList).hasSize(databaseSizeBeforeCreate + 1);
        TablesDefinition testTablesDefinition = tablesDefinitionList.get(tablesDefinitionList.size() - 1);
        assertThat(testTablesDefinition.getTableName()).isEqualTo(DEFAULT_TABLE_NAME);
        assertThat(testTablesDefinition.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testTablesDefinition.getSymbolSize()).isEqualTo(DEFAULT_SYMBOL_SIZE);
        assertThat(testTablesDefinition.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testTablesDefinition.getColCnt()).isEqualTo(DEFAULT_COL_CNT);
        assertThat(testTablesDefinition.getColCntNbr()).isEqualTo(DEFAULT_COL_CNT_NBR);
        assertThat(testTablesDefinition.getColCntTB()).isEqualTo(DEFAULT_COL_CNT_TB);
        assertThat(testTablesDefinition.getColCntSTR()).isEqualTo(DEFAULT_COL_CNT_STR);
        assertThat(testTablesDefinition.getColCntBL()).isEqualTo(DEFAULT_COL_CNT_BL);
        assertThat(testTablesDefinition.getColCntPK()).isEqualTo(DEFAULT_COL_CNT_PK);
        assertThat(testTablesDefinition.getColCntFK()).isEqualTo(DEFAULT_COL_CNT_FK);
        assertThat(testTablesDefinition.getColCntIX()).isEqualTo(DEFAULT_COL_CNT_IX);
        assertThat(testTablesDefinition.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testTablesDefinition.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void createTablesDefinitionWithExistingId() throws Exception {
        // Create the TablesDefinition with an existing ID
        tablesDefinition.setId(1L);
        TablesDefinitionDTO tablesDefinitionDTO = tablesDefinitionMapper.toDto(tablesDefinition);

        int databaseSizeBeforeCreate = tablesDefinitionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTablesDefinitionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tablesDefinitionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TablesDefinition in the database
        List<TablesDefinition> tablesDefinitionList = tablesDefinitionRepository.findAll();
        assertThat(tablesDefinitionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTableNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = tablesDefinitionRepository.findAll().size();
        // set the field null
        tablesDefinition.setTableName(null);

        // Create the TablesDefinition, which fails.
        TablesDefinitionDTO tablesDefinitionDTO = tablesDefinitionMapper.toDto(tablesDefinition);

        restTablesDefinitionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tablesDefinitionDTO))
            )
            .andExpect(status().isBadRequest());

        List<TablesDefinition> tablesDefinitionList = tablesDefinitionRepository.findAll();
        assertThat(tablesDefinitionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTablesDefinitions() throws Exception {
        // Initialize the database
        tablesDefinitionRepository.saveAndFlush(tablesDefinition);

        // Get all the tablesDefinitionList
        restTablesDefinitionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tablesDefinition.getId().intValue())))
            .andExpect(jsonPath("$.[*].tableName").value(hasItem(DEFAULT_TABLE_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].symbolSize").value(hasItem(DEFAULT_SYMBOL_SIZE)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].colCnt").value(hasItem(DEFAULT_COL_CNT)))
            .andExpect(jsonPath("$.[*].colCntNbr").value(hasItem(DEFAULT_COL_CNT_NBR)))
            .andExpect(jsonPath("$.[*].colCntTB").value(hasItem(DEFAULT_COL_CNT_TB)))
            .andExpect(jsonPath("$.[*].colCntSTR").value(hasItem(DEFAULT_COL_CNT_STR)))
            .andExpect(jsonPath("$.[*].colCntBL").value(hasItem(DEFAULT_COL_CNT_BL)))
            .andExpect(jsonPath("$.[*].colCntPK").value(hasItem(DEFAULT_COL_CNT_PK)))
            .andExpect(jsonPath("$.[*].colCntFK").value(hasItem(DEFAULT_COL_CNT_FK)))
            .andExpect(jsonPath("$.[*].colCntIX").value(hasItem(DEFAULT_COL_CNT_IX)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getTablesDefinition() throws Exception {
        // Initialize the database
        tablesDefinitionRepository.saveAndFlush(tablesDefinition);

        // Get the tablesDefinition
        restTablesDefinitionMockMvc
            .perform(get(ENTITY_API_URL_ID, tablesDefinition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tablesDefinition.getId().intValue()))
            .andExpect(jsonPath("$.tableName").value(DEFAULT_TABLE_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.symbolSize").value(DEFAULT_SYMBOL_SIZE))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.colCnt").value(DEFAULT_COL_CNT))
            .andExpect(jsonPath("$.colCntNbr").value(DEFAULT_COL_CNT_NBR))
            .andExpect(jsonPath("$.colCntTB").value(DEFAULT_COL_CNT_TB))
            .andExpect(jsonPath("$.colCntSTR").value(DEFAULT_COL_CNT_STR))
            .andExpect(jsonPath("$.colCntBL").value(DEFAULT_COL_CNT_BL))
            .andExpect(jsonPath("$.colCntPK").value(DEFAULT_COL_CNT_PK))
            .andExpect(jsonPath("$.colCntFK").value(DEFAULT_COL_CNT_FK))
            .andExpect(jsonPath("$.colCntIX").value(DEFAULT_COL_CNT_IX))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTablesDefinition() throws Exception {
        // Get the tablesDefinition
        restTablesDefinitionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTablesDefinition() throws Exception {
        // Initialize the database
        tablesDefinitionRepository.saveAndFlush(tablesDefinition);

        int databaseSizeBeforeUpdate = tablesDefinitionRepository.findAll().size();

        // Update the tablesDefinition
        TablesDefinition updatedTablesDefinition = tablesDefinitionRepository.findById(tablesDefinition.getId()).get();
        // Disconnect from session so that the updates on updatedTablesDefinition are not directly saved in db
        em.detach(updatedTablesDefinition);
        updatedTablesDefinition
            .tableName(UPDATED_TABLE_NAME)
            .value(UPDATED_VALUE)
            .symbolSize(UPDATED_SYMBOL_SIZE)
            .category(UPDATED_CATEGORY)
            .colCnt(UPDATED_COL_CNT)
            .colCntNbr(UPDATED_COL_CNT_NBR)
            .colCntTB(UPDATED_COL_CNT_TB)
            .colCntSTR(UPDATED_COL_CNT_STR)
            .colCntBL(UPDATED_COL_CNT_BL)
            .colCntPK(UPDATED_COL_CNT_PK)
            .colCntFK(UPDATED_COL_CNT_FK)
            .colCntIX(UPDATED_COL_CNT_IX)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        TablesDefinitionDTO tablesDefinitionDTO = tablesDefinitionMapper.toDto(updatedTablesDefinition);

        restTablesDefinitionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tablesDefinitionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tablesDefinitionDTO))
            )
            .andExpect(status().isOk());

        // Validate the TablesDefinition in the database
        List<TablesDefinition> tablesDefinitionList = tablesDefinitionRepository.findAll();
        assertThat(tablesDefinitionList).hasSize(databaseSizeBeforeUpdate);
        TablesDefinition testTablesDefinition = tablesDefinitionList.get(tablesDefinitionList.size() - 1);
        assertThat(testTablesDefinition.getTableName()).isEqualTo(UPDATED_TABLE_NAME);
        assertThat(testTablesDefinition.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTablesDefinition.getSymbolSize()).isEqualTo(UPDATED_SYMBOL_SIZE);
        assertThat(testTablesDefinition.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testTablesDefinition.getColCnt()).isEqualTo(UPDATED_COL_CNT);
        assertThat(testTablesDefinition.getColCntNbr()).isEqualTo(UPDATED_COL_CNT_NBR);
        assertThat(testTablesDefinition.getColCntTB()).isEqualTo(UPDATED_COL_CNT_TB);
        assertThat(testTablesDefinition.getColCntSTR()).isEqualTo(UPDATED_COL_CNT_STR);
        assertThat(testTablesDefinition.getColCntBL()).isEqualTo(UPDATED_COL_CNT_BL);
        assertThat(testTablesDefinition.getColCntPK()).isEqualTo(UPDATED_COL_CNT_PK);
        assertThat(testTablesDefinition.getColCntFK()).isEqualTo(UPDATED_COL_CNT_FK);
        assertThat(testTablesDefinition.getColCntIX()).isEqualTo(UPDATED_COL_CNT_IX);
        assertThat(testTablesDefinition.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testTablesDefinition.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingTablesDefinition() throws Exception {
        int databaseSizeBeforeUpdate = tablesDefinitionRepository.findAll().size();
        tablesDefinition.setId(count.incrementAndGet());

        // Create the TablesDefinition
        TablesDefinitionDTO tablesDefinitionDTO = tablesDefinitionMapper.toDto(tablesDefinition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTablesDefinitionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tablesDefinitionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tablesDefinitionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TablesDefinition in the database
        List<TablesDefinition> tablesDefinitionList = tablesDefinitionRepository.findAll();
        assertThat(tablesDefinitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTablesDefinition() throws Exception {
        int databaseSizeBeforeUpdate = tablesDefinitionRepository.findAll().size();
        tablesDefinition.setId(count.incrementAndGet());

        // Create the TablesDefinition
        TablesDefinitionDTO tablesDefinitionDTO = tablesDefinitionMapper.toDto(tablesDefinition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTablesDefinitionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tablesDefinitionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TablesDefinition in the database
        List<TablesDefinition> tablesDefinitionList = tablesDefinitionRepository.findAll();
        assertThat(tablesDefinitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTablesDefinition() throws Exception {
        int databaseSizeBeforeUpdate = tablesDefinitionRepository.findAll().size();
        tablesDefinition.setId(count.incrementAndGet());

        // Create the TablesDefinition
        TablesDefinitionDTO tablesDefinitionDTO = tablesDefinitionMapper.toDto(tablesDefinition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTablesDefinitionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tablesDefinitionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TablesDefinition in the database
        List<TablesDefinition> tablesDefinitionList = tablesDefinitionRepository.findAll();
        assertThat(tablesDefinitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTablesDefinitionWithPatch() throws Exception {
        // Initialize the database
        tablesDefinitionRepository.saveAndFlush(tablesDefinition);

        int databaseSizeBeforeUpdate = tablesDefinitionRepository.findAll().size();

        // Update the tablesDefinition using partial update
        TablesDefinition partialUpdatedTablesDefinition = new TablesDefinition();
        partialUpdatedTablesDefinition.setId(tablesDefinition.getId());

        partialUpdatedTablesDefinition
            .tableName(UPDATED_TABLE_NAME)
            .symbolSize(UPDATED_SYMBOL_SIZE)
            .category(UPDATED_CATEGORY)
            .colCntNbr(UPDATED_COL_CNT_NBR)
            .colCntTB(UPDATED_COL_CNT_TB)
            .colCntFK(UPDATED_COL_CNT_FK)
            .creationDate(UPDATED_CREATION_DATE);

        restTablesDefinitionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTablesDefinition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTablesDefinition))
            )
            .andExpect(status().isOk());

        // Validate the TablesDefinition in the database
        List<TablesDefinition> tablesDefinitionList = tablesDefinitionRepository.findAll();
        assertThat(tablesDefinitionList).hasSize(databaseSizeBeforeUpdate);
        TablesDefinition testTablesDefinition = tablesDefinitionList.get(tablesDefinitionList.size() - 1);
        assertThat(testTablesDefinition.getTableName()).isEqualTo(UPDATED_TABLE_NAME);
        assertThat(testTablesDefinition.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testTablesDefinition.getSymbolSize()).isEqualTo(UPDATED_SYMBOL_SIZE);
        assertThat(testTablesDefinition.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testTablesDefinition.getColCnt()).isEqualTo(DEFAULT_COL_CNT);
        assertThat(testTablesDefinition.getColCntNbr()).isEqualTo(UPDATED_COL_CNT_NBR);
        assertThat(testTablesDefinition.getColCntTB()).isEqualTo(UPDATED_COL_CNT_TB);
        assertThat(testTablesDefinition.getColCntSTR()).isEqualTo(DEFAULT_COL_CNT_STR);
        assertThat(testTablesDefinition.getColCntBL()).isEqualTo(DEFAULT_COL_CNT_BL);
        assertThat(testTablesDefinition.getColCntPK()).isEqualTo(DEFAULT_COL_CNT_PK);
        assertThat(testTablesDefinition.getColCntFK()).isEqualTo(UPDATED_COL_CNT_FK);
        assertThat(testTablesDefinition.getColCntIX()).isEqualTo(DEFAULT_COL_CNT_IX);
        assertThat(testTablesDefinition.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testTablesDefinition.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateTablesDefinitionWithPatch() throws Exception {
        // Initialize the database
        tablesDefinitionRepository.saveAndFlush(tablesDefinition);

        int databaseSizeBeforeUpdate = tablesDefinitionRepository.findAll().size();

        // Update the tablesDefinition using partial update
        TablesDefinition partialUpdatedTablesDefinition = new TablesDefinition();
        partialUpdatedTablesDefinition.setId(tablesDefinition.getId());

        partialUpdatedTablesDefinition
            .tableName(UPDATED_TABLE_NAME)
            .value(UPDATED_VALUE)
            .symbolSize(UPDATED_SYMBOL_SIZE)
            .category(UPDATED_CATEGORY)
            .colCnt(UPDATED_COL_CNT)
            .colCntNbr(UPDATED_COL_CNT_NBR)
            .colCntTB(UPDATED_COL_CNT_TB)
            .colCntSTR(UPDATED_COL_CNT_STR)
            .colCntBL(UPDATED_COL_CNT_BL)
            .colCntPK(UPDATED_COL_CNT_PK)
            .colCntFK(UPDATED_COL_CNT_FK)
            .colCntIX(UPDATED_COL_CNT_IX)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restTablesDefinitionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTablesDefinition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTablesDefinition))
            )
            .andExpect(status().isOk());

        // Validate the TablesDefinition in the database
        List<TablesDefinition> tablesDefinitionList = tablesDefinitionRepository.findAll();
        assertThat(tablesDefinitionList).hasSize(databaseSizeBeforeUpdate);
        TablesDefinition testTablesDefinition = tablesDefinitionList.get(tablesDefinitionList.size() - 1);
        assertThat(testTablesDefinition.getTableName()).isEqualTo(UPDATED_TABLE_NAME);
        assertThat(testTablesDefinition.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testTablesDefinition.getSymbolSize()).isEqualTo(UPDATED_SYMBOL_SIZE);
        assertThat(testTablesDefinition.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testTablesDefinition.getColCnt()).isEqualTo(UPDATED_COL_CNT);
        assertThat(testTablesDefinition.getColCntNbr()).isEqualTo(UPDATED_COL_CNT_NBR);
        assertThat(testTablesDefinition.getColCntTB()).isEqualTo(UPDATED_COL_CNT_TB);
        assertThat(testTablesDefinition.getColCntSTR()).isEqualTo(UPDATED_COL_CNT_STR);
        assertThat(testTablesDefinition.getColCntBL()).isEqualTo(UPDATED_COL_CNT_BL);
        assertThat(testTablesDefinition.getColCntPK()).isEqualTo(UPDATED_COL_CNT_PK);
        assertThat(testTablesDefinition.getColCntFK()).isEqualTo(UPDATED_COL_CNT_FK);
        assertThat(testTablesDefinition.getColCntIX()).isEqualTo(UPDATED_COL_CNT_IX);
        assertThat(testTablesDefinition.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testTablesDefinition.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingTablesDefinition() throws Exception {
        int databaseSizeBeforeUpdate = tablesDefinitionRepository.findAll().size();
        tablesDefinition.setId(count.incrementAndGet());

        // Create the TablesDefinition
        TablesDefinitionDTO tablesDefinitionDTO = tablesDefinitionMapper.toDto(tablesDefinition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTablesDefinitionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tablesDefinitionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tablesDefinitionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TablesDefinition in the database
        List<TablesDefinition> tablesDefinitionList = tablesDefinitionRepository.findAll();
        assertThat(tablesDefinitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTablesDefinition() throws Exception {
        int databaseSizeBeforeUpdate = tablesDefinitionRepository.findAll().size();
        tablesDefinition.setId(count.incrementAndGet());

        // Create the TablesDefinition
        TablesDefinitionDTO tablesDefinitionDTO = tablesDefinitionMapper.toDto(tablesDefinition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTablesDefinitionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tablesDefinitionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TablesDefinition in the database
        List<TablesDefinition> tablesDefinitionList = tablesDefinitionRepository.findAll();
        assertThat(tablesDefinitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTablesDefinition() throws Exception {
        int databaseSizeBeforeUpdate = tablesDefinitionRepository.findAll().size();
        tablesDefinition.setId(count.incrementAndGet());

        // Create the TablesDefinition
        TablesDefinitionDTO tablesDefinitionDTO = tablesDefinitionMapper.toDto(tablesDefinition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTablesDefinitionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tablesDefinitionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TablesDefinition in the database
        List<TablesDefinition> tablesDefinitionList = tablesDefinitionRepository.findAll();
        assertThat(tablesDefinitionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTablesDefinition() throws Exception {
        // Initialize the database
        tablesDefinitionRepository.saveAndFlush(tablesDefinition);

        int databaseSizeBeforeDelete = tablesDefinitionRepository.findAll().size();

        // Delete the tablesDefinition
        restTablesDefinitionMockMvc
            .perform(delete(ENTITY_API_URL_ID, tablesDefinition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TablesDefinition> tablesDefinitionList = tablesDefinitionRepository.findAll();
        assertThat(tablesDefinitionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
