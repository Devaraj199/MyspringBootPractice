package com.myflowdb.controller;

import com.myflowdb.service.FlowMuteUnmuteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("flowdb/api/flows")
public class FlowMuteUnmuteController {
    private static final Logger logger = LoggerFactory.getLogger(FlowMuteUnmuteController.class);
    @Autowired
    FlowMuteUnmuteService flowMuteUnmuteService;

        @PostMapping("/mute")
        public ResponseEntity<String> muteFlows(@RequestBody List<String> flows){
            logger.info("list of flows %s",flows);
            try {
                if (Objects.nonNull(flows) && !(flows.isEmpty())) {
                    flows.forEach(flowMuteUnmuteService::muteFlows);
                    return new ResponseEntity<String>("Flows are muted successfully", HttpStatus.OK);
                }
                return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
            }
            catch (Exception e){
                logger.info("error %s",e);
                return ResponseEntity.internalServerError().body("Provide valid flows");
            }
        }
        @PostMapping("/unmute")
        public ResponseEntity<String> unMuteFlows(@RequestBody List<String> flows){

        try {
            if (Objects.nonNull(flows) && !(flows.isEmpty())) {
                flows.forEach(flowMuteUnmuteService::unMuteFlows);
                return new ResponseEntity<String>("Flows are unmuted successfully", HttpStatus.OK);
            }
            return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            logger.info("error %s",e);
            return ResponseEntity.internalServerError().body("Provide valid flows");
        }
    }

    // /flowdb/api/flows/alarm/mute

    @PostMapping("/alarm/mute")
    public ResponseEntity<String> muteAlarmFlows(@RequestBody List<String> flows){
        logger.info("list of flows %s",flows);
        try {
            if (Objects.nonNull(flows) && !(flows.isEmpty())) {
                flows.forEach(flowMuteUnmuteService::muteAlarmFlow);
                return new ResponseEntity<String>("Muting of alarms for flows was successful", HttpStatus.OK);
            }
            return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            logger.info("error %s",e);
            return ResponseEntity.internalServerError().body("Provide valid flows");
        }
    }

    @PostMapping("/alarm/unmute")
    public ResponseEntity<String> muteUnAlarmFlows(@RequestBody List<String> flows){
        logger.info("list of flows %s",flows);
        try {
            if (Objects.nonNull(flows) && !(flows.isEmpty())) {
                flows.forEach(flowMuteUnmuteService::unMuteAlarmFlow);
                return new ResponseEntity<String>("UnMuting of alarms for flows was successful", HttpStatus.OK);
            }
            return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            logger.info("error %s",e);
            return ResponseEntity.internalServerError().body("Provide valid flows");
        }
    }
}
