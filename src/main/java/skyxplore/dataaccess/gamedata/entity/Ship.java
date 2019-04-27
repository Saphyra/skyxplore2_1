package skyxplore.dataaccess.gamedata.entity;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import skyxplore.dataaccess.gamedata.entity.abstractentity.EquipmentDescription;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Ship extends EquipmentDescription {
    @JsonProperty("corehull")
    private Integer coreHull;
    private Integer connector;
    private Slot defense;
    private Slot weapon;
    private ArrayList<String> ability;
}
