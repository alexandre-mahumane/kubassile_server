package com.kubassile.kubassile.domain.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    READY(1L),
    DELIVERED(2L),
    PENDING(3L);

    private final Long id;

    public static Status fromId(Long id){
        for(Status status: Status.values()){
            if(status.id == id){
                return status;
            }

        }
        throw new IllegalArgumentException("Invalid status id"); 
    }
}
