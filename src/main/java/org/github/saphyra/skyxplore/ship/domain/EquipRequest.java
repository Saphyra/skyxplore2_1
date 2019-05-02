package org.github.saphyra.skyxplore.ship.domain;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipRequest {
    @NotNull
    private String itemId;

    @NotNull
    private String equipTo;

}
