package com.github.saphyra.skyxplore.ship.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShipView {
    private String shipType;
    private Integer coreHull;
    private Integer connectorSlot;
    private List<String> connectorEquipped;
    private SlotView defenseSlot;
    private SlotView weaponSlot;
    private List<String> ability;
}
