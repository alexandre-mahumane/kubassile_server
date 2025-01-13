package com.kubassile.kubassile.domain.payments.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    PENDING(1L),
    REALIZED(2L);

    private final Long id;

    public static Status fromId(Long id){
        for(Status status: Status.values()){
            if(status.id == id){
                return status;
            }
        }

        throw new IllegalArgumentException("Invalid id");
    }
}
