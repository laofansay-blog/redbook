package com.laofansay.work.web.rest;

import com.laofansay.work.domain.JobResult;
import com.laofansay.work.repository.JobResultRepository;
import com.laofansay.work.service.JobResultService;
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
 * REST controller for managing {@link com.laofansay.work.domain.JobResult}.
 */
@RestController
@RequestMapping("/api/job-results")
public class JobResultResource {

    private static final Logger log = LoggerFactory.getLogger(JobResultResource.class);

    private static final String ENTITY_NAME = "jobResult";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobResultService jobResultService;

    private final JobResultRepository jobResultRepository;

    public JobResultResource(JobResultService jobResultService, JobResultRepository jobResultRepository) {
        this.jobResultService = jobResultService;
        this.jobResultRepository = jobResultRepository;
    }

    /**
     * {@code POST  /job-results} : Create a new jobResult.
     *
     * @param jobResult the jobResult to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobResult, or with status {@code 400 (Bad Request)} if the jobResult has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<JobResult> createJobResult(@Valid @RequestBody JobResult jobResult) throws URISyntaxException {
        log.debug("REST request to save JobResult : {}", jobResult);
        if (jobResult.getId() != null) {
            throw new BadRequestAlertException("A new jobResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        jobResult = jobResultService.save(jobResult);
        return ResponseEntity.created(new URI("/api/job-results/" + jobResult.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, jobResult.getId()))
            .body(jobResult);
    }

    /**
     * {@code PUT  /job-results/:id} : Updates an existing jobResult.
     *
     * @param id the id of the jobResult to save.
     * @param jobResult the jobResult to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobResult,
     * or with status {@code 400 (Bad Request)} if the jobResult is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobResult couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<JobResult> updateJobResult(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody JobResult jobResult
    ) throws URISyntaxException {
        log.debug("REST request to update JobResult : {}, {}", id, jobResult);
        if (jobResult.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobResult.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobResultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        jobResult = jobResultService.update(jobResult);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobResult.getId()))
            .body(jobResult);
    }

    /**
     * {@code PATCH  /job-results/:id} : Partial updates given fields of an existing jobResult, field will ignore if it is null
     *
     * @param id the id of the jobResult to save.
     * @param jobResult the jobResult to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobResult,
     * or with status {@code 400 (Bad Request)} if the jobResult is not valid,
     * or with status {@code 404 (Not Found)} if the jobResult is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobResult couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JobResult> partialUpdateJobResult(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody JobResult jobResult
    ) throws URISyntaxException {
        log.debug("REST request to partial update JobResult partially : {}, {}", id, jobResult);
        if (jobResult.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobResult.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobResultRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JobResult> result = jobResultService.partialUpdate(jobResult);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobResult.getId())
        );
    }

    /**
     * {@code GET  /job-results} : get all the jobResults.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobResults in body.
     */
    @GetMapping("")
    public ResponseEntity<List<JobResult>> getAllJobResults(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of JobResults");
        Page<JobResult> page = jobResultService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-results/:id} : get the "id" jobResult.
     *
     * @param id the id of the jobResult to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobResult, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<JobResult> getJobResult(@PathVariable("id") String id) {
        log.debug("REST request to get JobResult : {}", id);
        Optional<JobResult> jobResult = jobResultService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobResult);
    }

    /**
     * {@code DELETE  /job-results/:id} : delete the "id" jobResult.
     *
     * @param id the id of the jobResult to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobResult(@PathVariable("id") String id) {
        log.debug("REST request to delete JobResult : {}", id);
        jobResultService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
