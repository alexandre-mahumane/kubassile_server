package com.kubassile.kubassile.domain.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Roles {
    EMPLOYEE("employee"),
    ADMIN("admin");

    private final String role;

}
