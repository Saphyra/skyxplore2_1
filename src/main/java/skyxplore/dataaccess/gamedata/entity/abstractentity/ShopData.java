package skyxplore.dataaccess.gamedata.entity.abstractentity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public abstract class ShopData extends FactoryData {

    @JsonProperty("buyprice")
    private Integer buyPrice;

    @JsonProperty("sellprice")
    private Integer sellPrice;
}
