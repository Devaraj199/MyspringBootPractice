package com.myflowdb.service;

import com.myflowdb.entity.Flow;
import com.myflowdb.repository.FlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FlowService {

    @Autowired
    private FlowRepository flowRepository;

    public Flow saveFlow(Flow flow) {
       return flowRepository.save(flow);
    }

    public Optional<Flow> findByName(String name){
        return flowRepository.findOneByFlowName(name);
    }

    public Optional<Flow> getByIdFlow(Long id){
        return flowRepository.findById(id);
    }
    public void deleteFlowById(Long id){
        this.flowRepository.deleteById(id);
    }
}
