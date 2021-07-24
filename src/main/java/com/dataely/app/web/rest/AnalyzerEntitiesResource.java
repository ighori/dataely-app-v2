package com.dataely.app.web.rest;

import com.dataely.app.repository.AnalyzerEntitiesRepository;
import com.dataely.app.service.AnalyzerEntitiesService;
import com.dataely.app.service.dto.AnalyzerEntitiesDTO;
import com.dataely.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.dataely.app.domain.AnalyzerEntities}.
 */
@RestController
@RequestMapping("/api")
public class AnalyzerEntitiesResource {

    private final Logger log = LoggerFactory.getLogger(AnalyzerEntitiesResource.class);

    private static final String ENTITY_NAME = "analyzerEntities";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnalyzerEntitiesService analyzerEntitiesService;

    private final AnalyzerEntitiesRepository analyzerEntitiesRepository;

    public AnalyzerEntitiesResource(
        AnalyzerEntitiesService analyzerEntitiesService,
        AnalyzerEntitiesRepository analyzerEntitiesRepository
    ) {
        this.analyzerEntitiesService = analyzerEntitiesService;
        this.analyzerEntitiesRepository = analyzerEntitiesRepository;
    }

    /**
     * {@code POST  /analyzer-entities} : Create a new analyzerEntities.
     *
     * @param analyzerEntitiesDTO the analyzerEntitiesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new analyzerEntitiesDTO, or with status {@code 400 (Bad Request)} if the analyzerEntities has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/analyzer-entities")
    public ResponseEntity<AnalyzerEntitiesDTO> createAnalyzerEntities(@Valid @RequestBody AnalyzerEntitiesDTO analyzerEntitiesDTO)
        throws URISyntaxException {
        log.debug("REST request to save AnalyzerEntities : {}", analyzerEntitiesDTO);
        if (analyzerEntitiesDTO.getId() != null) {
            throw new BadRequestAlertException("A new analyzerEntities cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnalyzerEntitiesDTO result = analyzerEntitiesService.save(analyzerEntitiesDTO);
        return ResponseEntity
            .created(new URI("/api/analyzer-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /analyzer-entities/:id} : Updates an existing analyzerEntities.
     *
     * @param id the id of the analyzerEntitiesDTO to save.
     * @param analyzerEntitiesDTO the analyzerEntitiesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analyzerEntitiesDTO,
     * or with status {@code 400 (Bad Request)} if the analyzerEntitiesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the analyzerEntitiesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/analyzer-entities/{id}")
    public ResponseEntity<AnalyzerEntitiesDTO> updateAnalyzerEntities(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnalyzerEntitiesDTO analyzerEntitiesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AnalyzerEntities : {}, {}", id, analyzerEntitiesDTO);
        if (analyzerEntitiesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, analyzerEntitiesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!analyzerEntitiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AnalyzerEntitiesDTO result = analyzerEntitiesService.save(analyzerEntitiesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, analyzerEntitiesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /analyzer-entities/:id} : Partial updates given fields of an existing analyzerEntities, field will ignore if it is null
     *
     * @param id the id of the analyzerEntitiesDTO to save.
     * @param analyzerEntitiesDTO the analyzerEntitiesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analyzerEntitiesDTO,
     * or with status {@code 400 (Bad Request)} if the analyzerEntitiesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the analyzerEntitiesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the analyzerEntitiesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/analyzer-entities/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AnalyzerEntitiesDTO> partialUpdateAnalyzerEntities(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnalyzerEntitiesDTO analyzerEntitiesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnalyzerEntities partially : {}, {}", id, analyzerEntitiesDTO);
        if (analyzerEntitiesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, analyzerEntitiesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!analyzerEntitiesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnalyzerEntitiesDTO> result = analyzerEntitiesService.partialUpdate(analyzerEntitiesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, analyzerEntitiesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /analyzer-entities} : get all the analyzerEntities.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of analyzerEntities in body.
     */
    @GetMapping("/analyzer-entities")
    public ResponseEntity<List<AnalyzerEntitiesDTO>> getAllAnalyzerEntities(Pageable pageable) {
        log.debug("REST request to get a page of AnalyzerEntities");
        Page<AnalyzerEntitiesDTO> page = analyzerEntitiesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /analyzer-entities/:id} : get the "id" analyzerEntities.
     *
     * @param id the id of the analyzerEntitiesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the analyzerEntitiesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/analyzer-entities/{id}")
    public ResponseEntity<AnalyzerEntitiesDTO> getAnalyzerEntities(@PathVariable Long id) {
        log.debug("REST request to get AnalyzerEntities : {}", id);
        Optional<AnalyzerEntitiesDTO> analyzerEntitiesDTO = analyzerEntitiesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(analyzerEntitiesDTO);
    }

    /**
     * {@code DELETE  /analyzer-entities/:id} : delete the "id" analyzerEntities.
     *
     * @param id the id of the analyzerEntitiesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/analyzer-entities/{id}")
    public ResponseEntity<Void> deleteAnalyzerEntities(@PathVariable Long id) {
        log.debug("REST request to delete AnalyzerEntities : {}", id);
        analyzerEntitiesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
