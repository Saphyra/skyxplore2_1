package skyxplore.dataaccess.gamedata.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import skyxplore.dataaccess.gamedata.entity.abstractentity.EquipmentDescription;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Battery extends EquipmentDescription {
    private Integer capacity;
}
