package com.dataely.app.web.rest;

import com.dataely.app.repository.TablesDefinitionRepository;
import com.dataely.app.service.TablesDefinitionService;
import com.dataely.app.service.dto.TablesDefinitionDTO;
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
 * REST controller for managing {@link com.dataely.app.domain.TablesDefinition}.
 */
@RestController
@RequestMapping("/api")
public class TablesDefinitionResource {

    private final Logger log = LoggerFactory.getLogger(TablesDefinitionResource.class);

    private static final String ENTITY_NAME = "tablesDefinition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TablesDefinitionService tablesDefinitionService;

    private final TablesDefinitionRepository tablesDefinitionRepository;

    public TablesDefinitionResource(
        TablesDefinitionService tablesDefinitionService,
        TablesDefinitionRepository tablesDefinitionRepository
    ) {
        this.tablesDefinitionService = tablesDefinitionService;
        this.tablesDefinitionRepository = tablesDefinitionRepository;
    }

    /**
     * {@code POST  /tables-definitions} : Create a new tablesDefinition.
     *
     * @param tablesDefinitionDTO the tablesDefinitionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tablesDefinitionDTO, or with status {@code 400 (Bad Request)} if the tablesDefinition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tables-definitions")
    public ResponseEntity<TablesDefinitionDTO> createTablesDefinition(@Valid @RequestBody TablesDefinitionDTO tablesDefinitionDTO)
        throws URISyntaxException {
        log.debug("REST request to save TablesDefinition : {}", tablesDefinitionDTO);
        if (tablesDefinitionDTO.getId() != null) {
            throw new BadRequestAlertException("A new tablesDefinition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TablesDefinitionDTO result = tablesDefinitionService.save(tablesDefinitionDTO);
        return ResponseEntity
            .created(new URI("/api/tables-definitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tables-definitions/:id} : Updates an existing tablesDefinition.
     *
     * @param id the id of the tablesDefinitionDTO to save.
     * @param tablesDefinitionDTO the tablesDefinitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tablesDefinitionDTO,
     * or with status {@code 400 (Bad Request)} if the tablesDefinitionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tablesDefinitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tables-definitions/{id}")
    public ResponseEntity<TablesDefinitionDTO> updateTablesDefinition(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TablesDefinitionDTO tablesDefinitionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TablesDefinition : {}, {}", id, tablesDefinitionDTO);
        if (tablesDefinitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tablesDefinitionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tablesDefinitionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TablesDefinitionDTO result = tablesDefinitionService.save(tablesDefinitionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tablesDefinitionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tables-definitions/:id} : Partial updates given fields of an existing tablesDefinition, field will ignore if it is null
     *
     * @param id the id of the tablesDefinitionDTO to save.
     * @param tablesDefinitionDTO the tablesDefinitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tablesDefinitionDTO,
     * or with status {@code 400 (Bad Request)} if the tablesDefinitionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tablesDefinitionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tablesDefinitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tables-definitions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<TablesDefinitionDTO> partialUpdateTablesDefinition(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TablesDefinitionDTO tablesDefinitionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TablesDefinition partially : {}, {}", id, tablesDefinitionDTO);
        if (tablesDefinitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tablesDefinitionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tablesDefinitionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TablesDefinitionDTO> result = tablesDefinitionService.partialUpdate(tablesDefinitionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tablesDefinitionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tables-definitions} : get all the tablesDefinitions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tablesDefinitions in body.
     */
    @GetMapping("/tables-definitions")
    public ResponseEntity<List<TablesDefinitionDTO>> getAllTablesDefinitions(Pageable pageable) {
        log.debug("REST request to get a page of TablesDefinitions");
        Page<TablesDefinitionDTO> page = tablesDefinitionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tables-definitions/:id} : get the "id" tablesDefinition.
     *
     * @param id the id of the tablesDefinitionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tablesDefinitionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tables-definitions/{id}")
    public ResponseEntity<TablesDefinitionDTO> getTablesDefinition(@PathVariable Long id) {
        log.debug("REST request to get TablesDefinition : {}", id);
        Optional<TablesDefinitionDTO> tablesDefinitionDTO = tablesDefinitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tablesDefinitionDTO);
    }

    /**
     * {@code DELETE  /tables-definitions/:id} : delete the "id" tablesDefinition.
     *
     * @param id the id of the tablesDefinitionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tables-definitions/{id}")
    public ResponseEntity<Void> deleteTablesDefinition(@PathVariable Long id) {
        log.debug("REST request to delete TablesDefinition : {}", id);
        tablesDefinitionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
