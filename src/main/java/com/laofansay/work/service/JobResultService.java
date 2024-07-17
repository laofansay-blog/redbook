package com.laofansay.work.service;

import com.laofansay.work.domain.JobResult;
import com.laofansay.work.repository.JobResultRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.laofansay.work.domain.JobResult}.
 */
@Service
public class JobResultService {

    private static final Logger log = LoggerFactory.getLogger(JobResultService.class);

    private final JobResultRepository jobResultRepository;

    public JobResultService(JobResultRepository jobResultRepository) {
        this.jobResultRepository = jobResultRepository;
    }

    /**
     * Save a jobResult.
     *
     * @param jobResult the entity to save.
     * @return the persisted entity.
     */
    public JobResult save(JobResult jobResult) {
        log.debug("Request to save JobResult : {}", jobResult);
        return jobResultRepository.save(jobResult);
    }

    /**
     * Update a jobResult.
     *
     * @param jobResult the entity to save.
     * @return the persisted entity.
     */
    public JobResult update(JobResult jobResult) {
        log.debug("Request to update JobResult : {}", jobResult);
        return jobResultRepository.save(jobResult);
    }

    /**
     * Partially update a jobResult.
     *
     * @param jobResult the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<JobResult> partialUpdate(JobResult jobResult) {
        log.debug("Request to partially update JobResult : {}", jobResult);

        return jobResultRepository
            .findById(jobResult.getId())
            .map(existingJobResult -> {
                if (jobResult.getName() != null) {
                    existingJobResult.setName(jobResult.getName());
                }
                if (jobResult.getJobUrl() != null) {
                    existingJobResult.setJobUrl(jobResult.getJobUrl());
                }
                if (jobResult.getAuthorName() != null) {
                    existingJobResult.setAuthorName(jobResult.getAuthorName());
                }
                if (jobResult.getAccountId() != null) {
                    existingJobResult.setAccountId(jobResult.getAccountId());
                }
                if (jobResult.getCustomerId() != null) {
                    existingJobResult.setCustomerId(jobResult.getCustomerId());
                }
                if (jobResult.getStatus() != null) {
                    existingJobResult.setStatus(jobResult.getStatus());
                }
                if (jobResult.getJobDate() != null) {
                    existingJobResult.setJobDate(jobResult.getJobDate());
                }
                if (jobResult.getJobNo() != null) {
                    existingJobResult.setJobNo(jobResult.getJobNo());
                }
                if (jobResult.getBuilderDate() != null) {
                    existingJobResult.setBuilderDate(jobResult.getBuilderDate());
                }
                if (jobResult.getReplay() != null) {
                    existingJobResult.setReplay(jobResult.getReplay());
                }
                if (jobResult.getReplayTheme() != null) {
                    existingJobResult.setReplayTheme(jobResult.getReplayTheme());
                }
                if (jobResult.getReplayImage() != null) {
                    existingJobResult.setReplayImage(jobResult.getReplayImage());
                }
                if (jobResult.getReplayImageContentType() != null) {
                    existingJobResult.setReplayImageContentType(jobResult.getReplayImageContentType());
                }
                if (jobResult.getReplayDate() != null) {
                    existingJobResult.setReplayDate(jobResult.getReplayDate());
                }
                if (jobResult.getEffReplay() != null) {
                    existingJobResult.setEffReplay(jobResult.getEffReplay());
                }
                if (jobResult.getSettlement() != null) {
                    existingJobResult.setSettlement(jobResult.getSettlement());
                }
                if (jobResult.getSettlementOrder() != null) {
                    existingJobResult.setSettlementOrder(jobResult.getSettlementOrder());
                }
                if (jobResult.getSettlementDate() != null) {
                    existingJobResult.setSettlementDate(jobResult.getSettlementDate());
                }
                if (jobResult.getErrorMsg() != null) {
                    existingJobResult.setErrorMsg(jobResult.getErrorMsg());
                }
                if (jobResult.getErrorImage() != null) {
                    existingJobResult.setErrorImage(jobResult.getErrorImage());
                }
                if (jobResult.getErrorImageContentType() != null) {
                    existingJobResult.setErrorImageContentType(jobResult.getErrorImageContentType());
                }
                if (jobResult.getChannel() != null) {
                    existingJobResult.setChannel(jobResult.getChannel());
                }

                return existingJobResult;
            })
            .map(jobResultRepository::save);
    }

    /**
     * Get all the jobResults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<JobResult> findAll(Pageable pageable) {
        log.debug("Request to get all JobResults");
        return jobResultRepository.findAll(pageable);
    }

    /**
     * Get one jobResult by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<JobResult> findOne(String id) {
        log.debug("Request to get JobResult : {}", id);
        return jobResultRepository.findById(id);
    }

    /**
     * Delete the jobResult by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete JobResult : {}", id);
        jobResultRepository.deleteById(id);
    }
}
