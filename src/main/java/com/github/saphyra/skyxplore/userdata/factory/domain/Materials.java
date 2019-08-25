package com.github.saphyra.skyxplore.userdata.factory.domain;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@Slf4j
public class Materials extends HashMap<String, Integer> {

    public Materials(Map<String, Integer> elements) {
        putAll(elements);
    }

    @Override
    public Integer get(Object key) {
        if (!containsKey(key)) {
            return 0;
        }
        return super.get(key);
    }

    public Integer addMaterial(String key, Integer value) {
        return super.put(key, get(key) + value);
    }

    public void removeMaterial(String key, Integer amount) {
        Integer actual = get(key);
        log.info("removeMaterial - {} toRemove: {}, actual: {}", key, amount, actual);
        if (actual < amount) {
            throw ExceptionFactory.notEnoughMaterials(key, amount, actual);
        }
        actual -= amount;
        super.put(key, actual);
        log.debug("{} after change: {}", key, get(key));
        get(key);
    }

    @Override
    public Integer put(String key, Integer value) {
        return addMaterial(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ? extends Integer> map) {
        Set<? extends String> keySet = map.keySet();
        keySet.forEach(k -> put(k, map.get(k)));
    }

    @Override
    public Integer remove(Object key) {
        throw new UnsupportedOperationException("You cannot remove elements.");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("You cannot remove elements.");
    }
}
