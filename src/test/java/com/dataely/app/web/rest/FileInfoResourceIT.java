package com.dataely.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dataely.app.IntegrationTest;
import com.dataely.app.domain.FileInfo;
import com.dataely.app.domain.enumeration.EdsType;
import com.dataely.app.repository.FileInfoRepository;
import com.dataely.app.service.dto.FileInfoDTO;
import com.dataely.app.service.mapper.FileInfoMapper;
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
 * Integration tests for the {@link FileInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FileInfoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final EdsType DEFAULT_FILE_TYPE = EdsType.JDBC;
    private static final EdsType UPDATED_FILE_TYPE = EdsType.JSON;

    private static final String DEFAULT_FILE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_FILE_PATH = "BBBBBBBBBB";

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

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/file-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFileInfoMockMvc;

    private FileInfo fileInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileInfo createEntity(EntityManager em) {
        FileInfo fileInfo = new FileInfo()
            .name(DEFAULT_NAME)
            .detail(DEFAULT_DETAIL)
            .fileType(DEFAULT_FILE_TYPE)
            .filePath(DEFAULT_FILE_PATH)
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
            .creationDate(DEFAULT_CREATION_DATE)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return fileInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileInfo createUpdatedEntity(EntityManager em) {
        FileInfo fileInfo = new FileInfo()
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .fileType(UPDATED_FILE_TYPE)
            .filePath(UPDATED_FILE_PATH)
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
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return fileInfo;
    }

    @BeforeEach
    public void initTest() {
        fileInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createFileInfo() throws Exception {
        int databaseSizeBeforeCreate = fileInfoRepository.findAll().size();
        // Create the FileInfo
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);
        restFileInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the FileInfo in the database
        List<FileInfo> fileInfoList = fileInfoRepository.findAll();
        assertThat(fileInfoList).hasSize(databaseSizeBeforeCreate + 1);
        FileInfo testFileInfo = fileInfoList.get(fileInfoList.size() - 1);
        assertThat(testFileInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFileInfo.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testFileInfo.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testFileInfo.getFilePath()).isEqualTo(DEFAULT_FILE_PATH);
        assertThat(testFileInfo.getColumnNameLineNumber()).isEqualTo(DEFAULT_COLUMN_NAME_LINE_NUMBER);
        assertThat(testFileInfo.getEncoding()).isEqualTo(DEFAULT_ENCODING);
        assertThat(testFileInfo.getSeparatorChar()).isEqualTo(DEFAULT_SEPARATOR_CHAR);
        assertThat(testFileInfo.getQuoteChar()).isEqualTo(DEFAULT_QUOTE_CHAR);
        assertThat(testFileInfo.getEscapeChar()).isEqualTo(DEFAULT_ESCAPE_CHAR);
        assertThat(testFileInfo.getFixedValueWidth()).isEqualTo(DEFAULT_FIXED_VALUE_WIDTH);
        assertThat(testFileInfo.getSkipEmptyLines()).isEqualTo(DEFAULT_SKIP_EMPTY_LINES);
        assertThat(testFileInfo.getSkipEmptyColumns()).isEqualTo(DEFAULT_SKIP_EMPTY_COLUMNS);
        assertThat(testFileInfo.getFailOnInconsistentLineWidth()).isEqualTo(DEFAULT_FAIL_ON_INCONSISTENT_LINE_WIDTH);
        assertThat(testFileInfo.getSkipEbcdicHeader()).isEqualTo(DEFAULT_SKIP_EBCDIC_HEADER);
        assertThat(testFileInfo.getEolPresent()).isEqualTo(DEFAULT_EOL_PRESENT);
        assertThat(testFileInfo.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testFileInfo.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void createFileInfoWithExistingId() throws Exception {
        // Create the FileInfo with an existing ID
        fileInfo.setId(1L);
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);

        int databaseSizeBeforeCreate = fileInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FileInfo in the database
        List<FileInfo> fileInfoList = fileInfoRepository.findAll();
        assertThat(fileInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileInfoRepository.findAll().size();
        // set the field null
        fileInfo.setName(null);

        // Create the FileInfo, which fails.
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);

        restFileInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileInfoDTO)))
            .andExpect(status().isBadRequest());

        List<FileInfo> fileInfoList = fileInfoRepository.findAll();
        assertThat(fileInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFileTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileInfoRepository.findAll().size();
        // set the field null
        fileInfo.setFileType(null);

        // Create the FileInfo, which fails.
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);

        restFileInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileInfoDTO)))
            .andExpect(status().isBadRequest());

        List<FileInfo> fileInfoList = fileInfoRepository.findAll();
        assertThat(fileInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFilePathIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileInfoRepository.findAll().size();
        // set the field null
        fileInfo.setFilePath(null);

        // Create the FileInfo, which fails.
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);

        restFileInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileInfoDTO)))
            .andExpect(status().isBadRequest());

        List<FileInfo> fileInfoList = fileInfoRepository.findAll();
        assertThat(fileInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFileInfos() throws Exception {
        // Initialize the database
        fileInfoRepository.saveAndFlush(fileInfo);

        // Get all the fileInfoList
        restFileInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].filePath").value(hasItem(DEFAULT_FILE_PATH)))
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
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getFileInfo() throws Exception {
        // Initialize the database
        fileInfoRepository.saveAndFlush(fileInfo);

        // Get the fileInfo
        restFileInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, fileInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fileInfo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE.toString()))
            .andExpect(jsonPath("$.filePath").value(DEFAULT_FILE_PATH))
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
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFileInfo() throws Exception {
        // Get the fileInfo
        restFileInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFileInfo() throws Exception {
        // Initialize the database
        fileInfoRepository.saveAndFlush(fileInfo);

        int databaseSizeBeforeUpdate = fileInfoRepository.findAll().size();

        // Update the fileInfo
        FileInfo updatedFileInfo = fileInfoRepository.findById(fileInfo.getId()).get();
        // Disconnect from session so that the updates on updatedFileInfo are not directly saved in db
        em.detach(updatedFileInfo);
        updatedFileInfo
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .fileType(UPDATED_FILE_TYPE)
            .filePath(UPDATED_FILE_PATH)
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
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(updatedFileInfo);

        restFileInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fileInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the FileInfo in the database
        List<FileInfo> fileInfoList = fileInfoRepository.findAll();
        assertThat(fileInfoList).hasSize(databaseSizeBeforeUpdate);
        FileInfo testFileInfo = fileInfoList.get(fileInfoList.size() - 1);
        assertThat(testFileInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFileInfo.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testFileInfo.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testFileInfo.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
        assertThat(testFileInfo.getColumnNameLineNumber()).isEqualTo(UPDATED_COLUMN_NAME_LINE_NUMBER);
        assertThat(testFileInfo.getEncoding()).isEqualTo(UPDATED_ENCODING);
        assertThat(testFileInfo.getSeparatorChar()).isEqualTo(UPDATED_SEPARATOR_CHAR);
        assertThat(testFileInfo.getQuoteChar()).isEqualTo(UPDATED_QUOTE_CHAR);
        assertThat(testFileInfo.getEscapeChar()).isEqualTo(UPDATED_ESCAPE_CHAR);
        assertThat(testFileInfo.getFixedValueWidth()).isEqualTo(UPDATED_FIXED_VALUE_WIDTH);
        assertThat(testFileInfo.getSkipEmptyLines()).isEqualTo(UPDATED_SKIP_EMPTY_LINES);
        assertThat(testFileInfo.getSkipEmptyColumns()).isEqualTo(UPDATED_SKIP_EMPTY_COLUMNS);
        assertThat(testFileInfo.getFailOnInconsistentLineWidth()).isEqualTo(UPDATED_FAIL_ON_INCONSISTENT_LINE_WIDTH);
        assertThat(testFileInfo.getSkipEbcdicHeader()).isEqualTo(UPDATED_SKIP_EBCDIC_HEADER);
        assertThat(testFileInfo.getEolPresent()).isEqualTo(UPDATED_EOL_PRESENT);
        assertThat(testFileInfo.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testFileInfo.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingFileInfo() throws Exception {
        int databaseSizeBeforeUpdate = fileInfoRepository.findAll().size();
        fileInfo.setId(count.incrementAndGet());

        // Create the FileInfo
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fileInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileInfo in the database
        List<FileInfo> fileInfoList = fileInfoRepository.findAll();
        assertThat(fileInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFileInfo() throws Exception {
        int databaseSizeBeforeUpdate = fileInfoRepository.findAll().size();
        fileInfo.setId(count.incrementAndGet());

        // Create the FileInfo
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileInfo in the database
        List<FileInfo> fileInfoList = fileInfoRepository.findAll();
        assertThat(fileInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFileInfo() throws Exception {
        int databaseSizeBeforeUpdate = fileInfoRepository.findAll().size();
        fileInfo.setId(count.incrementAndGet());

        // Create the FileInfo
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileInfoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FileInfo in the database
        List<FileInfo> fileInfoList = fileInfoRepository.findAll();
        assertThat(fileInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFileInfoWithPatch() throws Exception {
        // Initialize the database
        fileInfoRepository.saveAndFlush(fileInfo);

        int databaseSizeBeforeUpdate = fileInfoRepository.findAll().size();

        // Update the fileInfo using partial update
        FileInfo partialUpdatedFileInfo = new FileInfo();
        partialUpdatedFileInfo.setId(fileInfo.getId());

        partialUpdatedFileInfo
            .fileType(UPDATED_FILE_TYPE)
            .filePath(UPDATED_FILE_PATH)
            .columnNameLineNumber(UPDATED_COLUMN_NAME_LINE_NUMBER)
            .fixedValueWidth(UPDATED_FIXED_VALUE_WIDTH)
            .skipEmptyLines(UPDATED_SKIP_EMPTY_LINES)
            .skipEmptyColumns(UPDATED_SKIP_EMPTY_COLUMNS)
            .failOnInconsistentLineWidth(UPDATED_FAIL_ON_INCONSISTENT_LINE_WIDTH)
            .skipEbcdicHeader(UPDATED_SKIP_EBCDIC_HEADER);

        restFileInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFileInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFileInfo))
            )
            .andExpect(status().isOk());

        // Validate the FileInfo in the database
        List<FileInfo> fileInfoList = fileInfoRepository.findAll();
        assertThat(fileInfoList).hasSize(databaseSizeBeforeUpdate);
        FileInfo testFileInfo = fileInfoList.get(fileInfoList.size() - 1);
        assertThat(testFileInfo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFileInfo.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testFileInfo.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testFileInfo.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
        assertThat(testFileInfo.getColumnNameLineNumber()).isEqualTo(UPDATED_COLUMN_NAME_LINE_NUMBER);
        assertThat(testFileInfo.getEncoding()).isEqualTo(DEFAULT_ENCODING);
        assertThat(testFileInfo.getSeparatorChar()).isEqualTo(DEFAULT_SEPARATOR_CHAR);
        assertThat(testFileInfo.getQuoteChar()).isEqualTo(DEFAULT_QUOTE_CHAR);
        assertThat(testFileInfo.getEscapeChar()).isEqualTo(DEFAULT_ESCAPE_CHAR);
        assertThat(testFileInfo.getFixedValueWidth()).isEqualTo(UPDATED_FIXED_VALUE_WIDTH);
        assertThat(testFileInfo.getSkipEmptyLines()).isEqualTo(UPDATED_SKIP_EMPTY_LINES);
        assertThat(testFileInfo.getSkipEmptyColumns()).isEqualTo(UPDATED_SKIP_EMPTY_COLUMNS);
        assertThat(testFileInfo.getFailOnInconsistentLineWidth()).isEqualTo(UPDATED_FAIL_ON_INCONSISTENT_LINE_WIDTH);
        assertThat(testFileInfo.getSkipEbcdicHeader()).isEqualTo(UPDATED_SKIP_EBCDIC_HEADER);
        assertThat(testFileInfo.getEolPresent()).isEqualTo(DEFAULT_EOL_PRESENT);
        assertThat(testFileInfo.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testFileInfo.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateFileInfoWithPatch() throws Exception {
        // Initialize the database
        fileInfoRepository.saveAndFlush(fileInfo);

        int databaseSizeBeforeUpdate = fileInfoRepository.findAll().size();

        // Update the fileInfo using partial update
        FileInfo partialUpdatedFileInfo = new FileInfo();
        partialUpdatedFileInfo.setId(fileInfo.getId());

        partialUpdatedFileInfo
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .fileType(UPDATED_FILE_TYPE)
            .filePath(UPDATED_FILE_PATH)
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
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restFileInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFileInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFileInfo))
            )
            .andExpect(status().isOk());

        // Validate the FileInfo in the database
        List<FileInfo> fileInfoList = fileInfoRepository.findAll();
        assertThat(fileInfoList).hasSize(databaseSizeBeforeUpdate);
        FileInfo testFileInfo = fileInfoList.get(fileInfoList.size() - 1);
        assertThat(testFileInfo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFileInfo.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testFileInfo.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testFileInfo.getFilePath()).isEqualTo(UPDATED_FILE_PATH);
        assertThat(testFileInfo.getColumnNameLineNumber()).isEqualTo(UPDATED_COLUMN_NAME_LINE_NUMBER);
        assertThat(testFileInfo.getEncoding()).isEqualTo(UPDATED_ENCODING);
        assertThat(testFileInfo.getSeparatorChar()).isEqualTo(UPDATED_SEPARATOR_CHAR);
        assertThat(testFileInfo.getQuoteChar()).isEqualTo(UPDATED_QUOTE_CHAR);
        assertThat(testFileInfo.getEscapeChar()).isEqualTo(UPDATED_ESCAPE_CHAR);
        assertThat(testFileInfo.getFixedValueWidth()).isEqualTo(UPDATED_FIXED_VALUE_WIDTH);
        assertThat(testFileInfo.getSkipEmptyLines()).isEqualTo(UPDATED_SKIP_EMPTY_LINES);
        assertThat(testFileInfo.getSkipEmptyColumns()).isEqualTo(UPDATED_SKIP_EMPTY_COLUMNS);
        assertThat(testFileInfo.getFailOnInconsistentLineWidth()).isEqualTo(UPDATED_FAIL_ON_INCONSISTENT_LINE_WIDTH);
        assertThat(testFileInfo.getSkipEbcdicHeader()).isEqualTo(UPDATED_SKIP_EBCDIC_HEADER);
        assertThat(testFileInfo.getEolPresent()).isEqualTo(UPDATED_EOL_PRESENT);
        assertThat(testFileInfo.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testFileInfo.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingFileInfo() throws Exception {
        int databaseSizeBeforeUpdate = fileInfoRepository.findAll().size();
        fileInfo.setId(count.incrementAndGet());

        // Create the FileInfo
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fileInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fileInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileInfo in the database
        List<FileInfo> fileInfoList = fileInfoRepository.findAll();
        assertThat(fileInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFileInfo() throws Exception {
        int databaseSizeBeforeUpdate = fileInfoRepository.findAll().size();
        fileInfo.setId(count.incrementAndGet());

        // Create the FileInfo
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fileInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileInfo in the database
        List<FileInfo> fileInfoList = fileInfoRepository.findAll();
        assertThat(fileInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFileInfo() throws Exception {
        int databaseSizeBeforeUpdate = fileInfoRepository.findAll().size();
        fileInfo.setId(count.incrementAndGet());

        // Create the FileInfo
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fileInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FileInfo in the database
        List<FileInfo> fileInfoList = fileInfoRepository.findAll();
        assertThat(fileInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFileInfo() throws Exception {
        // Initialize the database
        fileInfoRepository.saveAndFlush(fileInfo);

        int databaseSizeBeforeDelete = fileInfoRepository.findAll().size();

        // Delete the fileInfo
        restFileInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, fileInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FileInfo> fileInfoList = fileInfoRepository.findAll();
        assertThat(fileInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
