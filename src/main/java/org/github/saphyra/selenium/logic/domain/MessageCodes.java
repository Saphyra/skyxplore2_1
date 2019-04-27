package org.github.saphyra.selenium.logic.domain;

import java.util.HashMap;
import java.util.Optional;

public class MessageCodes extends HashMap<String, String> {
    @Override
    public String get(Object key) {
        return Optional.ofNullable(super.get(key)).orElseThrow(() -> new RuntimeException("No message found with key " + key));
    }
}
