package com.myflowdb.repository;

import com.myflowdb.entity.Endpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EndpointRepository extends JpaRepository<Endpoint, Long> {
    Optional<Endpoint> findOneByEndpointName(String name);
}
