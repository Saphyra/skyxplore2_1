package com.github.saphyra.skyxplore.lobby.creation;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.skyxplore.lobby.domain.FixedSizeConcurrentList;
import com.github.saphyra.skyxplore.lobby.domain.GameMode;
import com.github.saphyra.skyxplore.lobby.domain.Lobby;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
class TeamfightLobbyFactory implements LobbyFactory {
    private static final List<Integer> ALLOWED_TEAM_SIZE = Arrays.asList(2, 5, 10, 25, 33, 50);

    private final IdGenerator idGenerator;

    @Override
    public boolean canCreate(GameMode gameMode) {
        return gameMode == GameMode.TEAMFIGHT;
    }

    @Override
    public Lobby create(GameMode gameMode, String characterId, String data) {
        FixedSizeConcurrentList<String> users = new FixedSizeConcurrentList<>(parseTeamSize(data));
        users.add(characterId);


        return Lobby.builder()
            .lobbyId(idGenerator.randomUUID())
            .gameMode(gameMode)
            .users(users)
            .data(data)
            .ownerId(characterId)
            .build();
    }

    private int parseTeamSize(String data) {
        int result;
        try {
            result = Integer.valueOf(data);
        } catch (NumberFormatException e) {
            throw new BadRequestException(data + "could not be parsed to int.");
        }

        if (!ALLOWED_TEAM_SIZE.contains(result)) {
            throw new BadRequestException("Not allowed team size: " + result);
        }

        return result;
    }
}
