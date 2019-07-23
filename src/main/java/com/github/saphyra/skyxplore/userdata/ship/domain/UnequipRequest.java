package com.github.saphyra.skyxplore.userdata.ship.domain;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnequipRequest {
    @NotNull
    private String slot;
    @NotNull
    private String itemId;
}
