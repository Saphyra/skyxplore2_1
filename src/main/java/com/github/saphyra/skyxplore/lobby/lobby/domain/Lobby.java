package com.github.saphyra.skyxplore.lobby.lobby.domain;

import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.exceptionhandling.exception.PayloadTooLargeException;
import com.github.saphyra.exceptionhandling.exception.PreconditionFailedException;
import com.github.saphyra.skyxplore.common.domain.FixedSizeConcurrentList;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyContext;
import com.github.saphyra.skyxplore.lobby.message.domain.Message;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.Vector;

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
    private final FixedSizeConcurrentList<LobbyMember> members;

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

    @Builder.Default
    @Setter(AccessLevel.NONE)
    private volatile boolean inQueue = false;

    @Builder.Default
    @Setter(AccessLevel.NONE)
    private volatile boolean autoFill = true;

    public void removeMember(String characterId) {
        log.info("Removing character {} from lobby {}", characterId, lobbyId);
        Optional<LobbyMember> lobbyMember = findMemberByCharacterId(characterId);
        if (!lobbyMember.isPresent()) {
            throw new BadRequestException(characterId + " is not a member of lobby " + lobbyId);
        }

        members.remove(lobbyMember.get());

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
            ownerId = members.get(lobbyContext.getRandom().randInt(0, members.size() - 1)).getCharacterId();
            events.add(
                LobbyEvent.builder()
                    .eventType(LobbyEventType.OWNER_CHANGED)
                    .data(ownerId)
                    .build()
            );
            log.info("New owner of lobby {} is: {}", lobbyId, ownerId);
        }
    }

    public Optional<LobbyMember> findMemberByCharacterId(String characterId) {
        return members.stream()
            .filter(lobbyMember -> lobbyMember.getCharacterId().equals(characterId))
            .findFirst();
    }

    public List<LobbyMember> getMembers() {
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
            members.add(LobbyMember.builder().characterId(characterId).build());
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

    public void setMemberReady(String characterId) {
        findMemberByCharacterIdValidated(characterId).setReady(true);
        events.add(
            LobbyEvent.builder()
                .eventType(LobbyEventType.SET_READY)
                .data(characterId)
                .build()
        );
    }

    public void setMemberUnready(String characterId) {
        findMemberByCharacterIdValidated(characterId).setReady(false);
        events.add(
            LobbyEvent.builder()
                .eventType(LobbyEventType.SET_UNREADY)
                .data(characterId)
                .build()
        );
    }

    private LobbyMember findMemberByCharacterIdValidated(String characterId) {
        return findMemberByCharacterId(characterId)
            .orElseThrow(() -> new NotFoundException(characterId + " is not the member of lobby " + lobbyId));

    }

    public void startQueueing(Boolean autoFill) {
        members.forEach(lobbyMember -> {
            if (!lobbyMember.isReady()) {
                throw new PreconditionFailedException(lobbyMember.getCharacterId() + " is not ready.");
            }
        });

        inQueue = true;
        this.autoFill = autoFill;

        events.add(
            LobbyEvent.builder()
                .eventType(LobbyEventType.START_QUEUE)
                .build()
        );
    }
}
