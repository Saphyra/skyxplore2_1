package com.github.saphyra.skyxplore.common.event;

import lombok.Data;

@Data
public class AccountDeletedEvent {
    private final String userId;
}
