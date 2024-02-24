package com.myflowdb.controller;

import com.myflowdb.entity.Endpoint;
import com.myflowdb.entity.Flow;
import com.myflowdb.entity.UserTag;
import com.myflowdb.service.EndpointService;
import com.myflowdb.service.FlowService;
import com.myflowdb.service.TagService;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("flowdb/api/flows")
public class FlowController {
    private static final Logger logger = LoggerFactory.getLogger(FlowController.class);
    FlowService flowService;
    EndpointService endpointService;
    TagService tagService;

    public FlowController(FlowService flowService, EndpointService endpointService,TagService tagService) {
        this.flowService = flowService;
        this.endpointService = endpointService;
        this.tagService = tagService;
    }

    // create flow
    @PostMapping(produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Flow> saveFlow(@RequestBody Flow flow) {
            Flow savedFlow = flowService.saveFlow(flow);
            return new ResponseEntity<>(savedFlow, HttpStatus.OK);
    }

// Get flow by Name
    @GetMapping
    public ResponseEntity<Flow> getFlowByName(@RequestParam String name){
           Optional<Flow> optionalFlow = flowService.findByName(name);
           return new ResponseEntity<>(optionalFlow.get(),HttpStatus.OK);
    }

    // Delete single flow
    // http://localhost:8081/flowdb/api/flows/10
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlow(@PathVariable Long id){
        flowService.deleteFlowById(id);
        return new ResponseEntity<>("Flow deleted successfully",HttpStatus.OK);
    }

    // set endpoint for particular flow.
//    http://localhost:8081/flowdb/api/flows/10/setEndpoint?endpointName=Devaraj&seq=0
    @PostMapping(value = "/{id}/setEndpoint", produces = {"application/json"})
    public ResponseEntity<Flow> setEndpointForFlow(@NotNull @PathVariable Long id,
                                                   @RequestParam String endpointName,
                                                   @NonNull @RequestParam(name = "seq",
                                                           required = true) int seq){
        boolean updateOtherEndpoint = false;
        Optional<Flow> optionalFlow = flowService.getByIdFlow(id);
        Optional<Endpoint> endpointOptional = endpointService.getEndpointByName(endpointName);
        if (endpointOptional.isEmpty()) {
            logger.debug("No endpoint is available with name %s, hence saving it as new endpoint", endpointName);
            Endpoint.EndpointBuilder<?, ?> builder = Endpoint.builder().endpointName(endpointName).build().toBuilder();
            Endpoint endpoint = builder.build();
            endpointService.createEndpoint(endpoint);
            logger.debug("new endpoint %s saved successfully", endpointName);
            endpointOptional = endpointService.getEndpointByName(endpointName);
        }
        if (endpointOptional.isPresent()) {
            List<Endpoint> flowEndPoints = optionalFlow.get().getEndpoints();
            if (flowEndPoints == null || flowEndPoints.isEmpty()) {
                optionalFlow.get().setEndpoints(Arrays.asList(new Endpoint[2]));
                if(!endpointService.getEndpointByName("").isPresent()){
                    Endpoint endpoint1 = Endpoint.builder().endpointName("").build();
                    endpointService.createEndpoint(endpoint1);
                }
                updateOtherEndpoint = true;
            }
            setEndpointsForFlow(seq, endpointOptional.get(), optionalFlow, updateOtherEndpoint);
        }
        // Flow is not getting save need to work
//        Flow flow = flowService.saveFlow(optionalFlow.get());
        logger.debug("Endpoint %s successfully added to the flow %s", endpointName, id);
        return new ResponseEntity<>(optionalFlow.get(),HttpStatus.OK);

    }
    private void setEndpointsForFlow(int seq, Endpoint endpoint, Optional<Flow> flowOptional, boolean updateOtherEndpoint) {
        try {
            if(updateOtherEndpoint){
                if (seq == 0) {
                    flowOptional.get().getEndpoints().set(1, endpointService.getEndpointByName("").get());
                } else {
                    flowOptional.get().getEndpoints().set(0, endpointService.getEndpointByName("").get());
                }
            }

            flowOptional.get().getEndpoints().set(seq, endpoint);
        } catch (Exception e) {
            logger.error("Failed to set endpoint for the flow %s due to %s", flowOptional.get().getFlowName(), e);
        }
    }

    @PostMapping("/{id}/setAnchor")
    // http://localhost:8081/flowdb/api/flows/10/setAnchor?anchorName=Devaraj
    public ResponseEntity<Flow> setAnchorForFlow(@PathVariable String id,
                                                 @RequestParam String anchorName){

        Optional<Flow> flowOptional = flowService.getByIdFlow(Long.parseLong(id));
        if(flowOptional.isPresent()){
            flowOptional.get().setAnchor(anchorName);
            flowService.saveFlow(flowOptional.get());
            String msg = "Anchor " + anchorName + " for the flow " + id + " is updated successfully";
            logger.debug(msg);
            return new ResponseEntity<>(flowOptional.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(flowOptional.get(),HttpStatus.BAD_REQUEST);

    }

    // set tag for a flow
    // http://localhost:8081/flowdb/api/flows/10/setTag?tagName=Devaraj&tagValue=pradhan
    @PostMapping("/{id}/setTag")
    public ResponseEntity<Flow> setTagForFlow(@PathVariable String id,
                                              @RequestParam String tagName,
                                              @RequestParam String tagValue) {
        Optional<UserTag> optionalUserTag = tagService.findOneByTagNameAndTagValue(tagName,tagValue);
        if(optionalUserTag.isEmpty()){
            optionalUserTag = Optional.of(new UserTag());
            optionalUserTag.get().setTagName(tagName);
            optionalUserTag.get().setTagValue(tagValue);
        }
        else{
            optionalUserTag.get().setTagName(tagName);
            optionalUserTag.get().setTagValue(tagValue);
        }
        optionalUserTag = Optional.of(tagService.createTag(optionalUserTag.get()));
        Optional<Flow> optionalFlow = flowService.getByIdFlow(Long.parseLong(id));
        optionalFlow.get().getTags().add(optionalUserTag.get());
        logger.debug("Tag name %s successfully updated for the flow %s", tagName, id);
        flowService.saveFlow(optionalFlow.get());
        return new ResponseEntity<>(optionalFlow.get(),HttpStatus.OK);
    }

    @GetMapping("/tags/tagNamesForFlows")
    public ResponseEntity<List<String>> getTagNamesForFlows(){
        try {
            List<String> tagNames = tagService.getAllFlowTagNames();
            if (tagNames.isEmpty()) {
                return new ResponseEntity<>(List.of("No-TAGS-FOUND"), HttpStatus.NO_CONTENT);
            }
            logger.info("Tag names for flows retrieved successfully");
            return new ResponseEntity<>(tagNames,HttpStatus.OK);
        }
        catch (Exception e){
            logger.error("Failed to get tag names for the available flows due to %s", e);
            return new ResponseEntity<>(List.of("No tags found"), HttpStatus.BAD_GATEWAY);
        }
    }

    // http://localhost:8081/flowdb/api/flows/Devaraj/tagValuesForFlowByTagName
    @GetMapping("/{tagName}/tagValuesForFlowByTagName")
    public  ResponseEntity<List<String>> getTagValuesForFlowByTagName(@PathVariable String tagName){
        try {
            List<String> tagValues = tagService.findTagValuesForFlowByTagName(tagName);
            if(tagValues.isEmpty()){
                logger.info("No value available for this %",tagName);
                return new ResponseEntity<>(List.of("NO_TAG_VALUE"),HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tagValues,HttpStatus.OK);
        }
        catch (Exception e){
            logger.info("Not able retrieve tag value due to %",e);
            return new ResponseEntity<>(List.of("NO_TAG_VALUE"),HttpStatus.BAD_REQUEST);
        }
    }

}
