package skyxplore.dataaccess.gamedata.entity;

import lombok.Data;
import lombok.ToString;
import skyxplore.dataaccess.gamedata.entity.abstractentity.FactoryData;

@SuppressWarnings("Lombok")
@Data
@ToString(callSuper = true)
public class Material extends FactoryData {
    private boolean buildable;
}
