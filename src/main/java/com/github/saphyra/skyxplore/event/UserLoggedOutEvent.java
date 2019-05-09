package com.github.saphyra.skyxplore.event;

import lombok.Data;

@Data
public class UserLoggedOutEvent {
    private final String userId;
}
