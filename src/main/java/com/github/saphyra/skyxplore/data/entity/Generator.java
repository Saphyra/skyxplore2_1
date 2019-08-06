package com.github.saphyra.skyxplore.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Generator extends EquipmentDescription {
    @JsonProperty("energyrecharge")
    private Integer energyRecharge;
}
