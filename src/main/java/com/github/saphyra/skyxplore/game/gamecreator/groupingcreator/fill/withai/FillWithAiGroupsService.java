package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.withai;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupSizeRange;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupCharacterFactory;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupFactory;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
 class FillWithAiGroupsService {
    private final GameGroupCharacterFactory gameGroupCharacterFactory;
    private final GameGroupFactory gameGroupFactory;
    private final Random random;

     void fillGameWithAiGroups(GameGrouping gameGrouping, int missingMembers) {
        List<GameGroupCharacter> gameGroupCharacters = new ArrayList<>();
        for (; missingMembers > 0; missingMembers--) {
            gameGroupCharacters.add(gameGroupCharacterFactory.createAi());
        }
         int groupSize = getGroupSize(gameGrouping);
         gameGroupFactory.createGroups(gameGroupCharacters, false, groupSize)
            .forEach(gameGrouping::addGroup);
    }

    private int getGroupSize(GameGrouping gameGrouping) {
        GameGroupSizeRange gameGroupSizeRange = gameGrouping.getGameGroupSizeRange();
        return random.randInt(gameGroupSizeRange.getMinGroupSize(), gameGroupSizeRange.getMaxGroupSize());
    }
}
