package com.kubassile.kubassile.domain.order.dtos;

import com.kubassile.kubassile.domain.client.Client;

public record OrderResponseDto(
    Client client,
    Long orderStatusId,
    String orderType,
    String description

) {
    
}
