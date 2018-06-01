package skyxplore.dataaccess.gamedata.entity.abstractentity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public abstract class EquipmentDescription extends FactoryData {
    private String type;
    private String slot;
    private Integer level;
    private Integer score;
}
