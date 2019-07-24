package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameCharacter;
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
    private final GameCharacterFactory gameCharacterFactory;
    private final IdGenerator idGenerator;

    public List<GameGroup> createGroups(Lobby lobby, int maxGroupSize) {
        List<GameCharacter> gameCharacters = lobby.getMembers().stream()
            .map(LobbyMember::getCharacterId)
            .map(characterId -> gameCharacterFactory.createGameCharacter(characterId, false))
            .collect(Collectors.toList());
        return createGroups(gameCharacters, lobby.isAutoFill(), maxGroupSize);
    }

    public List<GameGroup> createGroups(List<GameCharacter> gameCharacters, boolean isAutoFill, int maxGroupSize) {
        return Lists.partition(gameCharacters, maxGroupSize).stream()
            .map(members -> createGroup(members, isAutoFill, maxGroupSize))
            .collect(Collectors.toList());
    }

    public GameGroup createGroup(List<GameCharacter> gameCharacters, boolean autoFill, int maxGroupSize) {
        GameGroup gameGroup = GameGroup.builder()
            .gameGroupId(idGenerator.randomUUID())
            .maxGroupSize(maxGroupSize)
            .autoFill(autoFill)
            .build();

        gameCharacters.forEach(gameGroup::addCharacter);
        log.debug("Created GameGroup: {}", gameGroup);
        return gameGroup;
    }


}
