package skyxplore.dataaccess.gamedata.entity.abstractentity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import java.util.HashMap;

@Data
@ToString(callSuper = true)
public abstract class FactoryData extends ShopData{
    @JsonProperty("constructiontime")
    private Integer constructionTime;

    @JsonProperty("buildprice")
    private Integer buildPrice;

    private HashMap<String, Integer> materials;
}
