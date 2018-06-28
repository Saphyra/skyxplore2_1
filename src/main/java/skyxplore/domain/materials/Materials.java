package skyxplore.domain.materials;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.exception.NotEnoughMaterialsException;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("WeakerAccess")
@NoArgsConstructor
@Slf4j
public class Materials extends HashMap<String, Integer> {

    public Materials(Map<String, Integer> elements){
        putAll(elements);
    }

    @Override
    public Integer get(Object key){
        if(!containsKey(key)){
            //TODO change back
            return 100;
        }
        return super.get(key);
    }

    public Integer addMaterial(String key, Integer value){
        return put(key, get(key) + value);
    }

    public Integer removeMaterial(String key, Integer amount){
        Integer actual = get(key);
        log.info("{} amount: {}", key, amount);
        log.info("{} actual: {}", key, actual);
        if(actual < amount){
            throw  new NotEnoughMaterialsException("Not enough " + key +". Needed: " + amount + ", have: " + actual);
        }
        actual -= amount;
        put(key, actual);
        log.info("{} after change: {}", key, get(key));
        return  actual;
    }
}
