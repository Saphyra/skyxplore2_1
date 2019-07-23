package com.github.saphyra.skyxplore.userdata.ship.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class SlotView {
    @NonNull
    private final Integer frontSlot;

    @NonNull
    @Builder.Default
    private final List<String> frontEquipped = new ArrayList<>();

    @NonNull
    private final Integer leftSlot;

    @Builder.Default
    @NonNull
    private final List<String> leftEquipped = new ArrayList<>();

    @NonNull
    private final Integer rightSlot;

    @NonNull
    @Builder.Default
    private final List<String> rightEquipped = new ArrayList<>();

    @NonNull
    private final Integer backSlot;

    @NonNull
    @Builder.Default
    private final List<String> backEquipped = new ArrayList<>();
}
