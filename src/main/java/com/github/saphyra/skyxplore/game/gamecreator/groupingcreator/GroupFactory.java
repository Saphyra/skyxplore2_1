package com.github.saphyra.skyxplore.game.gamecreator.groupingcreator;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.gamecreator.domain.GameCharacter;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroup;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.LobbyMember;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class GroupFactory {
    private final IdGenerator idGenerator;

    public List<GameGroup> createGroups(Lobby lobby, int maxGroupSize) {
        return lobby.getMembers().stream()
            .map(lobbyMember -> createGroup(lobbyMember, lobby.isAutoFill(), maxGroupSize))
            .collect(Collectors.toList());
    }

    public GameGroup createGroup(LobbyMember lobbyMember, boolean autoFill, int maxGroupSize) {
        GameGroup gameGroup = GameGroup.builder()
            .gameGroupId(idGenerator.randomUUID())
            .maxGroupSize(maxGroupSize)
            .autoFill(autoFill)
            .build()
            .addCharacter(createGameCharacter(lobbyMember));
        log.debug("Created GameGroup: {}", gameGroup);
        return gameGroup;
    }

    private GameCharacter createGameCharacter(LobbyMember lobbyMember) {
        return GameCharacter.builder()
            .characterId(lobbyMember.getCharacterId())
            .build();
    }
}
