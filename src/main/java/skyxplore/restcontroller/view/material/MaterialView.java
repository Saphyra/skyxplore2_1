package skyxplore.restcontroller.view.material;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MaterialView {
    private String materialId;
    private String name;
    private String description;
    private Integer amount;
}
