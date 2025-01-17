package com.kubassile.kubassile.domain.payments.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.kubassile.kubassile.domain.payments.dtos.PaymentStatusDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentStatus {
    PENDING(1L, "Pendente"),
    REALIZED(2L, "Realizado");

    private final Long id;
    private final String label;

    public static List<PaymentStatusDto> getAll() {
        return Arrays.stream(PaymentStatus.values())
                .map(
                        data -> new PaymentStatusDto(data.id, data.label))
                .collect(Collectors.toList());
    }

    public static PaymentStatusDto fromId(Long id) {
        for (PaymentStatus status : PaymentStatus.values()) {
            if (status.id == id) {
                return new PaymentStatusDto(status.getId(), status.getLabel());
            }
        }

        throw new IllegalArgumentException("Invalid id");
    }
}
