package com.laofansay.work.service;

import com.laofansay.work.domain.Channels;
import com.laofansay.work.repository.ChannelsRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.laofansay.work.domain.Channels}.
 */
@Service
public class ChannelsService {

    private static final Logger log = LoggerFactory.getLogger(ChannelsService.class);

    private final ChannelsRepository channelsRepository;

    public ChannelsService(ChannelsRepository channelsRepository) {
        this.channelsRepository = channelsRepository;
    }

    /**
     * Save a channels.
     *
     * @param channels the entity to save.
     * @return the persisted entity.
     */
    public Channels save(Channels channels) {
        log.debug("Request to save Channels : {}", channels);
        return channelsRepository.save(channels);
    }

    /**
     * Update a channels.
     *
     * @param channels the entity to save.
     * @return the persisted entity.
     */
    public Channels update(Channels channels) {
        log.debug("Request to update Channels : {}", channels);
        return channelsRepository.save(channels);
    }

    /**
     * Partially update a channels.
     *
     * @param channels the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<Channels> partialUpdate(Channels channels) {
        log.debug("Request to partially update Channels : {}", channels);

        return channelsRepository
            .findById(channels.getId())
            .map(existingChannels -> {
                if (channels.getName() != null) {
                    existingChannels.setName(channels.getName());
                }
                if (channels.getCategory() != null) {
                    existingChannels.setCategory(channels.getCategory());
                }
                if (channels.getRate() != null) {
                    existingChannels.setRate(channels.getRate());
                }
                if (channels.getProps() != null) {
                    existingChannels.setProps(channels.getProps());
                }
                if (channels.getOpen() != null) {
                    existingChannels.setOpen(channels.getOpen());
                }

                return existingChannels;
            })
            .map(channelsRepository::save);
    }

    /**
     * Get all the channels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<Channels> findAll(Pageable pageable) {
        log.debug("Request to get all Channels");
        return channelsRepository.findAll(pageable);
    }

    /**
     * Get one channels by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Channels> findOne(String id) {
        log.debug("Request to get Channels : {}", id);
        return channelsRepository.findById(id);
    }

    /**
     * Delete the channels by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Channels : {}", id);
        channelsRepository.deleteById(id);
    }
}
