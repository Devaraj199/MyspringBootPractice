package com.viavi.stockservice.service;

import com.viavi.basedomains.dto.OrderEvent;
import com.viavi.stockservice.entity.OrderEvents;
import com.viavi.stockservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    @Autowired
    private OrderRepository orderRepository;

    public void saveOrderEvent(OrderEvent orderEvent){
        OrderEvents orderEvents = new OrderEvents();
        orderEvents.setOrderId(orderEvent.getOrder().getOrderId());
        orderEvents.setName(orderEvent.getOrder().getName());
        orderEvents.setQty(orderEvent.getOrder().getQty());
        orderEvents.setStatus(orderEvent.getStatus());
        orderEvents.setPrice(orderEvent.getOrder().getPrice());
        orderEvents.setMessage(orderEvent.getMessage());
        orderEvents.setPrice(orderEvent.getOrder().getPrice());
        orderRepository.save(orderEvents);
    }
}
