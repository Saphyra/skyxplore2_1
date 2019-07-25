package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.fill.withai;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.game.gamecreator.GameGroupingStorage;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroup;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupSizeRange;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameCharacterFactory;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GameGroupFactory;
import com.github.saphyra.skyxplore.game.gamecreator.groupingcreator.GroupingCreatorConfiguration;
import com.github.saphyra.util.IdGenerator;
import com.github.saphyra.util.OffsetDateTimeProvider;
import com.github.saphyra.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FillWithAiService {
    private final GameCharacterFactory gameCharacterFactory;
    private final GroupingCreatorConfiguration configuration;
    private final GameGroupFactory gameGroupFactory;
    private final GameGroupingStorage gameGroupingStorage;
    private final IdGenerator idGenerator;
    private final OffsetDateTimeProvider offsetDateTimeProvider;
    private final Random random;

    public void fillWithAi() {
        log.info("Filling GameGroupings with AI-s...");
        try {
            gameGroupingStorage.values().stream()
                .filter(this::isAutoFillDelayElapsed)
                .filter(gameGrouping -> !gameGrouping.hasEnoughMembers())
                .forEach(this::fillWithAi);
        } catch (Exception e) {
            log.error("Error occurred during autofill:", e);
        }
    }

    private boolean isAutoFillDelayElapsed(GameGrouping gameGrouping) {
        return gameGrouping.getCreatedAt().plusSeconds(configuration.getAutoFillDelaySeconds()).isBefore(offsetDateTimeProvider.getCurrentDate());
    }

    private void fillWithAi(GameGrouping gameGrouping) {
        log.info("Filling GameGrouping {} with ais...", gameGrouping);
        int missingMembers = gameGrouping.getMinimumGameMembersAmount() - gameGrouping.getMembersAmount();
        log.debug("missingMembers: {}", missingMembers);

        List<GameGroup> gameGroupsWithEmptyPlace = getGameGroupsWithEmptyPlace(gameGrouping);

        for (GameGroup gameGroup : gameGroupsWithEmptyPlace) {
            log.debug("Filling GameGroup {} with ais...", gameGroup.getGameGroupId());
            for (int emptyPlaces = getEmptyPlaces(gameGroup); emptyPlaces > 0 && missingMembers > 0; emptyPlaces--, missingMembers--) {
                log.debug("Adding ai to GameGroup {}. emptyPlaces: {}, missingMembers: {}", gameGroup.getGameGroupId(), emptyPlaces, missingMembers);
                gameGroup.addCharacter(gameCharacterFactory.createAi());
            }
        }

        log.debug("All emptyPlaces are filled of GameGroups in GameGrouping {}. Remaining missingMembers: {}", gameGrouping.getGameGroupingId(), missingMembers);
        List<GameGroupCharacter> gameGroupCharacters = new ArrayList<>();
        for (; missingMembers > 0; missingMembers--) {
            GameGroupCharacter gameGroupCharacter = GameGroupCharacter.builder()
                .characterId(idGenerator.generateRandomId())
                .isAi(true)
                .build();
            gameGroupCharacters.add(gameGroupCharacter);
        }
        gameGroupFactory.createGroups(gameGroupCharacters, false, getGroupSize(gameGrouping))
            .forEach(gameGrouping::addGroup);
    }

    private int getGroupSize(GameGrouping gameGrouping) {
        GameGroupSizeRange gameGroupSizeRange = gameGrouping.getGameGroupSizeRange();
        return random.randInt(gameGroupSizeRange.getMinGroupSize(), gameGroupSizeRange.getMaxGroupSize());
    }

    private int getEmptyPlaces(GameGroup gameGroup) {
        return gameGroup.getCharacters().getMaxSize() - gameGroup.getCharacters().size();
    }

    private List<GameGroup> getGameGroupsWithEmptyPlace(GameGrouping gameGrouping) {
        return gameGrouping.getGameGroups().stream()
            .filter(gameGroup -> gameGroup.getCharacters().size() < gameGroup.getCharacters().size())
            .filter(GameGroup::isAutoFill)
            .collect(Collectors.toList());
    }
}
