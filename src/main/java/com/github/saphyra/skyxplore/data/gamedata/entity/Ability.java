package com.github.saphyra.skyxplore.data.gamedata.entity;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Ability extends GeneralDescription {
    @JsonProperty("energyusage")
    private Integer energyUsage;
    private Integer active;
    private Integer reload;
    private Map<String, Integer> effect;
}
