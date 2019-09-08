package com.github.saphyra.skyxplore.common;

import java.util.HashMap;
import java.util.Optional;

public class OptionalMap<K, V> extends HashMap<K, V> {
    public Optional<V> getOptional(K key) {
        return Optional.ofNullable(get(key));
    }
}
