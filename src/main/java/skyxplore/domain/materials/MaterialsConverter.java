package skyxplore.domain.materials;

import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;

import java.util.HashMap;

@Component
public class MaterialsConverter extends ConverterBase<HashMap<String, Integer>, Materials> {
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
}
