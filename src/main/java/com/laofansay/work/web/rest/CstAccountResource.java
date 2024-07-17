package com.laofansay.work.web.rest;

import com.laofansay.work.domain.CstAccount;
import com.laofansay.work.repository.CstAccountRepository;
import com.laofansay.work.service.CstAccountService;
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
 * REST controller for managing {@link com.laofansay.work.domain.CstAccount}.
 */
@RestController
@RequestMapping("/api/cst-accounts")
public class CstAccountResource {

    private static final Logger log = LoggerFactory.getLogger(CstAccountResource.class);

    private static final String ENTITY_NAME = "cstAccount";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CstAccountService cstAccountService;

    private final CstAccountRepository cstAccountRepository;

    public CstAccountResource(CstAccountService cstAccountService, CstAccountRepository cstAccountRepository) {
        this.cstAccountService = cstAccountService;
        this.cstAccountRepository = cstAccountRepository;
    }

    /**
     * {@code POST  /cst-accounts} : Create a new cstAccount.
     *
     * @param cstAccount the cstAccount to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cstAccount, or with status {@code 400 (Bad Request)} if the cstAccount has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CstAccount> createCstAccount(@Valid @RequestBody CstAccount cstAccount) throws URISyntaxException {
        log.debug("REST request to save CstAccount : {}", cstAccount);
        if (cstAccount.getId() != null) {
            throw new BadRequestAlertException("A new cstAccount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        cstAccount = cstAccountService.save(cstAccount);
        return ResponseEntity.created(new URI("/api/cst-accounts/" + cstAccount.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, cstAccount.getId()))
            .body(cstAccount);
    }

    /**
     * {@code PUT  /cst-accounts/:id} : Updates an existing cstAccount.
     *
     * @param id the id of the cstAccount to save.
     * @param cstAccount the cstAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cstAccount,
     * or with status {@code 400 (Bad Request)} if the cstAccount is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cstAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CstAccount> updateCstAccount(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody CstAccount cstAccount
    ) throws URISyntaxException {
        log.debug("REST request to update CstAccount : {}, {}", id, cstAccount);
        if (cstAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cstAccount.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cstAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        cstAccount = cstAccountService.update(cstAccount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cstAccount.getId()))
            .body(cstAccount);
    }

    /**
     * {@code PATCH  /cst-accounts/:id} : Partial updates given fields of an existing cstAccount, field will ignore if it is null
     *
     * @param id the id of the cstAccount to save.
     * @param cstAccount the cstAccount to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cstAccount,
     * or with status {@code 400 (Bad Request)} if the cstAccount is not valid,
     * or with status {@code 404 (Not Found)} if the cstAccount is not found,
     * or with status {@code 500 (Internal Server Error)} if the cstAccount couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CstAccount> partialUpdateCstAccount(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody CstAccount cstAccount
    ) throws URISyntaxException {
        log.debug("REST request to partial update CstAccount partially : {}, {}", id, cstAccount);
        if (cstAccount.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cstAccount.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cstAccountRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CstAccount> result = cstAccountService.partialUpdate(cstAccount);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cstAccount.getId())
        );
    }

    /**
     * {@code GET  /cst-accounts} : get all the cstAccounts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cstAccounts in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CstAccount>> getAllCstAccounts(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CstAccounts");
        Page<CstAccount> page = cstAccountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cst-accounts/:id} : get the "id" cstAccount.
     *
     * @param id the id of the cstAccount to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cstAccount, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CstAccount> getCstAccount(@PathVariable("id") String id) {
        log.debug("REST request to get CstAccount : {}", id);
        Optional<CstAccount> cstAccount = cstAccountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cstAccount);
    }

    /**
     * {@code DELETE  /cst-accounts/:id} : delete the "id" cstAccount.
     *
     * @param id the id of the cstAccount to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCstAccount(@PathVariable("id") String id) {
        log.debug("REST request to delete CstAccount : {}", id);
        cstAccountService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
