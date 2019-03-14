package skyxplore.dataaccess.gamedata.entity.abstractentity;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public abstract class FactoryData extends GeneralDescription {
    @JsonProperty("constructiontime")
    private Integer constructionTime;

    @JsonProperty("buildprice")
    private Integer buildPrice = 0;

    private HashMap<String, Integer> materials;
}
