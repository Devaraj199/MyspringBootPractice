package com.myflowdb.service;

import com.myflowdb.repository.FlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlowMuteUnmuteService {

    @Autowired
    FlowRepository flowRepository;

    public void muteFlows(String name){
        flowRepository.muteFlow(name);
    }

    public void unMuteFlows(String name){
        flowRepository.unMuteFlow(name);
    }

    public void muteAlarmFlow(String name){
        flowRepository.muteAlarmFlow(name);
    }

    public void unMuteAlarmFlow(String name){
        flowRepository.unMuteAlarmFlow(name);
    }
}
