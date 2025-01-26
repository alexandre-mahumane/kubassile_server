package com.kubassile.kubassile.domain.payments;

import java.time.LocalDateTime;

import org.hibernate.annotations.CurrentTimestamp;

import com.kubassile.kubassile.domain.order.Order;
import com.kubassile.kubassile.domain.payments.dtos.PaymentStatusDto;
import com.kubassile.kubassile.domain.payments.enums.PaymentMethod;
import com.kubassile.kubassile.domain.payments.enums.PaymentStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
    private Long paymentStatusId;

    private Double value;

    @Column(name = "method_id")
    private Long paymentMethodId;

    @Column(name = "created_at")
    @CurrentTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @CurrentTimestamp
    private LocalDateTime updatedAt;
    @Transient
    private PaymentStatusDto paymentStatus;

    @Transient
    private PaymentStatusDto paymentMethod;

    public void setPaymentStatusId(Long paymentStatusId) {
        this.paymentStatusId = paymentStatusId;
        this.paymentStatus = PaymentStatus.fromId(paymentStatusId);

    }

    public void setPaymentStatus(PaymentStatusDto paymentStatus) {
        this.paymentStatus = paymentStatus;
        this.paymentStatusId = paymentStatus.id();
    }

    public void setPaymentMethodId(Long paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
        this.paymentMethod = PaymentMethod.fromId(paymentMethodId);

    }

    public void setpaymentMethod(PaymentStatusDto paymentMethod) {
        this.paymentMethod = paymentMethod;
        this.paymentMethodId = paymentMethod.id();
    }

}
