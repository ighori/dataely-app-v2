package com.dataely.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dataely.app.IntegrationTest;
import com.dataely.app.domain.DataSource;
import com.dataely.app.domain.enumeration.EDbType;
import com.dataely.app.domain.enumeration.EdsType;
import com.dataely.app.repository.DataSourceRepository;
import com.dataely.app.service.dto.DataSourceDTO;
import com.dataely.app.service.mapper.DataSourceMapper;
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
 * Integration tests for the {@link DataSourceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DataSourceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DATABASE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DATABASE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INSTANCE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_INSTANCE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SCHEMA_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SCHEMA_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HOSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_HOSTNAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PORT = 1;
    private static final Integer UPDATED_PORT = 2;

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final EDbType DEFAULT_DB_TYPE = EDbType.ORACLE;
    private static final EDbType UPDATED_DB_TYPE = EDbType.MYSQL;

    private static final String DEFAULT_DB_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_DB_VERSION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SCHEMA_COUNT = 1;
    private static final Integer UPDATED_SCHEMA_COUNT = 2;

    private static final EdsType DEFAULT_DS_TYPE = EdsType.JDBC;
    private static final EdsType UPDATED_DS_TYPE = EdsType.JSON;

    private static final String DEFAULT_DRIVER_CLASS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DRIVER_CLASS_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_JDBC_URL = "AAAAAAAAAA";
    private static final String UPDATED_JDBC_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SID = "AAAAAAAAAA";
    private static final String UPDATED_SID = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/data-sources";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DataSourceRepository dataSourceRepository;

    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDataSourceMockMvc;

    private DataSource dataSource;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataSource createEntity(EntityManager em) {
        DataSource dataSource = new DataSource()
            .name(DEFAULT_NAME)
            .detail(DEFAULT_DETAIL)
            .databaseName(DEFAULT_DATABASE_NAME)
            .instanceName(DEFAULT_INSTANCE_NAME)
            .schemaName(DEFAULT_SCHEMA_NAME)
            .hostname(DEFAULT_HOSTNAME)
            .port(DEFAULT_PORT)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .icon(DEFAULT_ICON)
            .dbType(DEFAULT_DB_TYPE)
            .dbVersion(DEFAULT_DB_VERSION)
            .schemaCount(DEFAULT_SCHEMA_COUNT)
            .dsType(DEFAULT_DS_TYPE)
            .driverClassName(DEFAULT_DRIVER_CLASS_NAME)
            .jdbcUrl(DEFAULT_JDBC_URL)
            .sid(DEFAULT_SID)
            .creationDate(DEFAULT_CREATION_DATE)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return dataSource;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataSource createUpdatedEntity(EntityManager em) {
        DataSource dataSource = new DataSource()
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .databaseName(UPDATED_DATABASE_NAME)
            .instanceName(UPDATED_INSTANCE_NAME)
            .schemaName(UPDATED_SCHEMA_NAME)
            .hostname(UPDATED_HOSTNAME)
            .port(UPDATED_PORT)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .icon(UPDATED_ICON)
            .dbType(UPDATED_DB_TYPE)
            .dbVersion(UPDATED_DB_VERSION)
            .schemaCount(UPDATED_SCHEMA_COUNT)
            .dsType(UPDATED_DS_TYPE)
            .driverClassName(UPDATED_DRIVER_CLASS_NAME)
            .jdbcUrl(UPDATED_JDBC_URL)
            .sid(UPDATED_SID)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return dataSource;
    }

    @BeforeEach
    public void initTest() {
        dataSource = createEntity(em);
    }

    @Test
    @Transactional
    void createDataSource() throws Exception {
        int databaseSizeBeforeCreate = dataSourceRepository.findAll().size();
        // Create the DataSource
        DataSourceDTO dataSourceDTO = dataSourceMapper.toDto(dataSource);
        restDataSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataSourceDTO)))
            .andExpect(status().isCreated());

        // Validate the DataSource in the database
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeCreate + 1);
        DataSource testDataSource = dataSourceList.get(dataSourceList.size() - 1);
        assertThat(testDataSource.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataSource.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testDataSource.getDatabaseName()).isEqualTo(DEFAULT_DATABASE_NAME);
        assertThat(testDataSource.getInstanceName()).isEqualTo(DEFAULT_INSTANCE_NAME);
        assertThat(testDataSource.getSchemaName()).isEqualTo(DEFAULT_SCHEMA_NAME);
        assertThat(testDataSource.getHostname()).isEqualTo(DEFAULT_HOSTNAME);
        assertThat(testDataSource.getPort()).isEqualTo(DEFAULT_PORT);
        assertThat(testDataSource.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testDataSource.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testDataSource.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testDataSource.getDbType()).isEqualTo(DEFAULT_DB_TYPE);
        assertThat(testDataSource.getDbVersion()).isEqualTo(DEFAULT_DB_VERSION);
        assertThat(testDataSource.getSchemaCount()).isEqualTo(DEFAULT_SCHEMA_COUNT);
        assertThat(testDataSource.getDsType()).isEqualTo(DEFAULT_DS_TYPE);
        assertThat(testDataSource.getDriverClassName()).isEqualTo(DEFAULT_DRIVER_CLASS_NAME);
        assertThat(testDataSource.getJdbcUrl()).isEqualTo(DEFAULT_JDBC_URL);
        assertThat(testDataSource.getSid()).isEqualTo(DEFAULT_SID);
        assertThat(testDataSource.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testDataSource.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void createDataSourceWithExistingId() throws Exception {
        // Create the DataSource with an existing ID
        dataSource.setId(1L);
        DataSourceDTO dataSourceDTO = dataSourceMapper.toDto(dataSource);

        int databaseSizeBeforeCreate = dataSourceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataSourceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DataSource in the database
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataSourceRepository.findAll().size();
        // set the field null
        dataSource.setName(null);

        // Create the DataSource, which fails.
        DataSourceDTO dataSourceDTO = dataSourceMapper.toDto(dataSource);

        restDataSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataSourceDTO)))
            .andExpect(status().isBadRequest());

        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDatabaseNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataSourceRepository.findAll().size();
        // set the field null
        dataSource.setDatabaseName(null);

        // Create the DataSource, which fails.
        DataSourceDTO dataSourceDTO = dataSourceMapper.toDto(dataSource);

        restDataSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataSourceDTO)))
            .andExpect(status().isBadRequest());

        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHostnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataSourceRepository.findAll().size();
        // set the field null
        dataSource.setHostname(null);

        // Create the DataSource, which fails.
        DataSourceDTO dataSourceDTO = dataSourceMapper.toDto(dataSource);

        restDataSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataSourceDTO)))
            .andExpect(status().isBadRequest());

        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPortIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataSourceRepository.findAll().size();
        // set the field null
        dataSource.setPort(null);

        // Create the DataSource, which fails.
        DataSourceDTO dataSourceDTO = dataSourceMapper.toDto(dataSource);

        restDataSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataSourceDTO)))
            .andExpect(status().isBadRequest());

        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataSourceRepository.findAll().size();
        // set the field null
        dataSource.setUsername(null);

        // Create the DataSource, which fails.
        DataSourceDTO dataSourceDTO = dataSourceMapper.toDto(dataSource);

        restDataSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataSourceDTO)))
            .andExpect(status().isBadRequest());

        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataSourceRepository.findAll().size();
        // set the field null
        dataSource.setPassword(null);

        // Create the DataSource, which fails.
        DataSourceDTO dataSourceDTO = dataSourceMapper.toDto(dataSource);

        restDataSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataSourceDTO)))
            .andExpect(status().isBadRequest());

        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDbTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataSourceRepository.findAll().size();
        // set the field null
        dataSource.setDbType(null);

        // Create the DataSource, which fails.
        DataSourceDTO dataSourceDTO = dataSourceMapper.toDto(dataSource);

        restDataSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataSourceDTO)))
            .andExpect(status().isBadRequest());

        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDsTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dataSourceRepository.findAll().size();
        // set the field null
        dataSource.setDsType(null);

        // Create the DataSource, which fails.
        DataSourceDTO dataSourceDTO = dataSourceMapper.toDto(dataSource);

        restDataSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataSourceDTO)))
            .andExpect(status().isBadRequest());

        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDataSources() throws Exception {
        // Initialize the database
        dataSourceRepository.saveAndFlush(dataSource);

        // Get all the dataSourceList
        restDataSourceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataSource.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].databaseName").value(hasItem(DEFAULT_DATABASE_NAME)))
            .andExpect(jsonPath("$.[*].instanceName").value(hasItem(DEFAULT_INSTANCE_NAME)))
            .andExpect(jsonPath("$.[*].schemaName").value(hasItem(DEFAULT_SCHEMA_NAME)))
            .andExpect(jsonPath("$.[*].hostname").value(hasItem(DEFAULT_HOSTNAME)))
            .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].dbType").value(hasItem(DEFAULT_DB_TYPE.toString())))
            .andExpect(jsonPath("$.[*].dbVersion").value(hasItem(DEFAULT_DB_VERSION)))
            .andExpect(jsonPath("$.[*].schemaCount").value(hasItem(DEFAULT_SCHEMA_COUNT)))
            .andExpect(jsonPath("$.[*].dsType").value(hasItem(DEFAULT_DS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].driverClassName").value(hasItem(DEFAULT_DRIVER_CLASS_NAME)))
            .andExpect(jsonPath("$.[*].jdbcUrl").value(hasItem(DEFAULT_JDBC_URL)))
            .andExpect(jsonPath("$.[*].sid").value(hasItem(DEFAULT_SID)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getDataSource() throws Exception {
        // Initialize the database
        dataSourceRepository.saveAndFlush(dataSource);

        // Get the dataSource
        restDataSourceMockMvc
            .perform(get(ENTITY_API_URL_ID, dataSource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dataSource.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL))
            .andExpect(jsonPath("$.databaseName").value(DEFAULT_DATABASE_NAME))
            .andExpect(jsonPath("$.instanceName").value(DEFAULT_INSTANCE_NAME))
            .andExpect(jsonPath("$.schemaName").value(DEFAULT_SCHEMA_NAME))
            .andExpect(jsonPath("$.hostname").value(DEFAULT_HOSTNAME))
            .andExpect(jsonPath("$.port").value(DEFAULT_PORT))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.dbType").value(DEFAULT_DB_TYPE.toString()))
            .andExpect(jsonPath("$.dbVersion").value(DEFAULT_DB_VERSION))
            .andExpect(jsonPath("$.schemaCount").value(DEFAULT_SCHEMA_COUNT))
            .andExpect(jsonPath("$.dsType").value(DEFAULT_DS_TYPE.toString()))
            .andExpect(jsonPath("$.driverClassName").value(DEFAULT_DRIVER_CLASS_NAME))
            .andExpect(jsonPath("$.jdbcUrl").value(DEFAULT_JDBC_URL))
            .andExpect(jsonPath("$.sid").value(DEFAULT_SID))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDataSource() throws Exception {
        // Get the dataSource
        restDataSourceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDataSource() throws Exception {
        // Initialize the database
        dataSourceRepository.saveAndFlush(dataSource);

        int databaseSizeBeforeUpdate = dataSourceRepository.findAll().size();

        // Update the dataSource
        DataSource updatedDataSource = dataSourceRepository.findById(dataSource.getId()).get();
        // Disconnect from session so that the updates on updatedDataSource are not directly saved in db
        em.detach(updatedDataSource);
        updatedDataSource
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .databaseName(UPDATED_DATABASE_NAME)
            .instanceName(UPDATED_INSTANCE_NAME)
            .schemaName(UPDATED_SCHEMA_NAME)
            .hostname(UPDATED_HOSTNAME)
            .port(UPDATED_PORT)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .icon(UPDATED_ICON)
            .dbType(UPDATED_DB_TYPE)
            .dbVersion(UPDATED_DB_VERSION)
            .schemaCount(UPDATED_SCHEMA_COUNT)
            .dsType(UPDATED_DS_TYPE)
            .driverClassName(UPDATED_DRIVER_CLASS_NAME)
            .jdbcUrl(UPDATED_JDBC_URL)
            .sid(UPDATED_SID)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        DataSourceDTO dataSourceDTO = dataSourceMapper.toDto(updatedDataSource);

        restDataSourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dataSourceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataSourceDTO))
            )
            .andExpect(status().isOk());

        // Validate the DataSource in the database
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeUpdate);
        DataSource testDataSource = dataSourceList.get(dataSourceList.size() - 1);
        assertThat(testDataSource.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataSource.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testDataSource.getDatabaseName()).isEqualTo(UPDATED_DATABASE_NAME);
        assertThat(testDataSource.getInstanceName()).isEqualTo(UPDATED_INSTANCE_NAME);
        assertThat(testDataSource.getSchemaName()).isEqualTo(UPDATED_SCHEMA_NAME);
        assertThat(testDataSource.getHostname()).isEqualTo(UPDATED_HOSTNAME);
        assertThat(testDataSource.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testDataSource.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testDataSource.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testDataSource.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testDataSource.getDbType()).isEqualTo(UPDATED_DB_TYPE);
        assertThat(testDataSource.getDbVersion()).isEqualTo(UPDATED_DB_VERSION);
        assertThat(testDataSource.getSchemaCount()).isEqualTo(UPDATED_SCHEMA_COUNT);
        assertThat(testDataSource.getDsType()).isEqualTo(UPDATED_DS_TYPE);
        assertThat(testDataSource.getDriverClassName()).isEqualTo(UPDATED_DRIVER_CLASS_NAME);
        assertThat(testDataSource.getJdbcUrl()).isEqualTo(UPDATED_JDBC_URL);
        assertThat(testDataSource.getSid()).isEqualTo(UPDATED_SID);
        assertThat(testDataSource.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testDataSource.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingDataSource() throws Exception {
        int databaseSizeBeforeUpdate = dataSourceRepository.findAll().size();
        dataSource.setId(count.incrementAndGet());

        // Create the DataSource
        DataSourceDTO dataSourceDTO = dataSourceMapper.toDto(dataSource);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataSourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dataSourceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataSourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataSource in the database
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDataSource() throws Exception {
        int databaseSizeBeforeUpdate = dataSourceRepository.findAll().size();
        dataSource.setId(count.incrementAndGet());

        // Create the DataSource
        DataSourceDTO dataSourceDTO = dataSourceMapper.toDto(dataSource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataSourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataSourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataSource in the database
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDataSource() throws Exception {
        int databaseSizeBeforeUpdate = dataSourceRepository.findAll().size();
        dataSource.setId(count.incrementAndGet());

        // Create the DataSource
        DataSourceDTO dataSourceDTO = dataSourceMapper.toDto(dataSource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataSourceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataSourceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DataSource in the database
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDataSourceWithPatch() throws Exception {
        // Initialize the database
        dataSourceRepository.saveAndFlush(dataSource);

        int databaseSizeBeforeUpdate = dataSourceRepository.findAll().size();

        // Update the dataSource using partial update
        DataSource partialUpdatedDataSource = new DataSource();
        partialUpdatedDataSource.setId(dataSource.getId());

        partialUpdatedDataSource
            .instanceName(UPDATED_INSTANCE_NAME)
            .schemaName(UPDATED_SCHEMA_NAME)
            .port(UPDATED_PORT)
            .icon(UPDATED_ICON)
            .dbType(UPDATED_DB_TYPE)
            .dbVersion(UPDATED_DB_VERSION);

        restDataSourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataSource.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDataSource))
            )
            .andExpect(status().isOk());

        // Validate the DataSource in the database
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeUpdate);
        DataSource testDataSource = dataSourceList.get(dataSourceList.size() - 1);
        assertThat(testDataSource.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDataSource.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testDataSource.getDatabaseName()).isEqualTo(DEFAULT_DATABASE_NAME);
        assertThat(testDataSource.getInstanceName()).isEqualTo(UPDATED_INSTANCE_NAME);
        assertThat(testDataSource.getSchemaName()).isEqualTo(UPDATED_SCHEMA_NAME);
        assertThat(testDataSource.getHostname()).isEqualTo(DEFAULT_HOSTNAME);
        assertThat(testDataSource.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testDataSource.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testDataSource.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testDataSource.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testDataSource.getDbType()).isEqualTo(UPDATED_DB_TYPE);
        assertThat(testDataSource.getDbVersion()).isEqualTo(UPDATED_DB_VERSION);
        assertThat(testDataSource.getSchemaCount()).isEqualTo(DEFAULT_SCHEMA_COUNT);
        assertThat(testDataSource.getDsType()).isEqualTo(DEFAULT_DS_TYPE);
        assertThat(testDataSource.getDriverClassName()).isEqualTo(DEFAULT_DRIVER_CLASS_NAME);
        assertThat(testDataSource.getJdbcUrl()).isEqualTo(DEFAULT_JDBC_URL);
        assertThat(testDataSource.getSid()).isEqualTo(DEFAULT_SID);
        assertThat(testDataSource.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testDataSource.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateDataSourceWithPatch() throws Exception {
        // Initialize the database
        dataSourceRepository.saveAndFlush(dataSource);

        int databaseSizeBeforeUpdate = dataSourceRepository.findAll().size();

        // Update the dataSource using partial update
        DataSource partialUpdatedDataSource = new DataSource();
        partialUpdatedDataSource.setId(dataSource.getId());

        partialUpdatedDataSource
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .databaseName(UPDATED_DATABASE_NAME)
            .instanceName(UPDATED_INSTANCE_NAME)
            .schemaName(UPDATED_SCHEMA_NAME)
            .hostname(UPDATED_HOSTNAME)
            .port(UPDATED_PORT)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .icon(UPDATED_ICON)
            .dbType(UPDATED_DB_TYPE)
            .dbVersion(UPDATED_DB_VERSION)
            .schemaCount(UPDATED_SCHEMA_COUNT)
            .dsType(UPDATED_DS_TYPE)
            .driverClassName(UPDATED_DRIVER_CLASS_NAME)
            .jdbcUrl(UPDATED_JDBC_URL)
            .sid(UPDATED_SID)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restDataSourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataSource.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDataSource))
            )
            .andExpect(status().isOk());

        // Validate the DataSource in the database
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeUpdate);
        DataSource testDataSource = dataSourceList.get(dataSourceList.size() - 1);
        assertThat(testDataSource.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDataSource.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testDataSource.getDatabaseName()).isEqualTo(UPDATED_DATABASE_NAME);
        assertThat(testDataSource.getInstanceName()).isEqualTo(UPDATED_INSTANCE_NAME);
        assertThat(testDataSource.getSchemaName()).isEqualTo(UPDATED_SCHEMA_NAME);
        assertThat(testDataSource.getHostname()).isEqualTo(UPDATED_HOSTNAME);
        assertThat(testDataSource.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testDataSource.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testDataSource.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testDataSource.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testDataSource.getDbType()).isEqualTo(UPDATED_DB_TYPE);
        assertThat(testDataSource.getDbVersion()).isEqualTo(UPDATED_DB_VERSION);
        assertThat(testDataSource.getSchemaCount()).isEqualTo(UPDATED_SCHEMA_COUNT);
        assertThat(testDataSource.getDsType()).isEqualTo(UPDATED_DS_TYPE);
        assertThat(testDataSource.getDriverClassName()).isEqualTo(UPDATED_DRIVER_CLASS_NAME);
        assertThat(testDataSource.getJdbcUrl()).isEqualTo(UPDATED_JDBC_URL);
        assertThat(testDataSource.getSid()).isEqualTo(UPDATED_SID);
        assertThat(testDataSource.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testDataSource.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingDataSource() throws Exception {
        int databaseSizeBeforeUpdate = dataSourceRepository.findAll().size();
        dataSource.setId(count.incrementAndGet());

        // Create the DataSource
        DataSourceDTO dataSourceDTO = dataSourceMapper.toDto(dataSource);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataSourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dataSourceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataSourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataSource in the database
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDataSource() throws Exception {
        int databaseSizeBeforeUpdate = dataSourceRepository.findAll().size();
        dataSource.setId(count.incrementAndGet());

        // Create the DataSource
        DataSourceDTO dataSourceDTO = dataSourceMapper.toDto(dataSource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataSourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataSourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataSource in the database
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDataSource() throws Exception {
        int databaseSizeBeforeUpdate = dataSourceRepository.findAll().size();
        dataSource.setId(count.incrementAndGet());

        // Create the DataSource
        DataSourceDTO dataSourceDTO = dataSourceMapper.toDto(dataSource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataSourceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dataSourceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DataSource in the database
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDataSource() throws Exception {
        // Initialize the database
        dataSourceRepository.saveAndFlush(dataSource);

        int databaseSizeBeforeDelete = dataSourceRepository.findAll().size();

        // Delete the dataSource
        restDataSourceMockMvc
            .perform(delete(ENTITY_API_URL_ID, dataSource.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DataSource> dataSourceList = dataSourceRepository.findAll();
        assertThat(dataSourceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
