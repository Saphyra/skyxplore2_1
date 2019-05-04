package org.github.saphyra.skyxplore.ship.domain;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class SlotView {
    private Integer frontSlot;
    @Builder.Default
    private List<String> frontEquipped = new ArrayList<>();

    private Integer leftSlot;
    @Builder.Default
    private List<String> leftEquipped = new ArrayList<>();

    private Integer rightSlot;
    @Builder.Default
    private List<String> rightEquipped = new ArrayList<>();

    private Integer backSlot;
    @Builder.Default
    private List<String> backEquipped = new ArrayList<>();
}
