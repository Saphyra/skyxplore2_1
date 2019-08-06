package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.withai;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroup;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupCharacterFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
class ExistingGroupFiller {
    private final GameGroupCharacterFactory gameGroupCharacterFactory;
    private final GameGroupFilter gameGroupFilter;

    int fillEmptyPlacesInExistingGroups(GameGrouping gameGrouping) {
        int missingMembers = gameGrouping.getMinimumGameMembersAmount() - gameGrouping.getMembersAmount();
        log.debug("missingMembers: {}", missingMembers);

        List<GameGroup> gameGroupsWithEmptyPlace = gameGroupFilter.getAutoFillableGameGroups(gameGrouping.getGameGroups());

        for (GameGroup gameGroup : gameGroupsWithEmptyPlace) {
            log.debug("Filling GameGroup {} with ais...", gameGroup.getGameGroupId());
            for (int emptyPlaces = getEmptyPlaces(gameGroup); emptyPlaces > 0 && missingMembers > 0; emptyPlaces--, missingMembers--) {
                log.debug("Adding ai to GameGroup {}. emptyPlaces: {}, missingMembers: {}", gameGroup.getGameGroupId(), emptyPlaces, missingMembers);
                gameGroup.addCharacter(gameGroupCharacterFactory.createAi());
            }
        }
        return missingMembers;
    }

    private int getEmptyPlaces(GameGroup gameGroup) {
        return gameGroup.getCharacters().getMaxSize() - gameGroup.getCharacters().size();
    }
}
