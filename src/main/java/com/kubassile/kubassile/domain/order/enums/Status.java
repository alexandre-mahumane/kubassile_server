package com.kubassile.kubassile.domain.order.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.kubassile.kubassile.domain.order.dtos.StatusDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    READY(1L, "Pronto"),
    DELIVERED(2L, "Entregue"),
    PENDING(3L, "Pendente");

    private final Long id;
    private final String label;

    public static List<StatusDto> getAll(){
        return Arrays.stream(Status.values())
            .map((status)-> 
                new StatusDto(status.getId(), status.getLabel()))
                .collect(Collectors.toList());
        
    }
    public static StatusDto fromId(Long id){
        for(Status status: Status.values()){
            if(status.id == id){
                return new StatusDto(status.id, status.label);
            }

        }
        throw new IllegalArgumentException("Invalid status id"); 
    }
}
