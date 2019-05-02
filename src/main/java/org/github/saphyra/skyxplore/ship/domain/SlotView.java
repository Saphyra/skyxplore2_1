package org.github.saphyra.skyxplore.ship.domain;

import java.util.ArrayList;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SlotView {
    private Integer frontSlot;
    @Builder.Default
    private ArrayList<String> frontEquipped;

    private Integer leftSlot;
    @Builder.Default
    private ArrayList<String> leftEquipped;

    private Integer rightSlot;
    @Builder.Default
    private ArrayList<String> rightEquipped;

    private Integer backSlot;
    @Builder.Default
    private ArrayList<String> backEquipped;
}
