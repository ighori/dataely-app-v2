package com.dataely.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dataely.app.IntegrationTest;
import com.dataely.app.domain.FileSource;
import com.dataely.app.domain.enumeration.EdsType;
import com.dataely.app.repository.FileSourceRepository;
import com.dataely.app.service.dto.FileSourceDTO;
import com.dataely.app.service.mapper.FileSourceMapper;
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
 * Integration tests for the {@link FileSourceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FileSourceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

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

    private static final String DEFAULT_CONNECTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONNECTION_TYPE = "BBBBBBBBBB";

    private static final EdsType DEFAULT_TYPE = EdsType.JDBC;
    private static final EdsType UPDATED_TYPE = EdsType.JSON;

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IGNORE_DOTTED_FILES = false;
    private static final Boolean UPDATED_IGNORE_DOTTED_FILES = true;

    private static final Boolean DEFAULT_RECURSE = false;
    private static final Boolean UPDATED_RECURSE = true;

    private static final String DEFAULT_PATH_FILTER_REGEX = "AAAAAAAAAA";
    private static final String UPDATED_PATH_FILTER_REGEX = "BBBBBBBBBB";

    private static final String DEFAULT_REMOTE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_REMOTE_PATH = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/file-sources";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FileSourceRepository fileSourceRepository;

    @Autowired
    private FileSourceMapper fileSourceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFileSourceMockMvc;

    private FileSource fileSource;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileSource createEntity(EntityManager em) {
        FileSource fileSource = new FileSource()
            .name(DEFAULT_NAME)
            .detail(DEFAULT_DETAIL)
            .hostname(DEFAULT_HOSTNAME)
            .port(DEFAULT_PORT)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .icon(DEFAULT_ICON)
            .connectionType(DEFAULT_CONNECTION_TYPE)
            .type(DEFAULT_TYPE)
            .region(DEFAULT_REGION)
            .ignoreDottedFiles(DEFAULT_IGNORE_DOTTED_FILES)
            .recurse(DEFAULT_RECURSE)
            .pathFilterRegex(DEFAULT_PATH_FILTER_REGEX)
            .remotePath(DEFAULT_REMOTE_PATH)
            .creationDate(DEFAULT_CREATION_DATE)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return fileSource;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileSource createUpdatedEntity(EntityManager em) {
        FileSource fileSource = new FileSource()
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .hostname(UPDATED_HOSTNAME)
            .port(UPDATED_PORT)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .icon(UPDATED_ICON)
            .connectionType(UPDATED_CONNECTION_TYPE)
            .type(UPDATED_TYPE)
            .region(UPDATED_REGION)
            .ignoreDottedFiles(UPDATED_IGNORE_DOTTED_FILES)
            .recurse(UPDATED_RECURSE)
            .pathFilterRegex(UPDATED_PATH_FILTER_REGEX)
            .remotePath(UPDATED_REMOTE_PATH)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return fileSource;
    }

    @BeforeEach
    public void initTest() {
        fileSource = createEntity(em);
    }

    @Test
    @Transactional
    void createFileSource() throws Exception {
        int databaseSizeBeforeCreate = fileSourceRepository.findAll().size();
        // Create the FileSource
        FileSourceDTO fileSourceDTO = fileSourceMapper.toDto(fileSource);
        restFileSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileSourceDTO)))
            .andExpect(status().isCreated());

        // Validate the FileSource in the database
        List<FileSource> fileSourceList = fileSourceRepository.findAll();
        assertThat(fileSourceList).hasSize(databaseSizeBeforeCreate + 1);
        FileSource testFileSource = fileSourceList.get(fileSourceList.size() - 1);
        assertThat(testFileSource.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFileSource.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testFileSource.getHostname()).isEqualTo(DEFAULT_HOSTNAME);
        assertThat(testFileSource.getPort()).isEqualTo(DEFAULT_PORT);
        assertThat(testFileSource.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testFileSource.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testFileSource.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testFileSource.getConnectionType()).isEqualTo(DEFAULT_CONNECTION_TYPE);
        assertThat(testFileSource.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFileSource.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testFileSource.getIgnoreDottedFiles()).isEqualTo(DEFAULT_IGNORE_DOTTED_FILES);
        assertThat(testFileSource.getRecurse()).isEqualTo(DEFAULT_RECURSE);
        assertThat(testFileSource.getPathFilterRegex()).isEqualTo(DEFAULT_PATH_FILTER_REGEX);
        assertThat(testFileSource.getRemotePath()).isEqualTo(DEFAULT_REMOTE_PATH);
        assertThat(testFileSource.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testFileSource.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void createFileSourceWithExistingId() throws Exception {
        // Create the FileSource with an existing ID
        fileSource.setId(1L);
        FileSourceDTO fileSourceDTO = fileSourceMapper.toDto(fileSource);

        int databaseSizeBeforeCreate = fileSourceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileSourceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FileSource in the database
        List<FileSource> fileSourceList = fileSourceRepository.findAll();
        assertThat(fileSourceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileSourceRepository.findAll().size();
        // set the field null
        fileSource.setName(null);

        // Create the FileSource, which fails.
        FileSourceDTO fileSourceDTO = fileSourceMapper.toDto(fileSource);

        restFileSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileSourceDTO)))
            .andExpect(status().isBadRequest());

        List<FileSource> fileSourceList = fileSourceRepository.findAll();
        assertThat(fileSourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHostnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileSourceRepository.findAll().size();
        // set the field null
        fileSource.setHostname(null);

        // Create the FileSource, which fails.
        FileSourceDTO fileSourceDTO = fileSourceMapper.toDto(fileSource);

        restFileSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileSourceDTO)))
            .andExpect(status().isBadRequest());

        List<FileSource> fileSourceList = fileSourceRepository.findAll();
        assertThat(fileSourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPortIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileSourceRepository.findAll().size();
        // set the field null
        fileSource.setPort(null);

        // Create the FileSource, which fails.
        FileSourceDTO fileSourceDTO = fileSourceMapper.toDto(fileSource);

        restFileSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileSourceDTO)))
            .andExpect(status().isBadRequest());

        List<FileSource> fileSourceList = fileSourceRepository.findAll();
        assertThat(fileSourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileSourceRepository.findAll().size();
        // set the field null
        fileSource.setUsername(null);

        // Create the FileSource, which fails.
        FileSourceDTO fileSourceDTO = fileSourceMapper.toDto(fileSource);

        restFileSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileSourceDTO)))
            .andExpect(status().isBadRequest());

        List<FileSource> fileSourceList = fileSourceRepository.findAll();
        assertThat(fileSourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileSourceRepository.findAll().size();
        // set the field null
        fileSource.setPassword(null);

        // Create the FileSource, which fails.
        FileSourceDTO fileSourceDTO = fileSourceMapper.toDto(fileSource);

        restFileSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileSourceDTO)))
            .andExpect(status().isBadRequest());

        List<FileSource> fileSourceList = fileSourceRepository.findAll();
        assertThat(fileSourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFileSources() throws Exception {
        // Initialize the database
        fileSourceRepository.saveAndFlush(fileSource);

        // Get all the fileSourceList
        restFileSourceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileSource.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].hostname").value(hasItem(DEFAULT_HOSTNAME)))
            .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].connectionType").value(hasItem(DEFAULT_CONNECTION_TYPE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].ignoreDottedFiles").value(hasItem(DEFAULT_IGNORE_DOTTED_FILES.booleanValue())))
            .andExpect(jsonPath("$.[*].recurse").value(hasItem(DEFAULT_RECURSE.booleanValue())))
            .andExpect(jsonPath("$.[*].pathFilterRegex").value(hasItem(DEFAULT_PATH_FILTER_REGEX)))
            .andExpect(jsonPath("$.[*].remotePath").value(hasItem(DEFAULT_REMOTE_PATH)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getFileSource() throws Exception {
        // Initialize the database
        fileSourceRepository.saveAndFlush(fileSource);

        // Get the fileSource
        restFileSourceMockMvc
            .perform(get(ENTITY_API_URL_ID, fileSource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fileSource.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL))
            .andExpect(jsonPath("$.hostname").value(DEFAULT_HOSTNAME))
            .andExpect(jsonPath("$.port").value(DEFAULT_PORT))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.connectionType").value(DEFAULT_CONNECTION_TYPE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION))
            .andExpect(jsonPath("$.ignoreDottedFiles").value(DEFAULT_IGNORE_DOTTED_FILES.booleanValue()))
            .andExpect(jsonPath("$.recurse").value(DEFAULT_RECURSE.booleanValue()))
            .andExpect(jsonPath("$.pathFilterRegex").value(DEFAULT_PATH_FILTER_REGEX))
            .andExpect(jsonPath("$.remotePath").value(DEFAULT_REMOTE_PATH))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFileSource() throws Exception {
        // Get the fileSource
        restFileSourceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFileSource() throws Exception {
        // Initialize the database
        fileSourceRepository.saveAndFlush(fileSource);

        int databaseSizeBeforeUpdate = fileSourceRepository.findAll().size();

        // Update the fileSource
        FileSource updatedFileSource = fileSourceRepository.findById(fileSource.getId()).get();
        // Disconnect from session so that the updates on updatedFileSource are not directly saved in db
        em.detach(updatedFileSource);
        updatedFileSource
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .hostname(UPDATED_HOSTNAME)
            .port(UPDATED_PORT)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .icon(UPDATED_ICON)
            .connectionType(UPDATED_CONNECTION_TYPE)
            .type(UPDATED_TYPE)
            .region(UPDATED_REGION)
            .ignoreDottedFiles(UPDATED_IGNORE_DOTTED_FILES)
            .recurse(UPDATED_RECURSE)
            .pathFilterRegex(UPDATED_PATH_FILTER_REGEX)
            .remotePath(UPDATED_REMOTE_PATH)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        FileSourceDTO fileSourceDTO = fileSourceMapper.toDto(updatedFileSource);

        restFileSourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fileSourceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileSourceDTO))
            )
            .andExpect(status().isOk());

        // Validate the FileSource in the database
        List<FileSource> fileSourceList = fileSourceRepository.findAll();
        assertThat(fileSourceList).hasSize(databaseSizeBeforeUpdate);
        FileSource testFileSource = fileSourceList.get(fileSourceList.size() - 1);
        assertThat(testFileSource.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFileSource.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testFileSource.getHostname()).isEqualTo(UPDATED_HOSTNAME);
        assertThat(testFileSource.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testFileSource.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testFileSource.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testFileSource.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testFileSource.getConnectionType()).isEqualTo(UPDATED_CONNECTION_TYPE);
        assertThat(testFileSource.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFileSource.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testFileSource.getIgnoreDottedFiles()).isEqualTo(UPDATED_IGNORE_DOTTED_FILES);
        assertThat(testFileSource.getRecurse()).isEqualTo(UPDATED_RECURSE);
        assertThat(testFileSource.getPathFilterRegex()).isEqualTo(UPDATED_PATH_FILTER_REGEX);
        assertThat(testFileSource.getRemotePath()).isEqualTo(UPDATED_REMOTE_PATH);
        assertThat(testFileSource.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testFileSource.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingFileSource() throws Exception {
        int databaseSizeBeforeUpdate = fileSourceRepository.findAll().size();
        fileSource.setId(count.incrementAndGet());

        // Create the FileSource
        FileSourceDTO fileSourceDTO = fileSourceMapper.toDto(fileSource);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileSourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fileSourceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileSourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileSource in the database
        List<FileSource> fileSourceList = fileSourceRepository.findAll();
        assertThat(fileSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFileSource() throws Exception {
        int databaseSizeBeforeUpdate = fileSourceRepository.findAll().size();
        fileSource.setId(count.incrementAndGet());

        // Create the FileSource
        FileSourceDTO fileSourceDTO = fileSourceMapper.toDto(fileSource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileSourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileSourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileSource in the database
        List<FileSource> fileSourceList = fileSourceRepository.findAll();
        assertThat(fileSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFileSource() throws Exception {
        int databaseSizeBeforeUpdate = fileSourceRepository.findAll().size();
        fileSource.setId(count.incrementAndGet());

        // Create the FileSource
        FileSourceDTO fileSourceDTO = fileSourceMapper.toDto(fileSource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileSourceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileSourceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FileSource in the database
        List<FileSource> fileSourceList = fileSourceRepository.findAll();
        assertThat(fileSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFileSourceWithPatch() throws Exception {
        // Initialize the database
        fileSourceRepository.saveAndFlush(fileSource);

        int databaseSizeBeforeUpdate = fileSourceRepository.findAll().size();

        // Update the fileSource using partial update
        FileSource partialUpdatedFileSource = new FileSource();
        partialUpdatedFileSource.setId(fileSource.getId());

        partialUpdatedFileSource
            .name(UPDATED_NAME)
            .username(UPDATED_USERNAME)
            .icon(UPDATED_ICON)
            .connectionType(UPDATED_CONNECTION_TYPE)
            .pathFilterRegex(UPDATED_PATH_FILTER_REGEX)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restFileSourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFileSource.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFileSource))
            )
            .andExpect(status().isOk());

        // Validate the FileSource in the database
        List<FileSource> fileSourceList = fileSourceRepository.findAll();
        assertThat(fileSourceList).hasSize(databaseSizeBeforeUpdate);
        FileSource testFileSource = fileSourceList.get(fileSourceList.size() - 1);
        assertThat(testFileSource.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFileSource.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testFileSource.getHostname()).isEqualTo(DEFAULT_HOSTNAME);
        assertThat(testFileSource.getPort()).isEqualTo(DEFAULT_PORT);
        assertThat(testFileSource.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testFileSource.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testFileSource.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testFileSource.getConnectionType()).isEqualTo(UPDATED_CONNECTION_TYPE);
        assertThat(testFileSource.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testFileSource.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testFileSource.getIgnoreDottedFiles()).isEqualTo(DEFAULT_IGNORE_DOTTED_FILES);
        assertThat(testFileSource.getRecurse()).isEqualTo(DEFAULT_RECURSE);
        assertThat(testFileSource.getPathFilterRegex()).isEqualTo(UPDATED_PATH_FILTER_REGEX);
        assertThat(testFileSource.getRemotePath()).isEqualTo(DEFAULT_REMOTE_PATH);
        assertThat(testFileSource.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testFileSource.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateFileSourceWithPatch() throws Exception {
        // Initialize the database
        fileSourceRepository.saveAndFlush(fileSource);

        int databaseSizeBeforeUpdate = fileSourceRepository.findAll().size();

        // Update the fileSource using partial update
        FileSource partialUpdatedFileSource = new FileSource();
        partialUpdatedFileSource.setId(fileSource.getId());

        partialUpdatedFileSource
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .hostname(UPDATED_HOSTNAME)
            .port(UPDATED_PORT)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .icon(UPDATED_ICON)
            .connectionType(UPDATED_CONNECTION_TYPE)
            .type(UPDATED_TYPE)
            .region(UPDATED_REGION)
            .ignoreDottedFiles(UPDATED_IGNORE_DOTTED_FILES)
            .recurse(UPDATED_RECURSE)
            .pathFilterRegex(UPDATED_PATH_FILTER_REGEX)
            .remotePath(UPDATED_REMOTE_PATH)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restFileSourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFileSource.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFileSource))
            )
            .andExpect(status().isOk());

        // Validate the FileSource in the database
        List<FileSource> fileSourceList = fileSourceRepository.findAll();
        assertThat(fileSourceList).hasSize(databaseSizeBeforeUpdate);
        FileSource testFileSource = fileSourceList.get(fileSourceList.size() - 1);
        assertThat(testFileSource.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFileSource.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testFileSource.getHostname()).isEqualTo(UPDATED_HOSTNAME);
        assertThat(testFileSource.getPort()).isEqualTo(UPDATED_PORT);
        assertThat(testFileSource.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testFileSource.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testFileSource.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testFileSource.getConnectionType()).isEqualTo(UPDATED_CONNECTION_TYPE);
        assertThat(testFileSource.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testFileSource.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testFileSource.getIgnoreDottedFiles()).isEqualTo(UPDATED_IGNORE_DOTTED_FILES);
        assertThat(testFileSource.getRecurse()).isEqualTo(UPDATED_RECURSE);
        assertThat(testFileSource.getPathFilterRegex()).isEqualTo(UPDATED_PATH_FILTER_REGEX);
        assertThat(testFileSource.getRemotePath()).isEqualTo(UPDATED_REMOTE_PATH);
        assertThat(testFileSource.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testFileSource.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingFileSource() throws Exception {
        int databaseSizeBeforeUpdate = fileSourceRepository.findAll().size();
        fileSource.setId(count.incrementAndGet());

        // Create the FileSource
        FileSourceDTO fileSourceDTO = fileSourceMapper.toDto(fileSource);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileSourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fileSourceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fileSourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileSource in the database
        List<FileSource> fileSourceList = fileSourceRepository.findAll();
        assertThat(fileSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFileSource() throws Exception {
        int databaseSizeBeforeUpdate = fileSourceRepository.findAll().size();
        fileSource.setId(count.incrementAndGet());

        // Create the FileSource
        FileSourceDTO fileSourceDTO = fileSourceMapper.toDto(fileSource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileSourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fileSourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileSource in the database
        List<FileSource> fileSourceList = fileSourceRepository.findAll();
        assertThat(fileSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFileSource() throws Exception {
        int databaseSizeBeforeUpdate = fileSourceRepository.findAll().size();
        fileSource.setId(count.incrementAndGet());

        // Create the FileSource
        FileSourceDTO fileSourceDTO = fileSourceMapper.toDto(fileSource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileSourceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fileSourceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FileSource in the database
        List<FileSource> fileSourceList = fileSourceRepository.findAll();
        assertThat(fileSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFileSource() throws Exception {
        // Initialize the database
        fileSourceRepository.saveAndFlush(fileSource);

        int databaseSizeBeforeDelete = fileSourceRepository.findAll().size();

        // Delete the fileSource
        restFileSourceMockMvc
            .perform(delete(ENTITY_API_URL_ID, fileSource.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FileSource> fileSourceList = fileSourceRepository.findAll();
        assertThat(fileSourceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
