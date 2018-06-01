package skyxplore.dataaccess.gamedata.entity;

import lombok.Data;
import lombok.ToString;
import skyxplore.dataaccess.gamedata.entity.abstractentity.EquipmentDescription;

@Data
@ToString(callSuper = true)
public class Weapon extends EquipmentDescription {
    private Integer attackSpeed;
    private Integer criticalRate;
    private Integer range;
    private Integer hullDamage;
    private Integer shieldDamage;
    private Integer accuracy;
}
