package com.laofansay.work.web.rest;

import com.laofansay.work.domain.CstJob;
import com.laofansay.work.repository.CstJobRepository;
import com.laofansay.work.service.CstJobService;
import com.laofansay.work.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.laofansay.work.domain.CstJob}.
 */
@RestController
@RequestMapping("/api/cst-jobs")
public class CstJobResource {

    private static final Logger log = LoggerFactory.getLogger(CstJobResource.class);

    private static final String ENTITY_NAME = "cstJob";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CstJobService cstJobService;

    private final CstJobRepository cstJobRepository;

    public CstJobResource(CstJobService cstJobService, CstJobRepository cstJobRepository) {
        this.cstJobService = cstJobService;
        this.cstJobRepository = cstJobRepository;
    }

    /**
     * {@code POST  /cst-jobs} : Create a new cstJob.
     *
     * @param cstJob the cstJob to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cstJob, or with status {@code 400 (Bad Request)} if the cstJob has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CstJob> createCstJob(@Valid @RequestBody CstJob cstJob) throws URISyntaxException {
        log.debug("REST request to save CstJob : {}", cstJob);
        if (cstJob.getId() != null) {
            throw new BadRequestAlertException("A new cstJob cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cstJob = cstJobService.save(cstJob);
        return ResponseEntity.created(new URI("/api/cst-jobs/" + cstJob.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cstJob.getId()))
            .body(cstJob);
    }

    /**
     * {@code PUT  /cst-jobs/:id} : Updates an existing cstJob.
     *
     * @param id the id of the cstJob to save.
     * @param cstJob the cstJob to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cstJob,
     * or with status {@code 400 (Bad Request)} if the cstJob is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cstJob couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CstJob> updateCstJob(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody CstJob cstJob
    ) throws URISyntaxException {
        log.debug("REST request to update CstJob : {}, {}", id, cstJob);
        if (cstJob.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cstJob.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cstJobRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cstJob = cstJobService.update(cstJob);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cstJob.getId()))
            .body(cstJob);
    }

    /**
     * {@code PATCH  /cst-jobs/:id} : Partial updates given fields of an existing cstJob, field will ignore if it is null
     *
     * @param id the id of the cstJob to save.
     * @param cstJob the cstJob to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cstJob,
     * or with status {@code 400 (Bad Request)} if the cstJob is not valid,
     * or with status {@code 404 (Not Found)} if the cstJob is not found,
     * or with status {@code 500 (Internal Server Error)} if the cstJob couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CstJob> partialUpdateCstJob(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody CstJob cstJob
    ) throws URISyntaxException {
        log.debug("REST request to partial update CstJob partially : {}, {}", id, cstJob);
        if (cstJob.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cstJob.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cstJobRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CstJob> result = cstJobService.partialUpdate(cstJob);

        return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cstJob.getId()));
    }

    /**
     * {@code GET  /cst-jobs} : get all the cstJobs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cstJobs in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CstJob>> getAllCstJobs(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CstJobs");
        Page<CstJob> page = cstJobService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cst-jobs/:id} : get the "id" cstJob.
     *
     * @param id the id of the cstJob to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cstJob, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CstJob> getCstJob(@PathVariable("id") String id) {
        log.debug("REST request to get CstJob : {}", id);
        Optional<CstJob> cstJob = cstJobService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cstJob);
    }

    /**
     * {@code DELETE  /cst-jobs/:id} : delete the "id" cstJob.
     *
     * @param id the id of the cstJob to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCstJob(@PathVariable("id") String id) {
        log.debug("REST request to delete CstJob : {}", id);
        cstJobService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
