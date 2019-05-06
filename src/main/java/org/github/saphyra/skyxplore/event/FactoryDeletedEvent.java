package org.github.saphyra.skyxplore.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FactoryDeletedEvent {
    private final String factoryId;
}
