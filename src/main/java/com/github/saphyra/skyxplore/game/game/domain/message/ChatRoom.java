package com.github.saphyra.skyxplore.game.game.domain.message;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

import com.github.saphyra.skyxplore.game.game.service.message.GameMessageFactory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
//TODO unit test
public class ChatRoom {
    @NonNull
    @Getter
    private final UUID roomId;

    @NonNull
    @Getter
    private final String displayName;

    @Builder.Default
    @Getter
    private final boolean defaultRoom = false;

    @NonNull
    private final Vector<String> members;

    @Builder.Default
    private final List<GameMessage> messages = new Vector<>();

    void exitFromRoom(String characterId) {
        members.remove(characterId);
    }

    boolean isInRoom(String characterId) {
        return members.contains(characterId);
    }

    public List<GameMessage> getMessages() {
        return new ArrayList<>(messages);
    }

    void addMember(String invitedCharacterId) {
        members.add(invitedCharacterId);
    }

    void sendMessage(String characterId, String message, GameMessageFactory gameMessageFactory) {
        messages.add(gameMessageFactory.create(characterId, message));
    }
}
