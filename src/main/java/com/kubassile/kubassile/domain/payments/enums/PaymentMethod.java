package com.kubassile.kubassile.domain.payments.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.kubassile.kubassile.domain.payments.dtos.PaymentStatusDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentMethod {
    MONEY(1L, "Dinheiro"),
    CARD(2L, "Cartao"),
    TRANSFER(3L, "Transferencia");

    private final Long id;
    private final String label;

    public static List<PaymentStatusDto> getAll() {
        return Arrays.stream(PaymentMethod.values())
                .map(data -> new PaymentStatusDto(data.id, data.label))
                .collect(Collectors.toList());
    }

    public static PaymentStatusDto fromId(Long id) {
        for (PaymentMethod method : PaymentMethod.values()) {
            if (method.id == id) {
                return new PaymentStatusDto(method.id, method.label);
            }
        }

        throw new IllegalArgumentException("Invalid Id");
    }
}
