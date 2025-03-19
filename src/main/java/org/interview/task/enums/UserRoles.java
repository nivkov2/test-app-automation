package org.interview.task.enums;

import lombok.Getter;

@Getter
public enum UserRoles {
    ADMIN("admin"),
    SUPERVISOR("supervisor");

    private String value;

    UserRoles(String value) {
        this.value = value;
    }
}
