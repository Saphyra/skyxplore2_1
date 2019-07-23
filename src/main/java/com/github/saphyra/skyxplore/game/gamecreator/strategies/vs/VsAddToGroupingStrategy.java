package com.github.saphyra.skyxplore.game.gamecreator.strategies.vs;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.game.gamecreator.GameGroupingQueryService;
import com.github.saphyra.skyxplore.game.gamecreator.GameGroupingStorage;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameCharacter;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGroup;
import com.github.saphyra.skyxplore.game.gamecreator.domain.GameGrouping;
import com.github.saphyra.skyxplore.game.gamecreator.strategies.AddToGroupingStrategy;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.LobbyMember;
import com.github.saphyra.util.IdGenerator;
import com.github.saphyra.util.OffsetDateTimeProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
//TODO unit test
    //TODO refactor
class VsAddToGroupingStrategy implements AddToGroupingStrategy {
    private final GameGroupingQueryService gameGroupingQueryService;
    private final GameGroupingStorage gameGroupingStorage;
    private final IdGenerator idGenerator;
    private final OffsetDateTimeProvider offsetDateTimeProvider;

    @Override
    public boolean canAdd(GameMode gameMode) {
        return gameMode == GameMode.VS;
    }

    @Override
    public void add(Lobby lobby) {
        Optional<GameGrouping> availableGrouping = getAvailableGrouping(lobby.getMembers().size());

        if (availableGrouping.isPresent()) {
            addToExistingGrouping(lobby, availableGrouping.get());
        } else {
            createGroupingFrom(lobby);
        }
    }

    private Optional<GameGrouping> getAvailableGrouping(int lobbySize) {
        return gameGroupingQueryService.getGroupingsByGameMode(GameMode.VS).stream()
            .filter(gameGrouping -> gameGrouping.getGameGroups().size() + lobbySize <= 2)
            .findAny();
    }

    private void addToExistingGrouping(Lobby lobby, GameGrouping availableGrouping) {
        lobby.getMembers().forEach(lobbyMember -> availableGrouping.addGroup(createGroup(lobby, lobbyMember)));
        availableGrouping.lockLobby(lobby.getLobbyId());
    }

    private void createGroupingFrom(Lobby lobby) {
        GameGrouping gameGrouping = GameGrouping.builder()
            .gameGroupingId(idGenerator.randomUUID())
            .gameMode(lobby.getGameMode())
            .data(lobby.getData())
            .createdAt(offsetDateTimeProvider.getCurrentDate())
            .build()
            .lockLobby(lobby.getLobbyId())
            .addGroups(createGroups(lobby));
        gameGroupingStorage.put(gameGrouping.getGameGroupingId(), gameGrouping);
    }

    private List<GameGroup> createGroups(Lobby lobby) {
        return lobby.getMembers().stream()
            .map(lobbyMember -> createGroup(lobby, lobbyMember))
            .collect(Collectors.toList());
    }

    private GameGroup createGroup(Lobby lobby, LobbyMember lobbyMember) {
        return GameGroup.builder()
            .gameGroupId(idGenerator.randomUUID())
            .maxGroupSize(1)
            .autoFill(false)
            .build()
            .addCharacter(createGameCharacter(lobbyMember));
    }

    private GameCharacter createGameCharacter(LobbyMember lobbyMember) {
        return GameCharacter.builder()
            .characterId(lobbyMember.getCharacterId())
            .build();
    }
}
