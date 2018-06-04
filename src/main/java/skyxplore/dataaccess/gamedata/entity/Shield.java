package skyxplore.dataaccess.gamedata.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import skyxplore.dataaccess.gamedata.entity.abstractentity.EquipmentDescription;

@Data
@ToString(callSuper = true)
public class Shield extends EquipmentDescription {
    private Integer capacity;
    private Integer regeneration;
    @JsonProperty("energyusage")
    private Integer energyUsage;
}
