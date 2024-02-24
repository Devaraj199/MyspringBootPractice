package com.viavi.stockservice.kafkaconfig;

import com.viavi.basedomains.dto.OrderEvent;
import com.viavi.stockservice.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class StockConsumer {
    private static final Logger logger = LoggerFactory.getLogger(StockConsumer.class);
    @Autowired
    private StockService service;
    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumer(OrderEvent event){
        logger.info(String.format("Order event is received here>...%s",event.toString()));
        service.saveOrderEvent(event);
    }
}
