package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroupCharacter;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroup;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.LobbyMember;
import com.github.saphyra.util.IdGenerator;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class GameGroupFactory {
    private final GameGroupCharacterFactory gameGroupCharacterFactory;
    private final IdGenerator idGenerator;

    public List<GameGroup> createGroups(Lobby lobby, int maxGroupSize) {
        List<GameGroupCharacter> gameGroupCharacters = lobby.getMembers().stream()
            .map(LobbyMember::getCharacterId)
            .map(characterId -> gameGroupCharacterFactory.createGameGroupCharacter(characterId, false))
            .collect(Collectors.toList());
        return createGroups(gameGroupCharacters, lobby.isAutoFill(), maxGroupSize);
    }

    public List<GameGroup> createGroups(List<GameGroupCharacter> gameGroupCharacters, boolean isAutoFill, int maxGroupSize) {
        log.debug("Creating GameGroups from gameGroupCharacters {}, with autoFill: {}, maxGroupSize: {}", gameGroupCharacters, isAutoFill, maxGroupSize);
        return Lists.partition(gameGroupCharacters, maxGroupSize).stream()
            .map(members -> createGroup(members, isAutoFill, maxGroupSize))
            .collect(Collectors.toList());
    }

    public GameGroup createGroup(List<GameGroupCharacter> gameGroupCharacters, boolean autoFill, int maxGroupSize) {
        GameGroup gameGroup = GameGroup.builder()
            .gameGroupId(idGenerator.randomUUID())
            .maxGroupSize(maxGroupSize)
            .autoFill(autoFill)
            .build();

        gameGroupCharacters.forEach(gameGroup::addCharacter);
        log.debug("Created GameGroup: {}", gameGroup);
        return gameGroup;
    }


}
