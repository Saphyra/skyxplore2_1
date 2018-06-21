package skyxplore.domain.materials;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.gamedata.subservice.MaterialService;
import skyxplore.domain.ConverterBase;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class MaterialsConverter extends ConverterBase<HashMap<String, Integer>, Materials> {
    private final MaterialService materialService;

    @Override
    public Materials convertEntity(HashMap<String, Integer> entity) {
        if(entity == null){
            return null;
        }
        return new Materials(entity);
    }

    @Override
    public HashMap<String, Integer> convertDomain(Materials domain) {
        if(domain == null){
            return null;
        }
        return new HashMap<>(domain);
    }

    public Materials fillWithMaterials(Materials materials){
        Materials filled = new Materials();
        materialService.keySet().forEach(key -> filled.put(key, materials.get(key)));
        return filled;
    }
}
