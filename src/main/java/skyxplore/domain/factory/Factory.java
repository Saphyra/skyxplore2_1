package skyxplore.domain.factory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import skyxplore.domain.materials.Materials;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Factory {
    private String factoryId;
    private String characterId;
    private Materials materials;
}
