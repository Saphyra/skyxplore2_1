package skyxplore.dataaccess.gamedata.entity;

import java.util.HashMap;
import lombok.Data;
import lombok.ToString;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;

@Data
@ToString(callSuper = true)
public class Material extends GeneralDescription {
    private boolean buildable;
    private HashMap<String, Integer> materials;
}
