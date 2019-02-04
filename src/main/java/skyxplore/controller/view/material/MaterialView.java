package skyxplore.controller.view.material;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MaterialView {
    private String materialId;
    private Integer amount;
}
