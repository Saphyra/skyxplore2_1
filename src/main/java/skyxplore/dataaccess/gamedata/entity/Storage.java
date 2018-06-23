package skyxplore.dataaccess.gamedata.entity;

import lombok.Data;
import lombok.ToString;
import skyxplore.dataaccess.gamedata.entity.abstractentity.EquipmentDescription;

@SuppressWarnings("Lombok")
@Data
@ToString(callSuper = true)
public class Storage extends EquipmentDescription {
    private Integer capacity;
}
