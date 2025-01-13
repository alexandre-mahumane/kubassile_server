package com.kubassile.kubassile.domain.payments.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Method {
    MONEY(1L),
    CARD(2L),
    TRANSFER(3L);

    private final Long id;

    public static Method fromId(Long id){
        for(Method method: Method.values()){
            if(method.id == id){
                return method;
            }
        }

        throw new IllegalArgumentException("Invalid Id");
    }
}
