package skyxplore.dataaccess.gamedata.entity.abstractentity;

import lombok.Data;

@Data
public abstract class GeneralDescription {
    private String id;
    private String name;
    private String description;
}
