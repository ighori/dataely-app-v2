package com.dataely.app.web.rest;

import com.dataely.app.repository.AnalyzerJobRepository;
import com.dataely.app.service.AnalyzerJobService;
import com.dataely.app.service.dto.AnalyzerJobDTO;
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
 * REST controller for managing {@link com.dataely.app.domain.AnalyzerJob}.
 */
@RestController
@RequestMapping("/api")
public class AnalyzerJobResource {

    private final Logger log = LoggerFactory.getLogger(AnalyzerJobResource.class);

    private static final String ENTITY_NAME = "analyzerJob";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnalyzerJobService analyzerJobService;

    private final AnalyzerJobRepository analyzerJobRepository;

    public AnalyzerJobResource(AnalyzerJobService analyzerJobService, AnalyzerJobRepository analyzerJobRepository) {
        this.analyzerJobService = analyzerJobService;
        this.analyzerJobRepository = analyzerJobRepository;
    }

    /**
     * {@code POST  /analyzer-jobs} : Create a new analyzerJob.
     *
     * @param analyzerJobDTO the analyzerJobDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new analyzerJobDTO, or with status {@code 400 (Bad Request)} if the analyzerJob has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/analyzer-jobs")
    public ResponseEntity<AnalyzerJobDTO> createAnalyzerJob(@Valid @RequestBody AnalyzerJobDTO analyzerJobDTO) throws URISyntaxException {
        log.debug("REST request to save AnalyzerJob : {}", analyzerJobDTO);
        if (analyzerJobDTO.getId() != null) {
            throw new BadRequestAlertException("A new analyzerJob cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnalyzerJobDTO result = analyzerJobService.save(analyzerJobDTO);
        return ResponseEntity
            .created(new URI("/api/analyzer-jobs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /analyzer-jobs/:id} : Updates an existing analyzerJob.
     *
     * @param id the id of the analyzerJobDTO to save.
     * @param analyzerJobDTO the analyzerJobDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analyzerJobDTO,
     * or with status {@code 400 (Bad Request)} if the analyzerJobDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the analyzerJobDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/analyzer-jobs/{id}")
    public ResponseEntity<AnalyzerJobDTO> updateAnalyzerJob(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnalyzerJobDTO analyzerJobDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AnalyzerJob : {}, {}", id, analyzerJobDTO);
        if (analyzerJobDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, analyzerJobDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!analyzerJobRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AnalyzerJobDTO result = analyzerJobService.save(analyzerJobDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, analyzerJobDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /analyzer-jobs/:id} : Partial updates given fields of an existing analyzerJob, field will ignore if it is null
     *
     * @param id the id of the analyzerJobDTO to save.
     * @param analyzerJobDTO the analyzerJobDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analyzerJobDTO,
     * or with status {@code 400 (Bad Request)} if the analyzerJobDTO is not valid,
     * or with status {@code 404 (Not Found)} if the analyzerJobDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the analyzerJobDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/analyzer-jobs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AnalyzerJobDTO> partialUpdateAnalyzerJob(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnalyzerJobDTO analyzerJobDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnalyzerJob partially : {}, {}", id, analyzerJobDTO);
        if (analyzerJobDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, analyzerJobDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!analyzerJobRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnalyzerJobDTO> result = analyzerJobService.partialUpdate(analyzerJobDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, analyzerJobDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /analyzer-jobs} : get all the analyzerJobs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of analyzerJobs in body.
     */
    @GetMapping("/analyzer-jobs")
    public ResponseEntity<List<AnalyzerJobDTO>> getAllAnalyzerJobs(Pageable pageable) {
        log.debug("REST request to get a page of AnalyzerJobs");
        Page<AnalyzerJobDTO> page = analyzerJobService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /analyzer-jobs/:id} : get the "id" analyzerJob.
     *
     * @param id the id of the analyzerJobDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the analyzerJobDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/analyzer-jobs/{id}")
    public ResponseEntity<AnalyzerJobDTO> getAnalyzerJob(@PathVariable Long id) {
        log.debug("REST request to get AnalyzerJob : {}", id);
        Optional<AnalyzerJobDTO> analyzerJobDTO = analyzerJobService.findOne(id);
        return ResponseUtil.wrapOrNotFound(analyzerJobDTO);
    }

    /**
     * {@code DELETE  /analyzer-jobs/:id} : delete the "id" analyzerJob.
     *
     * @param id the id of the analyzerJobDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/analyzer-jobs/{id}")
    public ResponseEntity<Void> deleteAnalyzerJob(@PathVariable Long id) {
        log.debug("REST request to delete AnalyzerJob : {}", id);
        analyzerJobService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
