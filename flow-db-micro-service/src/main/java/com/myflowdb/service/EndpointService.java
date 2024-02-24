package com.myflowdb.service;

import com.myflowdb.entity.Endpoint;
import com.myflowdb.repository.EndpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EndpointService  {

    @Autowired
    private EndpointRepository endpointRepository;

    public Endpoint createEndpoint(Endpoint endpoint){
       return endpointRepository.save(endpoint);
    }

    public Optional<Endpoint> getEndpointByName(String name){
        return endpointRepository.findOneByEndpointName(name);
    }
}
