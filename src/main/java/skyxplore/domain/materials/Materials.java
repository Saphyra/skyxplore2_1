package skyxplore.domain.materials;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.exception.NotEnoughMaterialsException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
            return 0;
        }
        return super.get(key);
    }

    public Integer addMaterial(String key, Integer value){
        return super.put(key, get(key) + value);
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

    @Override
    public Integer put(String key, Integer value){
        return addMaterial(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ? extends Integer> map){
        Set<?extends String> keySet = map.keySet();
        keySet.forEach(k -> put(k, map.get(k)));
    }

    @Override
    public Integer remove(Object key){
        throw new UnsupportedOperationException("You cannot remove elements.");
    }

    @Override
    public void clear(){
        throw new UnsupportedOperationException("You cannot remove elements.");
    }
}
