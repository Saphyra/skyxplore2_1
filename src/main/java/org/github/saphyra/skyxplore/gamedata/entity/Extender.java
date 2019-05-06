package org.github.saphyra.skyxplore.gamedata.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Extender extends EquipmentDescription {
    @JsonProperty("extendedslot")
    private String extendedSlot;

    @JsonProperty("extendednum")
    private Integer extendedNum;
}
