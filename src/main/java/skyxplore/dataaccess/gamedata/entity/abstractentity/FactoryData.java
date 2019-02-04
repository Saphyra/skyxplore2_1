package skyxplore.dataaccess.gamedata.entity.abstractentity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.HashMap;

@Data
@ToString(callSuper = true)
public abstract class FactoryData extends GeneralDescription {
    @JsonProperty("constructiontime")
    private Integer constructionTime;

    @JsonProperty("buildprice")
    private Integer buildPrice = 0;

    private HashMap<String, Integer> materials;
}
