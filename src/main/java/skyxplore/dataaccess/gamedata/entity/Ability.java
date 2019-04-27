package skyxplore.dataaccess.gamedata.entity;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;

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
