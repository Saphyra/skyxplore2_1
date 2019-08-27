package com.github.saphyra.skyxplore.data.gamedata.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Weapon extends EquipmentDescription {
    private Integer attackSpeed;
    private Integer criticalRate;
    private Integer range;
    private Integer hullDamage;
    private Integer shieldDamage;
    private Integer accuracy;
}
