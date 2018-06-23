package skyxplore.domain.materials;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
@NoArgsConstructor
public class Materials extends HashMap<String, Integer> {

    public Materials(Map<String, Integer> elements){
        putAll(elements);
    }

    @Override
    public Integer get(Object key){
        if(!containsKey(key)){
            return 0;
        }
        return super.get(key);
    }

    @Override
    public Integer put(String key, Integer value){
        return super.put(key, get(key) + value);
    }
}
