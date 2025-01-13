package com.kubassile.kubassile.domain.payments;

import com.kubassile.kubassile.domain.order.Order;
import com.kubassile.kubassile.domain.order.enums.Status;
import com.kubassile.kubassile.domain.payments.enums.Method;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "payments")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    @Column(name = "payment_status_id")
    private Status paymentStatus;

    private Double value;

    @Column(name = "method_id")
    private Method paymentMethod;
    
}
