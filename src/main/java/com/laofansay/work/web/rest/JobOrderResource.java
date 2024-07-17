package com.laofansay.work.web.rest;

import com.laofansay.work.domain.JobOrder;
import com.laofansay.work.repository.JobOrderRepository;
import com.laofansay.work.service.JobOrderService;
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
 * REST controller for managing {@link com.laofansay.work.domain.JobOrder}.
 */
@RestController
@RequestMapping("/api/job-orders")
public class JobOrderResource {

    private static final Logger log = LoggerFactory.getLogger(JobOrderResource.class);

    private static final String ENTITY_NAME = "jobOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobOrderService jobOrderService;

    private final JobOrderRepository jobOrderRepository;

    public JobOrderResource(JobOrderService jobOrderService, JobOrderRepository jobOrderRepository) {
        this.jobOrderService = jobOrderService;
        this.jobOrderRepository = jobOrderRepository;
    }

    /**
     * {@code POST  /job-orders} : Create a new jobOrder.
     *
     * @param jobOrder the jobOrder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobOrder, or with status {@code 400 (Bad Request)} if the jobOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<JobOrder> createJobOrder(@Valid @RequestBody JobOrder jobOrder) throws URISyntaxException {
        log.debug("REST request to save JobOrder : {}", jobOrder);
        if (jobOrder.getId() != null) {
            throw new BadRequestAlertException("A new jobOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        jobOrder = jobOrderService.save(jobOrder);
        return ResponseEntity.created(new URI("/api/job-orders/" + jobOrder.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, jobOrder.getId()))
            .body(jobOrder);
    }

    /**
     * {@code PUT  /job-orders/:id} : Updates an existing jobOrder.
     *
     * @param id the id of the jobOrder to save.
     * @param jobOrder the jobOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobOrder,
     * or with status {@code 400 (Bad Request)} if the jobOrder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<JobOrder> updateJobOrder(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody JobOrder jobOrder
    ) throws URISyntaxException {
        log.debug("REST request to update JobOrder : {}, {}", id, jobOrder);
        if (jobOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobOrder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        jobOrder = jobOrderService.update(jobOrder);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobOrder.getId()))
            .body(jobOrder);
    }

    /**
     * {@code PATCH  /job-orders/:id} : Partial updates given fields of an existing jobOrder, field will ignore if it is null
     *
     * @param id the id of the jobOrder to save.
     * @param jobOrder the jobOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobOrder,
     * or with status {@code 400 (Bad Request)} if the jobOrder is not valid,
     * or with status {@code 404 (Not Found)} if the jobOrder is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JobOrder> partialUpdateJobOrder(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody JobOrder jobOrder
    ) throws URISyntaxException {
        log.debug("REST request to partial update JobOrder partially : {}, {}", id, jobOrder);
        if (jobOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobOrder.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JobOrder> result = jobOrderService.partialUpdate(jobOrder);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jobOrder.getId())
        );
    }

    /**
     * {@code GET  /job-orders} : get all the jobOrders.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobOrders in body.
     */
    @GetMapping("")
    public ResponseEntity<List<JobOrder>> getAllJobOrders(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of JobOrders");
        Page<JobOrder> page;
        if (eagerload) {
            page = jobOrderService.findAllWithEagerRelationships(pageable);
        } else {
            page = jobOrderService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-orders/:id} : get the "id" jobOrder.
     *
     * @param id the id of the jobOrder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobOrder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<JobOrder> getJobOrder(@PathVariable("id") String id) {
        log.debug("REST request to get JobOrder : {}", id);
        Optional<JobOrder> jobOrder = jobOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobOrder);
    }

    /**
     * {@code DELETE  /job-orders/:id} : delete the "id" jobOrder.
     *
     * @param id the id of the jobOrder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobOrder(@PathVariable("id") String id) {
        log.debug("REST request to delete JobOrder : {}", id);
        jobOrderService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
