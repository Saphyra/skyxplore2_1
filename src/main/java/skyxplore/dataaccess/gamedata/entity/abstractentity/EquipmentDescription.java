package skyxplore.dataaccess.gamedata.entity.abstractentity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public abstract class EquipmentDescription extends ShopData {
    private String type;
    private Integer level;
    private Integer score;
}
