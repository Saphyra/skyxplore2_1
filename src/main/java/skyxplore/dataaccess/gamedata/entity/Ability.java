package skyxplore.dataaccess.gamedata.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;

import java.util.Map;

@Data
@ToString(callSuper = true)
public class Ability extends GeneralDescription {
    @JsonProperty("energyusage")
    private Integer energyUsage;
    private Integer active;
    private Integer reload;
    private Map<String, Integer> effect;
}
