package com.github.saphyra.skyxplore.common.event;

import lombok.Data;

@Data
public class UserLoggedOutEvent {
    private final String userId;
}
