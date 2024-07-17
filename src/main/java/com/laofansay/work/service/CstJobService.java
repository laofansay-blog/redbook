package com.laofansay.work.service;

import com.laofansay.work.domain.CstJob;
import com.laofansay.work.repository.CstJobRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.laofansay.work.domain.CstJob}.
 */
@Service
public class CstJobService {

    private static final Logger log = LoggerFactory.getLogger(CstJobService.class);

    private final CstJobRepository cstJobRepository;

    public CstJobService(CstJobRepository cstJobRepository) {
        this.cstJobRepository = cstJobRepository;
    }

    /**
     * Save a cstJob.
     *
     * @param cstJob the entity to save.
     * @return the persisted entity.
     */
    public CstJob save(CstJob cstJob) {
        log.debug("Request to save CstJob : {}", cstJob);
        return cstJobRepository.save(cstJob);
    }

    /**
     * Update a cstJob.
     *
     * @param cstJob the entity to save.
     * @return the persisted entity.
     */
    public CstJob update(CstJob cstJob) {
        log.debug("Request to update CstJob : {}", cstJob);
        return cstJobRepository.save(cstJob);
    }

    /**
     * Partially update a cstJob.
     *
     * @param cstJob the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CstJob> partialUpdate(CstJob cstJob) {
        log.debug("Request to partially update CstJob : {}", cstJob);

        return cstJobRepository
            .findById(cstJob.getId())
            .map(existingCstJob -> {
                if (cstJob.getName() != null) {
                    existingCstJob.setName(cstJob.getName());
                }
                if (cstJob.getExecuteType() != null) {
                    existingCstJob.setExecuteType(cstJob.getExecuteType());
                }
                if (cstJob.getCategory() != null) {
                    existingCstJob.setCategory(cstJob.getCategory());
                }
                if (cstJob.getStatus() != null) {
                    existingCstJob.setStatus(cstJob.getStatus());
                }
                if (cstJob.getCreatedDate() != null) {
                    existingCstJob.setCreatedDate(cstJob.getCreatedDate());
                }
                if (cstJob.getJobProps() != null) {
                    existingCstJob.setJobProps(cstJob.getJobProps());
                }
                if (cstJob.getChannel() != null) {
                    existingCstJob.setChannel(cstJob.getChannel());
                }

                return existingCstJob;
            })
            .map(cstJobRepository::save);
    }

    /**
     * Get all the cstJobs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<CstJob> findAll(Pageable pageable) {
        log.debug("Request to get all CstJobs");
        return cstJobRepository.findAll(pageable);
    }

    /**
     * Get one cstJob by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<CstJob> findOne(String id) {
        log.debug("Request to get CstJob : {}", id);
        return cstJobRepository.findById(id);
    }

    /**
     * Delete the cstJob by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete CstJob : {}", id);
        cstJobRepository.deleteById(id);
    }
}
