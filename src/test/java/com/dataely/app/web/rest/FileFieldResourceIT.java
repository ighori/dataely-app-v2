package com.dataely.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dataely.app.IntegrationTest;
import com.dataely.app.domain.FileField;
import com.dataely.app.repository.FileFieldRepository;
import com.dataely.app.service.dto.FileFieldDTO;
import com.dataely.app.service.mapper.FileFieldMapper;
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
 * Integration tests for the {@link FileFieldResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FileFieldResourceIT {

    private static final String DEFAULT_FIELD_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIELD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FIELD_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_FIELD_SIZE = 1L;
    private static final Long UPDATED_FIELD_SIZE = 2L;

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/file-fields";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FileFieldRepository fileFieldRepository;

    @Autowired
    private FileFieldMapper fileFieldMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFileFieldMockMvc;

    private FileField fileField;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileField createEntity(EntityManager em) {
        FileField fileField = new FileField()
            .fieldName(DEFAULT_FIELD_NAME)
            .fieldType(DEFAULT_FIELD_TYPE)
            .fieldSize(DEFAULT_FIELD_SIZE)
            .creationDate(DEFAULT_CREATION_DATE)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return fileField;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileField createUpdatedEntity(EntityManager em) {
        FileField fileField = new FileField()
            .fieldName(UPDATED_FIELD_NAME)
            .fieldType(UPDATED_FIELD_TYPE)
            .fieldSize(UPDATED_FIELD_SIZE)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return fileField;
    }

    @BeforeEach
    public void initTest() {
        fileField = createEntity(em);
    }

    @Test
    @Transactional
    void createFileField() throws Exception {
        int databaseSizeBeforeCreate = fileFieldRepository.findAll().size();
        // Create the FileField
        FileFieldDTO fileFieldDTO = fileFieldMapper.toDto(fileField);
        restFileFieldMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileFieldDTO)))
            .andExpect(status().isCreated());

        // Validate the FileField in the database
        List<FileField> fileFieldList = fileFieldRepository.findAll();
        assertThat(fileFieldList).hasSize(databaseSizeBeforeCreate + 1);
        FileField testFileField = fileFieldList.get(fileFieldList.size() - 1);
        assertThat(testFileField.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
        assertThat(testFileField.getFieldType()).isEqualTo(DEFAULT_FIELD_TYPE);
        assertThat(testFileField.getFieldSize()).isEqualTo(DEFAULT_FIELD_SIZE);
        assertThat(testFileField.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testFileField.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void createFileFieldWithExistingId() throws Exception {
        // Create the FileField with an existing ID
        fileField.setId(1L);
        FileFieldDTO fileFieldDTO = fileFieldMapper.toDto(fileField);

        int databaseSizeBeforeCreate = fileFieldRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileFieldMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileFieldDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FileField in the database
        List<FileField> fileFieldList = fileFieldRepository.findAll();
        assertThat(fileFieldList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFieldNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileFieldRepository.findAll().size();
        // set the field null
        fileField.setFieldName(null);

        // Create the FileField, which fails.
        FileFieldDTO fileFieldDTO = fileFieldMapper.toDto(fileField);

        restFileFieldMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileFieldDTO)))
            .andExpect(status().isBadRequest());

        List<FileField> fileFieldList = fileFieldRepository.findAll();
        assertThat(fileFieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFileFields() throws Exception {
        // Initialize the database
        fileFieldRepository.saveAndFlush(fileField);

        // Get all the fileFieldList
        restFileFieldMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileField.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldName").value(hasItem(DEFAULT_FIELD_NAME)))
            .andExpect(jsonPath("$.[*].fieldType").value(hasItem(DEFAULT_FIELD_TYPE)))
            .andExpect(jsonPath("$.[*].fieldSize").value(hasItem(DEFAULT_FIELD_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getFileField() throws Exception {
        // Initialize the database
        fileFieldRepository.saveAndFlush(fileField);

        // Get the fileField
        restFileFieldMockMvc
            .perform(get(ENTITY_API_URL_ID, fileField.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fileField.getId().intValue()))
            .andExpect(jsonPath("$.fieldName").value(DEFAULT_FIELD_NAME))
            .andExpect(jsonPath("$.fieldType").value(DEFAULT_FIELD_TYPE))
            .andExpect(jsonPath("$.fieldSize").value(DEFAULT_FIELD_SIZE.intValue()))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFileField() throws Exception {
        // Get the fileField
        restFileFieldMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFileField() throws Exception {
        // Initialize the database
        fileFieldRepository.saveAndFlush(fileField);

        int databaseSizeBeforeUpdate = fileFieldRepository.findAll().size();

        // Update the fileField
        FileField updatedFileField = fileFieldRepository.findById(fileField.getId()).get();
        // Disconnect from session so that the updates on updatedFileField are not directly saved in db
        em.detach(updatedFileField);
        updatedFileField
            .fieldName(UPDATED_FIELD_NAME)
            .fieldType(UPDATED_FIELD_TYPE)
            .fieldSize(UPDATED_FIELD_SIZE)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        FileFieldDTO fileFieldDTO = fileFieldMapper.toDto(updatedFileField);

        restFileFieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fileFieldDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileFieldDTO))
            )
            .andExpect(status().isOk());

        // Validate the FileField in the database
        List<FileField> fileFieldList = fileFieldRepository.findAll();
        assertThat(fileFieldList).hasSize(databaseSizeBeforeUpdate);
        FileField testFileField = fileFieldList.get(fileFieldList.size() - 1);
        assertThat(testFileField.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testFileField.getFieldType()).isEqualTo(UPDATED_FIELD_TYPE);
        assertThat(testFileField.getFieldSize()).isEqualTo(UPDATED_FIELD_SIZE);
        assertThat(testFileField.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testFileField.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingFileField() throws Exception {
        int databaseSizeBeforeUpdate = fileFieldRepository.findAll().size();
        fileField.setId(count.incrementAndGet());

        // Create the FileField
        FileFieldDTO fileFieldDTO = fileFieldMapper.toDto(fileField);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileFieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fileFieldDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileFieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileField in the database
        List<FileField> fileFieldList = fileFieldRepository.findAll();
        assertThat(fileFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFileField() throws Exception {
        int databaseSizeBeforeUpdate = fileFieldRepository.findAll().size();
        fileField.setId(count.incrementAndGet());

        // Create the FileField
        FileFieldDTO fileFieldDTO = fileFieldMapper.toDto(fileField);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileFieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fileFieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileField in the database
        List<FileField> fileFieldList = fileFieldRepository.findAll();
        assertThat(fileFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFileField() throws Exception {
        int databaseSizeBeforeUpdate = fileFieldRepository.findAll().size();
        fileField.setId(count.incrementAndGet());

        // Create the FileField
        FileFieldDTO fileFieldDTO = fileFieldMapper.toDto(fileField);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileFieldMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fileFieldDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FileField in the database
        List<FileField> fileFieldList = fileFieldRepository.findAll();
        assertThat(fileFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFileFieldWithPatch() throws Exception {
        // Initialize the database
        fileFieldRepository.saveAndFlush(fileField);

        int databaseSizeBeforeUpdate = fileFieldRepository.findAll().size();

        // Update the fileField using partial update
        FileField partialUpdatedFileField = new FileField();
        partialUpdatedFileField.setId(fileField.getId());

        partialUpdatedFileField.fieldSize(UPDATED_FIELD_SIZE);

        restFileFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFileField.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFileField))
            )
            .andExpect(status().isOk());

        // Validate the FileField in the database
        List<FileField> fileFieldList = fileFieldRepository.findAll();
        assertThat(fileFieldList).hasSize(databaseSizeBeforeUpdate);
        FileField testFileField = fileFieldList.get(fileFieldList.size() - 1);
        assertThat(testFileField.getFieldName()).isEqualTo(DEFAULT_FIELD_NAME);
        assertThat(testFileField.getFieldType()).isEqualTo(DEFAULT_FIELD_TYPE);
        assertThat(testFileField.getFieldSize()).isEqualTo(UPDATED_FIELD_SIZE);
        assertThat(testFileField.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testFileField.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateFileFieldWithPatch() throws Exception {
        // Initialize the database
        fileFieldRepository.saveAndFlush(fileField);

        int databaseSizeBeforeUpdate = fileFieldRepository.findAll().size();

        // Update the fileField using partial update
        FileField partialUpdatedFileField = new FileField();
        partialUpdatedFileField.setId(fileField.getId());

        partialUpdatedFileField
            .fieldName(UPDATED_FIELD_NAME)
            .fieldType(UPDATED_FIELD_TYPE)
            .fieldSize(UPDATED_FIELD_SIZE)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restFileFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFileField.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFileField))
            )
            .andExpect(status().isOk());

        // Validate the FileField in the database
        List<FileField> fileFieldList = fileFieldRepository.findAll();
        assertThat(fileFieldList).hasSize(databaseSizeBeforeUpdate);
        FileField testFileField = fileFieldList.get(fileFieldList.size() - 1);
        assertThat(testFileField.getFieldName()).isEqualTo(UPDATED_FIELD_NAME);
        assertThat(testFileField.getFieldType()).isEqualTo(UPDATED_FIELD_TYPE);
        assertThat(testFileField.getFieldSize()).isEqualTo(UPDATED_FIELD_SIZE);
        assertThat(testFileField.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testFileField.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingFileField() throws Exception {
        int databaseSizeBeforeUpdate = fileFieldRepository.findAll().size();
        fileField.setId(count.incrementAndGet());

        // Create the FileField
        FileFieldDTO fileFieldDTO = fileFieldMapper.toDto(fileField);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fileFieldDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fileFieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileField in the database
        List<FileField> fileFieldList = fileFieldRepository.findAll();
        assertThat(fileFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFileField() throws Exception {
        int databaseSizeBeforeUpdate = fileFieldRepository.findAll().size();
        fileField.setId(count.incrementAndGet());

        // Create the FileField
        FileFieldDTO fileFieldDTO = fileFieldMapper.toDto(fileField);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileFieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fileFieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FileField in the database
        List<FileField> fileFieldList = fileFieldRepository.findAll();
        assertThat(fileFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFileField() throws Exception {
        int databaseSizeBeforeUpdate = fileFieldRepository.findAll().size();
        fileField.setId(count.incrementAndGet());

        // Create the FileField
        FileFieldDTO fileFieldDTO = fileFieldMapper.toDto(fileField);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFileFieldMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fileFieldDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FileField in the database
        List<FileField> fileFieldList = fileFieldRepository.findAll();
        assertThat(fileFieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFileField() throws Exception {
        // Initialize the database
        fileFieldRepository.saveAndFlush(fileField);

        int databaseSizeBeforeDelete = fileFieldRepository.findAll().size();

        // Delete the fileField
        restFileFieldMockMvc
            .perform(delete(ENTITY_API_URL_ID, fileField.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FileField> fileFieldList = fileFieldRepository.findAll();
        assertThat(fileFieldList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
