package skyxplore.dataaccess.gamedata.entity;

import lombok.Data;
import lombok.ToString;
import skyxplore.dataaccess.gamedata.entity.abstractentity.FactoryData;

import java.util.HashMap;

@SuppressWarnings("Lombok")
@Data
@ToString(callSuper = true)
public class Material extends FactoryData {
    private boolean buildable;
    private HashMap<String, Integer> materials;
}
