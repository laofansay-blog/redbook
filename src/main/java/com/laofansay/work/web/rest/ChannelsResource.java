package com.laofansay.work.web.rest;

import com.laofansay.work.domain.Channels;
import com.laofansay.work.repository.ChannelsRepository;
import com.laofansay.work.service.ChannelsService;
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
 * REST controller for managing {@link com.laofansay.work.domain.Channels}.
 */
@RestController
@RequestMapping("/api/channels")
public class ChannelsResource {

    private static final Logger log = LoggerFactory.getLogger(ChannelsResource.class);

    private static final String ENTITY_NAME = "channels";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChannelsService channelsService;

    private final ChannelsRepository channelsRepository;

    public ChannelsResource(ChannelsService channelsService, ChannelsRepository channelsRepository) {
        this.channelsService = channelsService;
        this.channelsRepository = channelsRepository;
    }

    /**
     * {@code POST  /channels} : Create a new channels.
     *
     * @param channels the channels to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new channels, or with status {@code 400 (Bad Request)} if the channels has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Channels> createChannels(@Valid @RequestBody Channels channels) throws URISyntaxException {
        log.debug("REST request to save Channels : {}", channels);
        if (channels.getId() != null) {
            throw new BadRequestAlertException("A new channels cannot already have an ID", ENTITY_NAME, "idexists");
        }
        channels = channelsService.save(channels);
        return ResponseEntity.created(new URI("/api/channels/" + channels.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, channels.getId()))
            .body(channels);
    }

    /**
     * {@code PUT  /channels/:id} : Updates an existing channels.
     *
     * @param id the id of the channels to save.
     * @param channels the channels to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated channels,
     * or with status {@code 400 (Bad Request)} if the channels is not valid,
     * or with status {@code 500 (Internal Server Error)} if the channels couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Channels> updateChannels(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody Channels channels
    ) throws URISyntaxException {
        log.debug("REST request to update Channels : {}, {}", id, channels);
        if (channels.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, channels.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!channelsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        channels = channelsService.update(channels);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, channels.getId()))
            .body(channels);
    }

    /**
     * {@code PATCH  /channels/:id} : Partial updates given fields of an existing channels, field will ignore if it is null
     *
     * @param id the id of the channels to save.
     * @param channels the channels to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated channels,
     * or with status {@code 400 (Bad Request)} if the channels is not valid,
     * or with status {@code 404 (Not Found)} if the channels is not found,
     * or with status {@code 500 (Internal Server Error)} if the channels couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Channels> partialUpdateChannels(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody Channels channels
    ) throws URISyntaxException {
        log.debug("REST request to partial update Channels partially : {}, {}", id, channels);
        if (channels.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, channels.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!channelsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Channels> result = channelsService.partialUpdate(channels);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, channels.getId())
        );
    }

    /**
     * {@code GET  /channels} : get all the channels.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of channels in body.
     */
    @GetMapping("")
    public ResponseEntity<List<Channels>> getAllChannels(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Channels");
        Page<Channels> page = channelsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /channels/:id} : get the "id" channels.
     *
     * @param id the id of the channels to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the channels, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Channels> getChannels(@PathVariable("id") String id) {
        log.debug("REST request to get Channels : {}", id);
        Optional<Channels> channels = channelsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(channels);
    }

    /**
     * {@code DELETE  /channels/:id} : delete the "id" channels.
     *
     * @param id the id of the channels to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChannels(@PathVariable("id") String id) {
        log.debug("REST request to delete Channels : {}", id);
        channelsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
