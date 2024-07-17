package com.laofansay.work.repository;

import com.laofansay.work.domain.Channels;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Channels entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChannelsRepository extends MongoRepository<Channels, String> {}
