package com.viavi.stockservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="OrderEvents")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderEvents {
    @Id
    private String orderId;
    private String message;
    private String status;
    private String name;
    private int qty;
    private  double price;
}
