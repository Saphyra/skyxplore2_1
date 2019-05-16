package com.github.saphyra.skyxplore.lobby.lobby.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.exceptionhandling.exception.PayloadTooLargeException;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyContext;
import com.github.saphyra.skyxplore.lobby.message.domain.Message;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

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
    private final FixedSizeConcurrentList<String> members;

    private final String data;

    @NonNull
    private volatile String ownerId;

    @NonNull
    @Getter(value = AccessLevel.NONE)
    private final List<Message> messages = new Vector<>();

    @NonNull
    @Builder.Default
    private final List<LobbyEvent> events = new Vector<>();

    @NonNull
    private final LobbyContext lobbyContext;

    public void removeMember(String characterId) {
        log.info("Removing character {} from lobby {}", characterId, lobbyId);
        if (!members.contains(characterId)) {
            throw new BadRequestException(characterId + " is not a member of lobby " + lobbyId);
        }

        members.remove(characterId);

        events.add(
            LobbyEvent.builder()
                .eventType(LobbyEventType.EXIT)
                .data(characterId)
                .build()
        );

        if (members.isEmpty()) {
            log.info("Lobby {} has no more members. Deleting...", lobbyId);
            lobbyContext.getLobbyStorage().remove(lobbyId);
            return;
        }

        if (ownerId.equals(characterId)) {
            log.info("Owner {} of lobby {} has left the lobby. Selecting new owner...", ownerId, lobbyId);
            ownerId = members.get(lobbyContext.getRandom().randInt(0, members.size() - 1));
            events.add(
                LobbyEvent.builder()
                    .eventType(LobbyEventType.OWNER_CHANGED)
                    .data(ownerId)
                    .build()
            );
            log.info("New owner of lobby {} is: {}", lobbyId, ownerId);
        }
    }

    public List<String> getMembers() {
        return new ArrayList<>(members);
    }

    public void addMember(String characterId) {
        log.info("Adding character {} to lobby {}", characterId, lobbyId);
        try {
            events.add(
                LobbyEvent.builder()
                    .eventType(LobbyEventType.ENTER)
                    .data(characterId)
                    .build()
            );
            members.add(characterId);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new PayloadTooLargeException(lobbyId + " lobby is already full.");
        }
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public List<Message> getMessages() {
        return new ArrayList<>(messages);
    }

    public void transferOwnership(String newOwnerId) {
        ownerId = newOwnerId;
        events.add(
            LobbyEvent.builder()
                .eventType(LobbyEventType.OWNER_CHANGED)
                .data(ownerId)
                .build()
        );
    }
}
