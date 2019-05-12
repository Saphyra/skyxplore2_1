package com.github.saphyra.skyxplore.lobby.lobby.domain;

import com.github.saphyra.exceptionhandling.exception.PayloadTooLargeException;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyContext;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Builder
@Slf4j
//TODO unit test
public class Lobby {
    @NonNull
    private final UUID lobbyId;

    @NonNull
    private final GameMode gameMode;

    @NonNull
    @Getter(value = AccessLevel.NONE)
    private final FixedSizeConcurrentList<String> users;

    private final String data;

    @NonNull
    private volatile String ownerId;

    @NotNull
    private final LobbyContext lobbyContext;

    public void removeMember(String characterId) {
        log.info("Removing character {} from lobby {}", characterId, lobbyId);
        users.remove(characterId);

        if (users.isEmpty()) {
            log.info("Lobby {} has no more members. Deleting...", lobbyId);
            lobbyContext.getLobbyStorage().remove(lobbyId);
            return;
        }

        if (ownerId.equals(characterId)) {
            log.info("Owner {} of lobby {} has left the lobby. Selecting new owner...", ownerId, lobbyId);
            ownerId = users.get(lobbyContext.getRandom().randInt(0, users.size()));
            log.info("New owner of lobby {} is: {}", lobbyId, ownerId);
        }
    }

    public List<String> getUsers() {
        return new ArrayList<>(users);
    }

    public void addMember(String characterId) {
        log.info("Adding character {} to lobby {}", characterId, lobbyId);
        try {
            users.add(characterId);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new PayloadTooLargeException(lobbyId + " lobby is already full.");
        }
    }
}
