package com.github.saphyra.skyxplore.game.game.domain.message;

import com.github.saphyra.skyxplore.game.lobby.message.domain.LobbyMessage;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
//TODO unit test
public class ChatRoom {
    @NonNull
    @Getter
    private final UUID roomId;

    @NonNull
    private final String displayName;

    @Builder.Default
    private final boolean defaultRoom = false;

    @NonNull
    private final List<String> members;

    @Builder.Default
    private final List<LobbyMessage> lobbyMessages = new Vector<>();

    void exitFromRoom(String characterId) {
        members.remove(characterId);
    }
}
