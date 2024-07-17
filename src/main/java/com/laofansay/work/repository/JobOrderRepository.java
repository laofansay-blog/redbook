package com.laofansay.work.repository;

import com.laofansay.work.domain.JobOrder;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the JobOrder entity.
 */
@Repository
public interface JobOrderRepository extends MongoRepository<JobOrder, String> {
    @Query("{}")
    Page<JobOrder> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<JobOrder> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<JobOrder> findOneWithEagerRelationships(String id);
}
