package skyxplore.domain.factory;

import lombok.Data;
import skyxplore.domain.materials.Materials;

@Data
public class Factory {
    private String factoryId;
    private String characterId;
    private Materials materials;
}
