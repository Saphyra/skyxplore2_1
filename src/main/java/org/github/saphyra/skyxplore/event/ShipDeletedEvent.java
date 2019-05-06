package org.github.saphyra.skyxplore.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ShipDeletedEvent {
    private final String shipId;
}
