package skyxplore.dataaccess.gamedata.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import skyxplore.dataaccess.gamedata.entity.abstractentity.EquipmentDescription;

import java.util.ArrayList;

@Data
@ToString(callSuper = true)
public class Ship extends EquipmentDescription {
    @JsonProperty("corehull")
    private Integer coreHull;
    private Integer connector;
    private Slot defense;
    private Slot weapon;
    private ArrayList<String> ability;
}
