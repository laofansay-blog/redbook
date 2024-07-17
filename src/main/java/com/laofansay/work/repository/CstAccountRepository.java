package com.laofansay.work.repository;

import com.laofansay.work.domain.CstAccount;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the CstAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CstAccountRepository extends MongoRepository<CstAccount, String> {}
