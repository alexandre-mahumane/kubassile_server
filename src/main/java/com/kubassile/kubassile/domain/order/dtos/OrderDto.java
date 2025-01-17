package com.kubassile.kubassile.domain.order.dtos;

public record OrderDto(
        String clientName,
        String phone,
        Long orderStatusId,
        String orderType,
        String description,
        Long paymentStatusId,
        Long paymentMethodId,
        Double value) {

}
