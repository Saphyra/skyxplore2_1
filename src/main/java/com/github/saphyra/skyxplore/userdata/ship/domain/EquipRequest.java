package com.github.saphyra.skyxplore.userdata.ship.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipRequest {
    @NotNull
    private String itemId;

    @NotNull
    private String equipTo;
}
