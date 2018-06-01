package skyxplore.dataaccess.gamedata.entity;

import lombok.Data;
import lombok.ToString;
import skyxplore.dataaccess.gamedata.entity.abstractentity.EquipmentDescription;

@Data
@ToString(callSuper = true)
public class Shield extends EquipmentDescription {
    private Integer capacity;
    private Integer regeneration;
    private Integer energyUsage;
}
