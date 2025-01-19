package com.kubassile.kubassile.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Roles {
    EMPLOYEE(1),
    ADMIN(2);

    private final Integer id;

    public static String fromId(Integer id) {
        for (Roles roles : Roles.values()) {
            if (roles.id == id) {
                return roles.name();
            }
        }

        throw new IllegalArgumentException("Invalid Id");
    }
}
