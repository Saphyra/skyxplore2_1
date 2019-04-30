package org.github.saphyra.skyxplore.gamedata.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.github.saphyra.skyxplore.gamedata.entity.abstractentity.EquipmentDescription;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Shield extends EquipmentDescription {
    private Integer capacity;
    private Integer regeneration;
    @JsonProperty("energyusage")
    private Integer energyUsage;
}
