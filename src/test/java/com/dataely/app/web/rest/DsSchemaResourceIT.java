package com.dataely.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dataely.app.IntegrationTest;
import com.dataely.app.domain.DsSchema;
import com.dataely.app.repository.DsSchemaRepository;
import com.dataely.app.service.dto.DsSchemaDTO;
import com.dataely.app.service.mapper.DsSchemaMapper;
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
 * Integration tests for the {@link DsSchemaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DsSchemaResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL = "BBBBBBBBBB";

    private static final Integer DEFAULT_TABLE_COUNT = 1;
    private static final Integer UPDATED_TABLE_COUNT = 2;

    private static final Integer DEFAULT_TABLE_REL_COUNT = 1;
    private static final Integer UPDATED_TABLE_REL_COUNT = 2;

    private static final Instant DEFAULT_CREATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_UPDATED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_UPDATED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/ds-schemas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DsSchemaRepository dsSchemaRepository;

    @Autowired
    private DsSchemaMapper dsSchemaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDsSchemaMockMvc;

    private DsSchema dsSchema;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DsSchema createEntity(EntityManager em) {
        DsSchema dsSchema = new DsSchema()
            .name(DEFAULT_NAME)
            .detail(DEFAULT_DETAIL)
            .tableCount(DEFAULT_TABLE_COUNT)
            .tableRelCount(DEFAULT_TABLE_REL_COUNT)
            .creationDate(DEFAULT_CREATION_DATE)
            .lastUpdated(DEFAULT_LAST_UPDATED);
        return dsSchema;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DsSchema createUpdatedEntity(EntityManager em) {
        DsSchema dsSchema = new DsSchema()
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .tableCount(UPDATED_TABLE_COUNT)
            .tableRelCount(UPDATED_TABLE_REL_COUNT)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        return dsSchema;
    }

    @BeforeEach
    public void initTest() {
        dsSchema = createEntity(em);
    }

    @Test
    @Transactional
    void createDsSchema() throws Exception {
        int databaseSizeBeforeCreate = dsSchemaRepository.findAll().size();
        // Create the DsSchema
        DsSchemaDTO dsSchemaDTO = dsSchemaMapper.toDto(dsSchema);
        restDsSchemaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dsSchemaDTO)))
            .andExpect(status().isCreated());

        // Validate the DsSchema in the database
        List<DsSchema> dsSchemaList = dsSchemaRepository.findAll();
        assertThat(dsSchemaList).hasSize(databaseSizeBeforeCreate + 1);
        DsSchema testDsSchema = dsSchemaList.get(dsSchemaList.size() - 1);
        assertThat(testDsSchema.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDsSchema.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testDsSchema.getTableCount()).isEqualTo(DEFAULT_TABLE_COUNT);
        assertThat(testDsSchema.getTableRelCount()).isEqualTo(DEFAULT_TABLE_REL_COUNT);
        assertThat(testDsSchema.getCreationDate()).isEqualTo(DEFAULT_CREATION_DATE);
        assertThat(testDsSchema.getLastUpdated()).isEqualTo(DEFAULT_LAST_UPDATED);
    }

    @Test
    @Transactional
    void createDsSchemaWithExistingId() throws Exception {
        // Create the DsSchema with an existing ID
        dsSchema.setId(1L);
        DsSchemaDTO dsSchemaDTO = dsSchemaMapper.toDto(dsSchema);

        int databaseSizeBeforeCreate = dsSchemaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDsSchemaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dsSchemaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DsSchema in the database
        List<DsSchema> dsSchemaList = dsSchemaRepository.findAll();
        assertThat(dsSchemaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dsSchemaRepository.findAll().size();
        // set the field null
        dsSchema.setName(null);

        // Create the DsSchema, which fails.
        DsSchemaDTO dsSchemaDTO = dsSchemaMapper.toDto(dsSchema);

        restDsSchemaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dsSchemaDTO)))
            .andExpect(status().isBadRequest());

        List<DsSchema> dsSchemaList = dsSchemaRepository.findAll();
        assertThat(dsSchemaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDsSchemas() throws Exception {
        // Initialize the database
        dsSchemaRepository.saveAndFlush(dsSchema);

        // Get all the dsSchemaList
        restDsSchemaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dsSchema.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].detail").value(hasItem(DEFAULT_DETAIL)))
            .andExpect(jsonPath("$.[*].tableCount").value(hasItem(DEFAULT_TABLE_COUNT)))
            .andExpect(jsonPath("$.[*].tableRelCount").value(hasItem(DEFAULT_TABLE_REL_COUNT)))
            .andExpect(jsonPath("$.[*].creationDate").value(hasItem(DEFAULT_CREATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastUpdated").value(hasItem(DEFAULT_LAST_UPDATED.toString())));
    }

    @Test
    @Transactional
    void getDsSchema() throws Exception {
        // Initialize the database
        dsSchemaRepository.saveAndFlush(dsSchema);

        // Get the dsSchema
        restDsSchemaMockMvc
            .perform(get(ENTITY_API_URL_ID, dsSchema.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dsSchema.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.detail").value(DEFAULT_DETAIL))
            .andExpect(jsonPath("$.tableCount").value(DEFAULT_TABLE_COUNT))
            .andExpect(jsonPath("$.tableRelCount").value(DEFAULT_TABLE_REL_COUNT))
            .andExpect(jsonPath("$.creationDate").value(DEFAULT_CREATION_DATE.toString()))
            .andExpect(jsonPath("$.lastUpdated").value(DEFAULT_LAST_UPDATED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDsSchema() throws Exception {
        // Get the dsSchema
        restDsSchemaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDsSchema() throws Exception {
        // Initialize the database
        dsSchemaRepository.saveAndFlush(dsSchema);

        int databaseSizeBeforeUpdate = dsSchemaRepository.findAll().size();

        // Update the dsSchema
        DsSchema updatedDsSchema = dsSchemaRepository.findById(dsSchema.getId()).get();
        // Disconnect from session so that the updates on updatedDsSchema are not directly saved in db
        em.detach(updatedDsSchema);
        updatedDsSchema
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .tableCount(UPDATED_TABLE_COUNT)
            .tableRelCount(UPDATED_TABLE_REL_COUNT)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);
        DsSchemaDTO dsSchemaDTO = dsSchemaMapper.toDto(updatedDsSchema);

        restDsSchemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dsSchemaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dsSchemaDTO))
            )
            .andExpect(status().isOk());

        // Validate the DsSchema in the database
        List<DsSchema> dsSchemaList = dsSchemaRepository.findAll();
        assertThat(dsSchemaList).hasSize(databaseSizeBeforeUpdate);
        DsSchema testDsSchema = dsSchemaList.get(dsSchemaList.size() - 1);
        assertThat(testDsSchema.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDsSchema.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testDsSchema.getTableCount()).isEqualTo(UPDATED_TABLE_COUNT);
        assertThat(testDsSchema.getTableRelCount()).isEqualTo(UPDATED_TABLE_REL_COUNT);
        assertThat(testDsSchema.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testDsSchema.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void putNonExistingDsSchema() throws Exception {
        int databaseSizeBeforeUpdate = dsSchemaRepository.findAll().size();
        dsSchema.setId(count.incrementAndGet());

        // Create the DsSchema
        DsSchemaDTO dsSchemaDTO = dsSchemaMapper.toDto(dsSchema);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDsSchemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dsSchemaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dsSchemaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DsSchema in the database
        List<DsSchema> dsSchemaList = dsSchemaRepository.findAll();
        assertThat(dsSchemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDsSchema() throws Exception {
        int databaseSizeBeforeUpdate = dsSchemaRepository.findAll().size();
        dsSchema.setId(count.incrementAndGet());

        // Create the DsSchema
        DsSchemaDTO dsSchemaDTO = dsSchemaMapper.toDto(dsSchema);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDsSchemaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dsSchemaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DsSchema in the database
        List<DsSchema> dsSchemaList = dsSchemaRepository.findAll();
        assertThat(dsSchemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDsSchema() throws Exception {
        int databaseSizeBeforeUpdate = dsSchemaRepository.findAll().size();
        dsSchema.setId(count.incrementAndGet());

        // Create the DsSchema
        DsSchemaDTO dsSchemaDTO = dsSchemaMapper.toDto(dsSchema);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDsSchemaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dsSchemaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DsSchema in the database
        List<DsSchema> dsSchemaList = dsSchemaRepository.findAll();
        assertThat(dsSchemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDsSchemaWithPatch() throws Exception {
        // Initialize the database
        dsSchemaRepository.saveAndFlush(dsSchema);

        int databaseSizeBeforeUpdate = dsSchemaRepository.findAll().size();

        // Update the dsSchema using partial update
        DsSchema partialUpdatedDsSchema = new DsSchema();
        partialUpdatedDsSchema.setId(dsSchema.getId());

        partialUpdatedDsSchema
            .name(UPDATED_NAME)
            .tableCount(UPDATED_TABLE_COUNT)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restDsSchemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDsSchema.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDsSchema))
            )
            .andExpect(status().isOk());

        // Validate the DsSchema in the database
        List<DsSchema> dsSchemaList = dsSchemaRepository.findAll();
        assertThat(dsSchemaList).hasSize(databaseSizeBeforeUpdate);
        DsSchema testDsSchema = dsSchemaList.get(dsSchemaList.size() - 1);
        assertThat(testDsSchema.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDsSchema.getDetail()).isEqualTo(DEFAULT_DETAIL);
        assertThat(testDsSchema.getTableCount()).isEqualTo(UPDATED_TABLE_COUNT);
        assertThat(testDsSchema.getTableRelCount()).isEqualTo(DEFAULT_TABLE_REL_COUNT);
        assertThat(testDsSchema.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testDsSchema.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void fullUpdateDsSchemaWithPatch() throws Exception {
        // Initialize the database
        dsSchemaRepository.saveAndFlush(dsSchema);

        int databaseSizeBeforeUpdate = dsSchemaRepository.findAll().size();

        // Update the dsSchema using partial update
        DsSchema partialUpdatedDsSchema = new DsSchema();
        partialUpdatedDsSchema.setId(dsSchema.getId());

        partialUpdatedDsSchema
            .name(UPDATED_NAME)
            .detail(UPDATED_DETAIL)
            .tableCount(UPDATED_TABLE_COUNT)
            .tableRelCount(UPDATED_TABLE_REL_COUNT)
            .creationDate(UPDATED_CREATION_DATE)
            .lastUpdated(UPDATED_LAST_UPDATED);

        restDsSchemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDsSchema.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDsSchema))
            )
            .andExpect(status().isOk());

        // Validate the DsSchema in the database
        List<DsSchema> dsSchemaList = dsSchemaRepository.findAll();
        assertThat(dsSchemaList).hasSize(databaseSizeBeforeUpdate);
        DsSchema testDsSchema = dsSchemaList.get(dsSchemaList.size() - 1);
        assertThat(testDsSchema.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDsSchema.getDetail()).isEqualTo(UPDATED_DETAIL);
        assertThat(testDsSchema.getTableCount()).isEqualTo(UPDATED_TABLE_COUNT);
        assertThat(testDsSchema.getTableRelCount()).isEqualTo(UPDATED_TABLE_REL_COUNT);
        assertThat(testDsSchema.getCreationDate()).isEqualTo(UPDATED_CREATION_DATE);
        assertThat(testDsSchema.getLastUpdated()).isEqualTo(UPDATED_LAST_UPDATED);
    }

    @Test
    @Transactional
    void patchNonExistingDsSchema() throws Exception {
        int databaseSizeBeforeUpdate = dsSchemaRepository.findAll().size();
        dsSchema.setId(count.incrementAndGet());

        // Create the DsSchema
        DsSchemaDTO dsSchemaDTO = dsSchemaMapper.toDto(dsSchema);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDsSchemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dsSchemaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dsSchemaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DsSchema in the database
        List<DsSchema> dsSchemaList = dsSchemaRepository.findAll();
        assertThat(dsSchemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDsSchema() throws Exception {
        int databaseSizeBeforeUpdate = dsSchemaRepository.findAll().size();
        dsSchema.setId(count.incrementAndGet());

        // Create the DsSchema
        DsSchemaDTO dsSchemaDTO = dsSchemaMapper.toDto(dsSchema);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDsSchemaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dsSchemaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DsSchema in the database
        List<DsSchema> dsSchemaList = dsSchemaRepository.findAll();
        assertThat(dsSchemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDsSchema() throws Exception {
        int databaseSizeBeforeUpdate = dsSchemaRepository.findAll().size();
        dsSchema.setId(count.incrementAndGet());

        // Create the DsSchema
        DsSchemaDTO dsSchemaDTO = dsSchemaMapper.toDto(dsSchema);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDsSchemaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dsSchemaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DsSchema in the database
        List<DsSchema> dsSchemaList = dsSchemaRepository.findAll();
        assertThat(dsSchemaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDsSchema() throws Exception {
        // Initialize the database
        dsSchemaRepository.saveAndFlush(dsSchema);

        int databaseSizeBeforeDelete = dsSchemaRepository.findAll().size();

        // Delete the dsSchema
        restDsSchemaMockMvc
            .perform(delete(ENTITY_API_URL_ID, dsSchema.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DsSchema> dsSchemaList = dsSchemaRepository.findAll();
        assertThat(dsSchemaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
