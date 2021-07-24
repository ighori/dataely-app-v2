package com.dataely.app.web.rest;

import com.dataely.app.repository.DsSchemaRelationshipRepository;
import com.dataely.app.service.DsSchemaRelationshipService;
import com.dataely.app.service.dto.DsSchemaRelationshipDTO;
import com.dataely.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.dataely.app.domain.DsSchemaRelationship}.
 */
@RestController
@RequestMapping("/api")
public class DsSchemaRelationshipResource {

    private final Logger log = LoggerFactory.getLogger(DsSchemaRelationshipResource.class);

    private static final String ENTITY_NAME = "dsSchemaRelationship";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DsSchemaRelationshipService dsSchemaRelationshipService;

    private final DsSchemaRelationshipRepository dsSchemaRelationshipRepository;

    public DsSchemaRelationshipResource(
        DsSchemaRelationshipService dsSchemaRelationshipService,
        DsSchemaRelationshipRepository dsSchemaRelationshipRepository
    ) {
        this.dsSchemaRelationshipService = dsSchemaRelationshipService;
        this.dsSchemaRelationshipRepository = dsSchemaRelationshipRepository;
    }

    /**
     * {@code POST  /ds-schema-relationships} : Create a new dsSchemaRelationship.
     *
     * @param dsSchemaRelationshipDTO the dsSchemaRelationshipDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dsSchemaRelationshipDTO, or with status {@code 400 (Bad Request)} if the dsSchemaRelationship has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ds-schema-relationships")
    public ResponseEntity<DsSchemaRelationshipDTO> createDsSchemaRelationship(@RequestBody DsSchemaRelationshipDTO dsSchemaRelationshipDTO)
        throws URISyntaxException {
        log.debug("REST request to save DsSchemaRelationship : {}", dsSchemaRelationshipDTO);
        if (dsSchemaRelationshipDTO.getId() != null) {
            throw new BadRequestAlertException("A new dsSchemaRelationship cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DsSchemaRelationshipDTO result = dsSchemaRelationshipService.save(dsSchemaRelationshipDTO);
        return ResponseEntity
            .created(new URI("/api/ds-schema-relationships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ds-schema-relationships/:id} : Updates an existing dsSchemaRelationship.
     *
     * @param id the id of the dsSchemaRelationshipDTO to save.
     * @param dsSchemaRelationshipDTO the dsSchemaRelationshipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dsSchemaRelationshipDTO,
     * or with status {@code 400 (Bad Request)} if the dsSchemaRelationshipDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dsSchemaRelationshipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ds-schema-relationships/{id}")
    public ResponseEntity<DsSchemaRelationshipDTO> updateDsSchemaRelationship(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DsSchemaRelationshipDTO dsSchemaRelationshipDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DsSchemaRelationship : {}, {}", id, dsSchemaRelationshipDTO);
        if (dsSchemaRelationshipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dsSchemaRelationshipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dsSchemaRelationshipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DsSchemaRelationshipDTO result = dsSchemaRelationshipService.save(dsSchemaRelationshipDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dsSchemaRelationshipDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /ds-schema-relationships/:id} : Partial updates given fields of an existing dsSchemaRelationship, field will ignore if it is null
     *
     * @param id the id of the dsSchemaRelationshipDTO to save.
     * @param dsSchemaRelationshipDTO the dsSchemaRelationshipDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dsSchemaRelationshipDTO,
     * or with status {@code 400 (Bad Request)} if the dsSchemaRelationshipDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dsSchemaRelationshipDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dsSchemaRelationshipDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/ds-schema-relationships/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DsSchemaRelationshipDTO> partialUpdateDsSchemaRelationship(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DsSchemaRelationshipDTO dsSchemaRelationshipDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DsSchemaRelationship partially : {}, {}", id, dsSchemaRelationshipDTO);
        if (dsSchemaRelationshipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dsSchemaRelationshipDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dsSchemaRelationshipRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DsSchemaRelationshipDTO> result = dsSchemaRelationshipService.partialUpdate(dsSchemaRelationshipDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dsSchemaRelationshipDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /ds-schema-relationships} : get all the dsSchemaRelationships.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dsSchemaRelationships in body.
     */
    @GetMapping("/ds-schema-relationships")
    public ResponseEntity<List<DsSchemaRelationshipDTO>> getAllDsSchemaRelationships(Pageable pageable) {
        log.debug("REST request to get a page of DsSchemaRelationships");
        Page<DsSchemaRelationshipDTO> page = dsSchemaRelationshipService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ds-schema-relationships/:id} : get the "id" dsSchemaRelationship.
     *
     * @param id the id of the dsSchemaRelationshipDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dsSchemaRelationshipDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ds-schema-relationships/{id}")
    public ResponseEntity<DsSchemaRelationshipDTO> getDsSchemaRelationship(@PathVariable Long id) {
        log.debug("REST request to get DsSchemaRelationship : {}", id);
        Optional<DsSchemaRelationshipDTO> dsSchemaRelationshipDTO = dsSchemaRelationshipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dsSchemaRelationshipDTO);
    }

    /**
     * {@code DELETE  /ds-schema-relationships/:id} : delete the "id" dsSchemaRelationship.
     *
     * @param id the id of the dsSchemaRelationshipDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ds-schema-relationships/{id}")
    public ResponseEntity<Void> deleteDsSchemaRelationship(@PathVariable Long id) {
        log.debug("REST request to delete DsSchemaRelationship : {}", id);
        dsSchemaRelationshipService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
