package skyxplore.dataaccess.gamedata.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import skyxplore.dataaccess.gamedata.entity.abstractentity.EquipmentDescription;

@Data
@ToString(callSuper = true)
public class Extender extends EquipmentDescription {
    @JsonProperty("extendedslot")
    private String extendedSlot;

    @JsonProperty("extendednum")
    private Integer extendedNum;
}
