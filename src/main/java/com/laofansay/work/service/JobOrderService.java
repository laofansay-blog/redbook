package com.laofansay.work.service;

import com.laofansay.work.domain.JobOrder;
import com.laofansay.work.repository.JobOrderRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.laofansay.work.domain.JobOrder}.
 */
@Service
public class JobOrderService {

    private static final Logger log = LoggerFactory.getLogger(JobOrderService.class);

    private final JobOrderRepository jobOrderRepository;

    public JobOrderService(JobOrderRepository jobOrderRepository) {
        this.jobOrderRepository = jobOrderRepository;
    }

    /**
     * Save a jobOrder.
     *
     * @param jobOrder the entity to save.
     * @return the persisted entity.
     */
    public JobOrder save(JobOrder jobOrder) {
        log.debug("Request to save JobOrder : {}", jobOrder);
        return jobOrderRepository.save(jobOrder);
    }

    /**
     * Update a jobOrder.
     *
     * @param jobOrder the entity to save.
     * @return the persisted entity.
     */
    public JobOrder update(JobOrder jobOrder) {
        log.debug("Request to update JobOrder : {}", jobOrder);
        return jobOrderRepository.save(jobOrder);
    }

    /**
     * Partially update a jobOrder.
     *
     * @param jobOrder the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<JobOrder> partialUpdate(JobOrder jobOrder) {
        log.debug("Request to partially update JobOrder : {}", jobOrder);

        return jobOrderRepository
            .findById(jobOrder.getId())
            .map(existingJobOrder -> {
                if (jobOrder.getSettlementOrderNo() != null) {
                    existingJobOrder.setSettlementOrderNo(jobOrder.getSettlementOrderNo());
                }
                if (jobOrder.getAmount() != null) {
                    existingJobOrder.setAmount(jobOrder.getAmount());
                }
                if (jobOrder.getPaymentStatus() != null) {
                    existingJobOrder.setPaymentStatus(jobOrder.getPaymentStatus());
                }
                if (jobOrder.getSettlementDate() != null) {
                    existingJobOrder.setSettlementDate(jobOrder.getSettlementDate());
                }
                if (jobOrder.getPaymentDate() != null) {
                    existingJobOrder.setPaymentDate(jobOrder.getPaymentDate());
                }
                if (jobOrder.getCustomerId() != null) {
                    existingJobOrder.setCustomerId(jobOrder.getCustomerId());
                }
                if (jobOrder.getChannel() != null) {
                    existingJobOrder.setChannel(jobOrder.getChannel());
                }

                return existingJobOrder;
            })
            .map(jobOrderRepository::save);
    }

    /**
     * Get all the jobOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<JobOrder> findAll(Pageable pageable) {
        log.debug("Request to get all JobOrders");
        return jobOrderRepository.findAll(pageable);
    }

    /**
     * Get all the jobOrders with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<JobOrder> findAllWithEagerRelationships(Pageable pageable) {
        return jobOrderRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one jobOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<JobOrder> findOne(String id) {
        log.debug("Request to get JobOrder : {}", id);
        return jobOrderRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the jobOrder by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete JobOrder : {}", id);
        jobOrderRepository.deleteById(id);
    }
}
