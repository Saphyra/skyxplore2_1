package org.github.saphyra.skyxplore.gamedata.entity;

import java.util.List;

import org.github.saphyra.skyxplore.gamedata.entity.abstractentity.EquipmentDescription;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Ship extends EquipmentDescription {
    @JsonProperty("corehull")
    private Integer coreHull;
    private Integer connector;
    private Slot defense;
    private Slot weapon;
    private List<String> ability;
}
