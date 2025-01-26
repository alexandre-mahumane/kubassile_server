package com.kubassile.kubassile.domain.client.dto;

public record ClientResponseDto(
                Long id,
                String clientName,
                String phone,
                String createdAt) {

}
