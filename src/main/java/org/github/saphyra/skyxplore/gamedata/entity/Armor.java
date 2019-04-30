package org.github.saphyra.skyxplore.gamedata.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.github.saphyra.skyxplore.gamedata.entity.abstractentity.EquipmentDescription;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Armor extends EquipmentDescription {
    private Integer capacity;
}
