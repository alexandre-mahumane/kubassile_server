package com.kubassile.kubassile.domain.payments.dtos;

import com.kubassile.kubassile.domain.order.Order;

public record PaymentDto(
                Order order,
                Long paymentStatusId,
                Double value,
                Long paymentMethodId) {

}
