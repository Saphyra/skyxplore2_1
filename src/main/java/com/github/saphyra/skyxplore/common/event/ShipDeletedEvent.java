package com.github.saphyra.skyxplore.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ShipDeletedEvent {
    private final String shipId;
}
