package skyxplore.dataaccess.gamedata.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import skyxplore.dataaccess.gamedata.entity.abstractentity.GeneralDescription;

import java.util.HashMap;

@SuppressWarnings("Lombok")
@Data
@ToString(callSuper = true)
public class Material extends GeneralDescription {
    private boolean buildable;
    private HashMap<String, Integer> materials;
    @JsonProperty("constructiontime")
    private int constructionTime;
    @JsonProperty("buildprice")
    private int buildPrice = 0;
}
