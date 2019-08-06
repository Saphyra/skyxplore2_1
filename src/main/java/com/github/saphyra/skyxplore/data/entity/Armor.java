package com.github.saphyra.skyxplore.data.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Armor extends EquipmentDescription {
    private Integer capacity;
}
