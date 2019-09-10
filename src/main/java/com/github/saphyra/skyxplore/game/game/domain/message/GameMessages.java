package com.github.saphyra.skyxplore.game.game.domain.message;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameMessages {
    @NonNull
    private final Map<UUID, ChatRoom> chatRooms = new ConcurrentHashMap<>();

    public void addRoom(UUID roomId, ChatRoom chatRoom) {
        chatRooms.put(roomId, chatRoom);
    }
}
