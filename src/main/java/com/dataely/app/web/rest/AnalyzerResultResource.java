package com.dataely.app.web.rest;

import com.dataely.app.repository.AnalyzerResultRepository;
import com.dataely.app.service.AnalyzerResultService;
import com.dataely.app.service.dto.AnalyzerResultDTO;
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
 * REST controller for managing {@link com.dataely.app.domain.AnalyzerResult}.
 */
@RestController
@RequestMapping("/api")
public class AnalyzerResultResource {

    private final Logger log = LoggerFactory.getLogger(AnalyzerResultResource.class);

    private static final String ENTITY_NAME = "analyzerResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnalyzerResultService analyzerResultService;

    private final AnalyzerResultRepository analyzerResultRepository;

    public AnalyzerResultResource(AnalyzerResultService analyzerResultService, AnalyzerResultRepository analyzerResultRepository) {
        this.analyzerResultService = analyzerResultService;
        this.analyzerResultRepository = analyzerResultRepository;
    }

    /**
     * {@code POST  /analyzer-results} : Create a new analyzerResult.
     *
     * @param analyzerResultDTO the analyzerResultDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new analyzerResultDTO, or with status {@code 400 (Bad Request)} if the analyzerResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/analyzer-results")
    public ResponseEntity<AnalyzerResultDTO> createAnalyzerResult(@Valid @RequestBody AnalyzerResultDTO analyzerResultDTO)
        throws URISyntaxException {
        log.debug("REST request to save AnalyzerResult : {}", analyzerResultDTO);
        if (analyzerResultDTO.getId() != null) {
            throw new BadRequestAlertException("A new analyzerResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnalyzerResultDTO result = analyzerResultService.save(analyzerResultDTO);
        return ResponseEntity
            .created(new URI("/api/analyzer-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /analyzer-results/:id} : Updates an existing analyzerResult.
     *
     * @param id the id of the analyzerResultDTO to save.
     * @param analyzerResultDTO the analyzerResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analyzerResultDTO,
     * or with status {@code 400 (Bad Request)} if the analyzerResultDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the analyzerResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/analyzer-results/{id}")
    public ResponseEntity<AnalyzerResultDTO> updateAnalyzerResult(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AnalyzerResultDTO analyzerResultDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AnalyzerResult : {}, {}", id, analyzerResultDTO);
        if (analyzerResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, analyzerResultDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!analyzerResultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AnalyzerResultDTO result = analyzerResultService.save(analyzerResultDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, analyzerResultDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /analyzer-results/:id} : Partial updates given fields of an existing analyzerResult, field will ignore if it is null
     *
     * @param id the id of the analyzerResultDTO to save.
     * @param analyzerResultDTO the analyzerResultDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated analyzerResultDTO,
     * or with status {@code 400 (Bad Request)} if the analyzerResultDTO is not valid,
     * or with status {@code 404 (Not Found)} if the analyzerResultDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the analyzerResultDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/analyzer-results/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AnalyzerResultDTO> partialUpdateAnalyzerResult(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AnalyzerResultDTO analyzerResultDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AnalyzerResult partially : {}, {}", id, analyzerResultDTO);
        if (analyzerResultDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, analyzerResultDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!analyzerResultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnalyzerResultDTO> result = analyzerResultService.partialUpdate(analyzerResultDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, analyzerResultDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /analyzer-results} : get all the analyzerResults.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of analyzerResults in body.
     */
    @GetMapping("/analyzer-results")
    public ResponseEntity<List<AnalyzerResultDTO>> getAllAnalyzerResults(Pageable pageable) {
        log.debug("REST request to get a page of AnalyzerResults");
        Page<AnalyzerResultDTO> page = analyzerResultService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /analyzer-results/:id} : get the "id" analyzerResult.
     *
     * @param id the id of the analyzerResultDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the analyzerResultDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/analyzer-results/{id}")
    public ResponseEntity<AnalyzerResultDTO> getAnalyzerResult(@PathVariable Long id) {
        log.debug("REST request to get AnalyzerResult : {}", id);
        Optional<AnalyzerResultDTO> analyzerResultDTO = analyzerResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(analyzerResultDTO);
    }

    /**
     * {@code DELETE  /analyzer-results/:id} : delete the "id" analyzerResult.
     *
     * @param id the id of the analyzerResultDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/analyzer-results/{id}")
    public ResponseEntity<Void> deleteAnalyzerResult(@PathVariable Long id) {
        log.debug("REST request to delete AnalyzerResult : {}", id);
        analyzerResultService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
