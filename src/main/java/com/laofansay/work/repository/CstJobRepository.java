package com.laofansay.work.repository;

import com.laofansay.work.domain.CstJob;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the CstJob entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CstJobRepository extends MongoRepository<CstJob, String> {}
