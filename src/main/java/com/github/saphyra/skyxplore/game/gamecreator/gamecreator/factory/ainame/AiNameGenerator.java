package com.github.saphyra.skyxplore.game.gamecreator.gamecreator.factory.ainame;


import org.springframework.stereotype.Component;

import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
class AiNameGenerator {
    private final FirstNames firstNames;
    private final LastNames lastNames;
    private final Random random;

    String generateCharacterName() {
        String aiName = getFirstName() + " " + getLastName() + " " + random.randInt(0, 1000);
        log.debug("Generated ai name: {}", aiName);
        return aiName;
    }

    private String getFirstName() {
        return firstNames.get(random.randInt(0, firstNames.size() - 1));
    }

    private String getLastName() {
        return lastNames.get(random.randInt(0, lastNames.size() - 1));
    }
}
