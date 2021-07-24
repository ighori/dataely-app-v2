package com.dataely.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dataely.app.IntegrationTest;
import com.dataely.app.domain.FileConfig;
import com.dataely.app.repository.FileConfigRepository;
import com.dataely.app.service.dto.FileConfigDTO;
import com.dataely.app.service.mapper.FileConfigMapper;
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
 * Integration tests for the {@link FileConfigResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FileConfigResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_COLUMN_NAME_LINE_NUMBER = 1;
    private static final Integer UPDATED_COLUMN_NAME_LINE_NUMBER = 2;

    private static final String DEFAULT_ENCODING = "AAAAAAAAAA";
    private static final String UPDATED_ENCODING = "BBBBBBBBBB";

    private static final String DEFAULT_SEPARATOR_CHAR = "AAAAAAAAAA";
    private static final String UPDATED_SEPARATOR_CHAR = "BBBBBBBBBB";

    private static final String DEFAULT_QUOTE_CHAR = "AAAAAAAAAA";
    private static final String UPDATED_QUOTE_CHAR = "BBBBBBBBBB";

    private static final String DEFAULT_ESCAPE_CHAR = "AAAAAAAAAA";
    private static final String UPDATED_ESCAPE_CHAR = "BBBBBBBBBB";

    private static final Integer DEFAULT_FIXED_VALUE_WIDTH = 1;
    private static final Integer UPDATED_FIXED_VALUE_WIDTH = 2;

    private static final Boolean DEFAULT_SKIP_EMPTY_LINES = false;
    private static final Boolean UPDATED_SKIP_EMPTY_LINES = true;

    private static final Boolean DEFAULT_SKIP_EMPTY_COLUMNS = false;
    private static final Boolean UPDATED_SKIP_EMPTY_COLUMNS = true;

    private static final Boolean DEFAULT_FAIL_ON_INCONSISTENT_LINE_WIDTH = false;
    private static final Boolean UPDATED_FAIL_ON_INCONSISTENT_LINE_WIDTH = true;

    private static final Boolean DEFAULT_SKIP_EBCDIC_HEADER = false;
    private static final Boolean UPDATED_SKIP_EBCDIC_HEADER = true;

    private static final Boolean DEFAULT_EOL_PRESENT = false;
    private static final Boolean UPDATED_EOL_PRESENT = true;

    private static final String DEFAULT_FILE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FILE_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/file-configs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FileConfigRepository fileConfigRepository;

    @Autowired
    private FileConfigMapper fileConfigMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFileConfigMockMvc;

    private FileConfig fileConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileConfig createEntity(EntityManager em) {
        FileConfig fileConfig = new FileConfig()
            .name(DEFAULT_NAME)
            .detail(DEFAULT_DETAIL)
            .columnNameLineNumber(DEFAULT_COLUMN_NAME_LINE_NUMBER)
            .encoding(DEFAULT_ENCODING)
            .separatorChar(DEFAULT_SEPARATOR_CHAR)
            .quoteChar(DEFAULT_QUOTE_CHAR)
            .escapeChar(DEFAULT_ESCAPE_CHAR)
            .fixedValueWidth(DEFAULT_FIXED_VALUE_WIDTH)
            .skipEmptyLines(DEFAULT_SKIP_EMPTY_LINES)
            .skipEmptyColumns(DEFAULT_SKIP_EMPTY_COLUMNS)
            .failOnInconsistentLineWidth(DEFAULT_FAIL_ON_INCONSISTENT_LINE_WIDTH)
            .skipEbcdicHeader(DEFAULT_SKIP_EBCDIC_HEADER)
            .eolPresent(DEFAULT_EOL_PRESENT)
            .fileType(DEFAULT_FILE_TYPE)
            .creationDate(DEFAULT_CREATION_DATE)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return fileConfig;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileConfig createUpdatedEntity(EntityManager em) {
        FileConfig fileConfig = new FileConfig()
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .columnNameLineNumber(UPDATED_COLUMN_NAME_LINE_NUMBER)
            .encoding(UPDATED_ENCODING)
            .separatorChar(UPDATED_SEPARATOR_CHAR)
            .quoteChar(UPDATED_QUOTE_CHAR)
            .escapeChar(UPDATED_ESCAPE_CHAR)
            .fixedValueWidth(UPDATED_FIXED_VALUE_WIDTH)
            .skipEmptyLines(UPDATED_SKIP_EMPTY_LINES)
            .skipEmptyColumns(UPDATED_SKIP_EMPTY_COLUMNS)
            .failOnInconsistentLineWidth(UPDATED_FAIL_ON_INCONSISTENT_LINE_WIDTH)
            .skipEbcdicHeader(UPDATED_SKIP_EBCDIC_HEADER)
            .eolPresent(UPDATED_EOL_PRESENT)
            .fileType(UPDATED_FILE_TYPE)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return fileConfig;
    }

    @BeforeEach
    public void initTest() {
        fileConfig = createEntity(em);
    }

    @Test
    @Transactional
    void createFileConfig() throws Exception {
        int databaseSizeBeforeCreate = fileConfigRepository.findAll().size();
        // Create the FileConfig
        FileConfigDTO fileConfigDTO = fileConfigMapper.toDto(fileConfig);
        restFileConfigMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the FileConfig in the database
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeCreate + 1);
        FileConfig testFileConfig = fileConfigList.get(fileConfigList.size() - 1);
        assertThat(testFileConfig.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFileConfig.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testFileConfig.getColumnNameLineNumber()).isEqualTo(DEFAULT_COLUMN_NAME_LINE_NUMBER);
        assertThat(testFileConfig.getEncoding()).isEqualTo(DEFAULT_ENCODING);
        assertThat(testFileConfig.getSeparatorChar()).isEqualTo(DEFAULT_SEPARATOR_CHAR);
        assertThat(testFileConfig.getQuoteChar()).isEqualTo(DEFAULT_QUOTE_CHAR);
        assertThat(testFileConfig.getEscapeChar()).isEqualTo(DEFAULT_ESCAPE_CHAR);
        assertThat(testFileConfig.getFixedValueWidth()).isEqualTo(DEFAULT_FIXED_VALUE_WIDTH);
        assertThat(testFileConfig.getSkipEmptyLines()).isEqualTo(DEFAULT_SKIP_EMPTY_LINES);
        assertThat(testFileConfig.getSkipEmptyColumns()).isEqualTo(DEFAULT_SKIP_EMPTY_COLUMNS);
        assertThat(testFileConfig.getFailOnInconsistentLineWidth()).isEqualTo(DEFAULT_FAIL_ON_INCONSISTENT_LINE_WIDTH);
        assertThat(testFileConfig.getSkipEbcdicHeader()).isEqualTo(DEFAULT_SKIP_EBCDIC_HEADER);
        assertThat(testFileConfig.getEolPresent()).isEqualTo(DEFAULT_EOL_PRESENT);
        assertThat(testFileConfig.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testFileConfig.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testFileConfig.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void createFileConfigWithExistingId() throws Exception {
        // Create the FileConfig with an existing ID
        fileConfig.setId(1L);
        FileConfigDTO fileConfigDTO = fileConfigMapper.toDto(fileConfig);

        int databaseSizeBeforeCreate = fileConfigRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileConfigMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FileConfig in the database
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileConfigRepository.findAll().size();
        // set the field null
        fileConfig.setName(null);

        // Create the FileConfig, which fails.
        FileConfigDTO fileConfigDTO = fileConfigMapper.toDto(fileConfig);

        restFileConfigMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileConfigDTO)))
            .andExpect(status().isBadRequest());

        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFileConfigs() throws Exception {
        // Initialize the database
        fileConfigRepository.saveAndFlush(fileConfig);

        // Get all the fileConfigList
        restFileConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].columnNameLineNumber").value(hasItem(DEFAULT_COLUMN_NAME_LINE_NUMBER)))
            .andExpect(jsonPath("$.[*].encoding").value(hasItem(DEFAULT_ENCODING)))
            .andExpect(jsonPath("$.[*].separatorChar").value(hasItem(DEFAULT_SEPARATOR_CHAR)))
            .andExpect(jsonPath("$.[*].quoteChar").value(hasItem(DEFAULT_QUOTE_CHAR)))
            .andExpect(jsonPath("$.[*].escapeChar").value(hasItem(DEFAULT_ESCAPE_CHAR)))
            .andExpect(jsonPath("$.[*].fixedValueWidth").value(hasItem(DEFAULT_FIXED_VALUE_WIDTH)))
            .andExpect(jsonPath("$.[*].skipEmptyLines").value(hasItem(DEFAULT_SKIP_EMPTY_LINES.booleanValue())))
            .andExpect(jsonPath("$.[*].skipEmptyColumns").value(hasItem(DEFAULT_SKIP_EMPTY_COLUMNS.booleanValue())))
            .andExpect(jsonPath("$.[*].failOnInconsistentLineWidth").value(hasItem(DEFAULT_FAIL_ON_INCONSISTENT_LINE_WIDTH.booleanValue())))
            .andExpect(jsonPath("$.[*].skipEbcdicHeader").value(hasItem(DEFAULT_SKIP_EBCDIC_HEADER.booleanValue())))
            .andExpect(jsonPath("$.[*].eolPresent").value(hasItem(DEFAULT_EOL_PRESENT.booleanValue())))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getFileConfig() throws Exception {
        // Initialize the database
        fileConfigRepository.saveAndFlush(fileConfig);

        // Get the fileConfig
        restFileConfigMockMvc
            .perform(get(ENTITY_API_URL_ID, fileConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fileConfig.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL))
            .andExpect(jsonPath("$.columnNameLineNumber").value(DEFAULT_COLUMN_NAME_LINE_NUMBER))
            .andExpect(jsonPath("$.encoding").value(DEFAULT_ENCODING))
            .andExpect(jsonPath("$.separatorChar").value(DEFAULT_SEPARATOR_CHAR))
            .andExpect(jsonPath("$.quoteChar").value(DEFAULT_QUOTE_CHAR))
            .andExpect(jsonPath("$.escapeChar").value(DEFAULT_ESCAPE_CHAR))
            .andExpect(jsonPath("$.fixedValueWidth").value(DEFAULT_FIXED_VALUE_WIDTH))
            .andExpect(jsonPath("$.skipEmptyLines").value(DEFAULT_SKIP_EMPTY_LINES.booleanValue()))
            .andExpect(jsonPath("$.skipEmptyColumns").value(DEFAULT_SKIP_EMPTY_COLUMNS.booleanValue()))
            .andExpect(jsonPath("$.failOnInconsistentLineWidth").value(DEFAULT_FAIL_ON_INCONSISTENT_LINE_WIDTH.booleanValue()))
            .andExpect(jsonPath("$.skipEbcdicHeader").value(DEFAULT_SKIP_EBCDIC_HEADER.booleanValue()))
            .andExpect(jsonPath("$.eolPresent").value(DEFAULT_EOL_PRESENT.booleanValue()))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFileConfig() throws Exception {
        // Get the fileConfig
        restFileConfigMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFileConfig() throws Exception {
        // Initialize the database
        fileConfigRepository.saveAndFlush(fileConfig);

        int databaseSizeBeforeUpdate = fileConfigRepository.findAll().size();

        // Update the fileConfig
        FileConfig updatedFileConfig = fileConfigRepository.findById(fileConfig.getId()).get();
        // Disconnect from session so that the updates on updatedFileConfig are not directly saved in db
        em.detach(updatedFileConfig);
        updatedFileConfig
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .columnNameLineNumber(UPDATED_COLUMN_NAME_LINE_NUMBER)
            .encoding(UPDATED_ENCODING)
            .separatorChar(UPDATED_SEPARATOR_CHAR)
            .quoteChar(UPDATED_QUOTE_CHAR)
            .escapeChar(UPDATED_ESCAPE_CHAR)
            .fixedValueWidth(UPDATED_FIXED_VALUE_WIDTH)
            .skipEmptyLines(UPDATED_SKIP_EMPTY_LINES)
            .skipEmptyColumns(UPDATED_SKIP_EMPTY_COLUMNS)
            .failOnInconsistentLineWidth(UPDATED_FAIL_ON_INCONSISTENT_LINE_WIDTH)
            .skipEbcdicHeader(UPDATED_SKIP_EBCDIC_HEADER)
            .eolPresent(UPDATED_EOL_PRESENT)
            .fileType(UPDATED_FILE_TYPE)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        FileConfigDTO fileConfigDTO = fileConfigMapper.toDto(updatedFileConfig);

        restFileConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fileConfigDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileConfigDTO))
            )
            .andExpect(status().isOk());

        // Validate the FileConfig in the database
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeUpdate);
        FileConfig testFileConfig = fileConfigList.get(fileConfigList.size() - 1);
        assertThat(testFileConfig.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFileConfig.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testFileConfig.getColumnNameLineNumber()).isEqualTo(UPDATED_COLUMN_NAME_LINE_NUMBER);
        assertThat(testFileConfig.getEncoding()).isEqualTo(UPDATED_ENCODING);
        assertThat(testFileConfig.getSeparatorChar()).isEqualTo(UPDATED_SEPARATOR_CHAR);
        assertThat(testFileConfig.getQuoteChar()).isEqualTo(UPDATED_QUOTE_CHAR);
        assertThat(testFileConfig.getEscapeChar()).isEqualTo(UPDATED_ESCAPE_CHAR);
        assertThat(testFileConfig.getFixedValueWidth()).isEqualTo(UPDATED_FIXED_VALUE_WIDTH);
        assertThat(testFileConfig.getSkipEmptyLines()).isEqualTo(UPDATED_SKIP_EMPTY_LINES);
        assertThat(testFileConfig.getSkipEmptyColumns()).isEqualTo(UPDATED_SKIP_EMPTY_COLUMNS);
        assertThat(testFileConfig.getFailOnInconsistentLineWidth()).isEqualTo(UPDATED_FAIL_ON_INCONSISTENT_LINE_WIDTH);
        assertThat(testFileConfig.getSkipEbcdicHeader()).isEqualTo(UPDATED_SKIP_EBCDIC_HEADER);
        assertThat(testFileConfig.getEolPresent()).isEqualTo(UPDATED_EOL_PRESENT);
        assertThat(testFileConfig.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testFileConfig.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testFileConfig.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingFileConfig() throws Exception {
        int databaseSizeBeforeUpdate = fileConfigRepository.findAll().size();
        fileConfig.setId(count.incrementAndGet());

        // Create the FileConfig
        FileConfigDTO fileConfigDTO = fileConfigMapper.toDto(fileConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fileConfigDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileConfig in the database
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFileConfig() throws Exception {
        int databaseSizeBeforeUpdate = fileConfigRepository.findAll().size();
        fileConfig.setId(count.incrementAndGet());

        // Create the FileConfig
        FileConfigDTO fileConfigDTO = fileConfigMapper.toDto(fileConfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileConfig in the database
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFileConfig() throws Exception {
        int databaseSizeBeforeUpdate = fileConfigRepository.findAll().size();
        fileConfig.setId(count.incrementAndGet());

        // Create the FileConfig
        FileConfigDTO fileConfigDTO = fileConfigMapper.toDto(fileConfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileConfigMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileConfigDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FileConfig in the database
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFileConfigWithPatch() throws Exception {
        // Initialize the database
        fileConfigRepository.saveAndFlush(fileConfig);

        int databaseSizeBeforeUpdate = fileConfigRepository.findAll().size();

        // Update the fileConfig using partial update
        FileConfig partialUpdatedFileConfig = new FileConfig();
        partialUpdatedFileConfig.setId(fileConfig.getId());

        partialUpdatedFileConfig
            .name(UPDATED_NAME)
            .columnNameLineNumber(UPDATED_COLUMN_NAME_LINE_NUMBER)
            .encoding(UPDATED_ENCODING)
            .separatorChar(UPDATED_SEPARATOR_CHAR)
            .quoteChar(UPDATED_QUOTE_CHAR)
            .fixedValueWidth(UPDATED_FIXED_VALUE_WIDTH)
            .skipEmptyLines(UPDATED_SKIP_EMPTY_LINES)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restFileConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFileConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFileConfig))
            )
            .andExpect(status().isOk());

        // Validate the FileConfig in the database
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeUpdate);
        FileConfig testFileConfig = fileConfigList.get(fileConfigList.size() - 1);
        assertThat(testFileConfig.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFileConfig.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testFileConfig.getColumnNameLineNumber()).isEqualTo(UPDATED_COLUMN_NAME_LINE_NUMBER);
        assertThat(testFileConfig.getEncoding()).isEqualTo(UPDATED_ENCODING);
        assertThat(testFileConfig.getSeparatorChar()).isEqualTo(UPDATED_SEPARATOR_CHAR);
        assertThat(testFileConfig.getQuoteChar()).isEqualTo(UPDATED_QUOTE_CHAR);
        assertThat(testFileConfig.getEscapeChar()).isEqualTo(DEFAULT_ESCAPE_CHAR);
        assertThat(testFileConfig.getFixedValueWidth()).isEqualTo(UPDATED_FIXED_VALUE_WIDTH);
        assertThat(testFileConfig.getSkipEmptyLines()).isEqualTo(UPDATED_SKIP_EMPTY_LINES);
        assertThat(testFileConfig.getSkipEmptyColumns()).isEqualTo(DEFAULT_SKIP_EMPTY_COLUMNS);
        assertThat(testFileConfig.getFailOnInconsistentLineWidth()).isEqualTo(DEFAULT_FAIL_ON_INCONSISTENT_LINE_WIDTH);
        assertThat(testFileConfig.getSkipEbcdicHeader()).isEqualTo(DEFAULT_SKIP_EBCDIC_HEADER);
        assertThat(testFileConfig.getEolPresent()).isEqualTo(DEFAULT_EOL_PRESENT);
        assertThat(testFileConfig.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testFileConfig.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testFileConfig.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateFileConfigWithPatch() throws Exception {
        // Initialize the database
        fileConfigRepository.saveAndFlush(fileConfig);

        int databaseSizeBeforeUpdate = fileConfigRepository.findAll().size();

        // Update the fileConfig using partial update
        FileConfig partialUpdatedFileConfig = new FileConfig();
        partialUpdatedFileConfig.setId(fileConfig.getId());

        partialUpdatedFileConfig
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .columnNameLineNumber(UPDATED_COLUMN_NAME_LINE_NUMBER)
            .encoding(UPDATED_ENCODING)
            .separatorChar(UPDATED_SEPARATOR_CHAR)
            .quoteChar(UPDATED_QUOTE_CHAR)
            .escapeChar(UPDATED_ESCAPE_CHAR)
            .fixedValueWidth(UPDATED_FIXED_VALUE_WIDTH)
            .skipEmptyLines(UPDATED_SKIP_EMPTY_LINES)
            .skipEmptyColumns(UPDATED_SKIP_EMPTY_COLUMNS)
            .failOnInconsistentLineWidth(UPDATED_FAIL_ON_INCONSISTENT_LINE_WIDTH)
            .skipEbcdicHeader(UPDATED_SKIP_EBCDIC_HEADER)
            .eolPresent(UPDATED_EOL_PRESENT)
            .fileType(UPDATED_FILE_TYPE)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restFileConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFileConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFileConfig))
            )
            .andExpect(status().isOk());

        // Validate the FileConfig in the database
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeUpdate);
        FileConfig testFileConfig = fileConfigList.get(fileConfigList.size() - 1);
        assertThat(testFileConfig.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFileConfig.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testFileConfig.getColumnNameLineNumber()).isEqualTo(UPDATED_COLUMN_NAME_LINE_NUMBER);
        assertThat(testFileConfig.getEncoding()).isEqualTo(UPDATED_ENCODING);
        assertThat(testFileConfig.getSeparatorChar()).isEqualTo(UPDATED_SEPARATOR_CHAR);
        assertThat(testFileConfig.getQuoteChar()).isEqualTo(UPDATED_QUOTE_CHAR);
        assertThat(testFileConfig.getEscapeChar()).isEqualTo(UPDATED_ESCAPE_CHAR);
        assertThat(testFileConfig.getFixedValueWidth()).isEqualTo(UPDATED_FIXED_VALUE_WIDTH);
        assertThat(testFileConfig.getSkipEmptyLines()).isEqualTo(UPDATED_SKIP_EMPTY_LINES);
        assertThat(testFileConfig.getSkipEmptyColumns()).isEqualTo(UPDATED_SKIP_EMPTY_COLUMNS);
        assertThat(testFileConfig.getFailOnInconsistentLineWidth()).isEqualTo(UPDATED_FAIL_ON_INCONSISTENT_LINE_WIDTH);
        assertThat(testFileConfig.getSkipEbcdicHeader()).isEqualTo(UPDATED_SKIP_EBCDIC_HEADER);
        assertThat(testFileConfig.getEolPresent()).isEqualTo(UPDATED_EOL_PRESENT);
        assertThat(testFileConfig.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testFileConfig.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testFileConfig.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingFileConfig() throws Exception {
        int databaseSizeBeforeUpdate = fileConfigRepository.findAll().size();
        fileConfig.setId(count.incrementAndGet());

        // Create the FileConfig
        FileConfigDTO fileConfigDTO = fileConfigMapper.toDto(fileConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fileConfigDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fileConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileConfig in the database
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFileConfig() throws Exception {
        int databaseSizeBeforeUpdate = fileConfigRepository.findAll().size();
        fileConfig.setId(count.incrementAndGet());

        // Create the FileConfig
        FileConfigDTO fileConfigDTO = fileConfigMapper.toDto(fileConfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fileConfigDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileConfig in the database
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFileConfig() throws Exception {
        int databaseSizeBeforeUpdate = fileConfigRepository.findAll().size();
        fileConfig.setId(count.incrementAndGet());

        // Create the FileConfig
        FileConfigDTO fileConfigDTO = fileConfigMapper.toDto(fileConfig);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileConfigMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fileConfigDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FileConfig in the database
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFileConfig() throws Exception {
        // Initialize the database
        fileConfigRepository.saveAndFlush(fileConfig);

        int databaseSizeBeforeDelete = fileConfigRepository.findAll().size();

        // Delete the fileConfig
        restFileConfigMockMvc
            .perform(delete(ENTITY_API_URL_ID, fileConfig.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FileConfig> fileConfigList = fileConfigRepository.findAll();
        assertThat(fileConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
