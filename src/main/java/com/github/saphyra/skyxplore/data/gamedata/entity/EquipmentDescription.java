package com.github.saphyra.skyxplore.data.gamedata.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class EquipmentDescription extends ShopData {
    private Integer level;
    private Integer score;
}
