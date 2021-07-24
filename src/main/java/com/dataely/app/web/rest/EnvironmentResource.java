package com.dataely.app.web.rest;

import com.dataely.app.repository.EnvironmentRepository;
import com.dataely.app.service.EnvironmentService;
import com.dataely.app.service.dto.EnvironmentDTO;
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
 * REST controller for managing {@link com.dataely.app.domain.Environment}.
 */
@RestController
@RequestMapping("/api")
public class EnvironmentResource {

    private final Logger log = LoggerFactory.getLogger(EnvironmentResource.class);

    private static final String ENTITY_NAME = "environment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnvironmentService environmentService;

    private final EnvironmentRepository environmentRepository;

    public EnvironmentResource(EnvironmentService environmentService, EnvironmentRepository environmentRepository) {
        this.environmentService = environmentService;
        this.environmentRepository = environmentRepository;
    }

    /**
     * {@code POST  /environments} : Create a new environment.
     *
     * @param environmentDTO the environmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new environmentDTO, or with status {@code 400 (Bad Request)} if the environment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/environments")
    public ResponseEntity<EnvironmentDTO> createEnvironment(@Valid @RequestBody EnvironmentDTO environmentDTO) throws URISyntaxException {
        log.debug("REST request to save Environment : {}", environmentDTO);
        if (environmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new environment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnvironmentDTO result = environmentService.save(environmentDTO);
        return ResponseEntity
            .created(new URI("/api/environments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /environments/:id} : Updates an existing environment.
     *
     * @param id the id of the environmentDTO to save.
     * @param environmentDTO the environmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated environmentDTO,
     * or with status {@code 400 (Bad Request)} if the environmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the environmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/environments/{id}")
    public ResponseEntity<EnvironmentDTO> updateEnvironment(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EnvironmentDTO environmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Environment : {}, {}", id, environmentDTO);
        if (environmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, environmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!environmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EnvironmentDTO result = environmentService.save(environmentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, environmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /environments/:id} : Partial updates given fields of an existing environment, field will ignore if it is null
     *
     * @param id the id of the environmentDTO to save.
     * @param environmentDTO the environmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated environmentDTO,
     * or with status {@code 400 (Bad Request)} if the environmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the environmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the environmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/environments/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EnvironmentDTO> partialUpdateEnvironment(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EnvironmentDTO environmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Environment partially : {}, {}", id, environmentDTO);
        if (environmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, environmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!environmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EnvironmentDTO> result = environmentService.partialUpdate(environmentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, environmentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /environments} : get all the environments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of environments in body.
     */
    @GetMapping("/environments")
    public ResponseEntity<List<EnvironmentDTO>> getAllEnvironments(Pageable pageable) {
        log.debug("REST request to get a page of Environments");
        Page<EnvironmentDTO> page = environmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /environments/:id} : get the "id" environment.
     *
     * @param id the id of the environmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the environmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/environments/{id}")
    public ResponseEntity<EnvironmentDTO> getEnvironment(@PathVariable Long id) {
        log.debug("REST request to get Environment : {}", id);
        Optional<EnvironmentDTO> environmentDTO = environmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(environmentDTO);
    }

    /**
     * {@code DELETE  /environments/:id} : delete the "id" environment.
     *
     * @param id the id of the environmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/environments/{id}")
    public ResponseEntity<Void> deleteEnvironment(@PathVariable Long id) {
        log.debug("REST request to delete Environment : {}", id);
        environmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
