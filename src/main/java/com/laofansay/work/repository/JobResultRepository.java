package com.laofansay.work.repository;

import com.laofansay.work.domain.JobResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the JobResult entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobResultRepository extends MongoRepository<JobResult, String> {}
