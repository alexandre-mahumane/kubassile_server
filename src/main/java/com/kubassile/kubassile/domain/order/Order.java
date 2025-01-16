package com.kubassile.kubassile.domain.order;

import com.kubassile.kubassile.domain.client.Client;
import com.kubassile.kubassile.domain.order.dtos.StatusDto;
import com.kubassile.kubassile.domain.order.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client clientId;

    @Column(name = "order_status_id", nullable = false)
    private Long orderStatusId;
    
    @Transient
    private StatusDto orderStatus;
    
    @Column(name = "order_type")
    private String type;
    
    private String description;
    
    
    public void setOrderStatusId(Long orderStatusId) {
        this.orderStatusId = orderStatusId;
        this.orderStatus = Status.fromId(orderStatusId);
       
    }
    public void setOrderStatus(StatusDto orderStatus) {
        this.orderStatus = orderStatus;
        this.orderStatusId = orderStatus.id();
    }
}
