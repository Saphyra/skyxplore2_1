package org.github.saphyra.skyxplore.event;

import lombok.Data;

@Data
public class AccountDeletedEvent {
    private final String userId;
}
