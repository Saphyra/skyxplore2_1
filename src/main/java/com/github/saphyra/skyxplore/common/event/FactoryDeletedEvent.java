package com.github.saphyra.skyxplore.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FactoryDeletedEvent {
    private final String factoryId;
}
