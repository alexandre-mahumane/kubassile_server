package com.kubassile.kubassile.domain.order.dtos;

import com.kubassile.kubassile.domain.order.Order;

public record OrderDataResponseDto(
                Order order,
                Double value,
                Long paymentStatusId,
                Long paymentMethodId,
                String created_at,
                String updated_at

) {

}
