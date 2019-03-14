package skyxplore.dataaccess.gamedata.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import skyxplore.dataaccess.gamedata.entity.abstractentity.FactoryData;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Material extends FactoryData {
    private boolean buildable;
}
