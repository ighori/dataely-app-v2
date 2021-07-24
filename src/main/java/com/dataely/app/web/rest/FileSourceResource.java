package com.dataely.app.web.rest;

import com.dataely.app.repository.FileSourceRepository;
import com.dataely.app.service.FileSourceService;
import com.dataely.app.service.dto.FileSourceDTO;
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
 * REST controller for managing {@link com.dataely.app.domain.FileSource}.
 */
@RestController
@RequestMapping("/api")
public class FileSourceResource {

    private final Logger log = LoggerFactory.getLogger(FileSourceResource.class);

    private static final String ENTITY_NAME = "fileSource";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileSourceService fileSourceService;

    private final FileSourceRepository fileSourceRepository;

    public FileSourceResource(FileSourceService fileSourceService, FileSourceRepository fileSourceRepository) {
        this.fileSourceService = fileSourceService;
        this.fileSourceRepository = fileSourceRepository;
    }

    /**
     * {@code POST  /file-sources} : Create a new fileSource.
     *
     * @param fileSourceDTO the fileSourceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileSourceDTO, or with status {@code 400 (Bad Request)} if the fileSource has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/file-sources")
    public ResponseEntity<FileSourceDTO> createFileSource(@Valid @RequestBody FileSourceDTO fileSourceDTO) throws URISyntaxException {
        log.debug("REST request to save FileSource : {}", fileSourceDTO);
        if (fileSourceDTO.getId() != null) {
            throw new BadRequestAlertException("A new fileSource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileSourceDTO result = fileSourceService.save(fileSourceDTO);
        return ResponseEntity
            .created(new URI("/api/file-sources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /file-sources/:id} : Updates an existing fileSource.
     *
     * @param id the id of the fileSourceDTO to save.
     * @param fileSourceDTO the fileSourceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileSourceDTO,
     * or with status {@code 400 (Bad Request)} if the fileSourceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fileSourceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/file-sources/{id}")
    public ResponseEntity<FileSourceDTO> updateFileSource(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FileSourceDTO fileSourceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FileSource : {}, {}", id, fileSourceDTO);
        if (fileSourceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileSourceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileSourceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FileSourceDTO result = fileSourceService.save(fileSourceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fileSourceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /file-sources/:id} : Partial updates given fields of an existing fileSource, field will ignore if it is null
     *
     * @param id the id of the fileSourceDTO to save.
     * @param fileSourceDTO the fileSourceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileSourceDTO,
     * or with status {@code 400 (Bad Request)} if the fileSourceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fileSourceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fileSourceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/file-sources/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<FileSourceDTO> partialUpdateFileSource(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FileSourceDTO fileSourceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FileSource partially : {}, {}", id, fileSourceDTO);
        if (fileSourceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileSourceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileSourceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FileSourceDTO> result = fileSourceService.partialUpdate(fileSourceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fileSourceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /file-sources} : get all the fileSources.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fileSources in body.
     */
    @GetMapping("/file-sources")
    public ResponseEntity<List<FileSourceDTO>> getAllFileSources(Pageable pageable) {
        log.debug("REST request to get a page of FileSources");
        Page<FileSourceDTO> page = fileSourceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /file-sources/:id} : get the "id" fileSource.
     *
     * @param id the id of the fileSourceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileSourceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/file-sources/{id}")
    public ResponseEntity<FileSourceDTO> getFileSource(@PathVariable Long id) {
        log.debug("REST request to get FileSource : {}", id);
        Optional<FileSourceDTO> fileSourceDTO = fileSourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fileSourceDTO);
    }

    /**
     * {@code DELETE  /file-sources/:id} : delete the "id" fileSource.
     *
     * @param id the id of the fileSourceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/file-sources/{id}")
    public ResponseEntity<Void> deleteFileSource(@PathVariable Long id) {
        log.debug("REST request to delete FileSource : {}", id);
        fileSourceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
