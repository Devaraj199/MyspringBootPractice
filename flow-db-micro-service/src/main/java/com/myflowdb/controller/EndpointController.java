package com.myflowdb.controller;

import com.myflowdb.entity.Endpoint;
import com.myflowdb.service.EndpointService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("flowdb/api/endpoint")
public class EndpointController {
    @Autowired
    private EndpointService endpointService;
    @PostMapping
    public ResponseEntity<Endpoint> createEndpoint(@RequestBody Endpoint endpoint){
            Endpoint savedEndpoint = endpointService.createEndpoint(endpoint);
            return new ResponseEntity<>(savedEndpoint, HttpStatus.OK);

    }
}
