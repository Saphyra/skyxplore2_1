package com.github.saphyra.skyxplore.game.game.view;

import java.util.List;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
public class ChatRoomView {
    private final UUID roomId;
    private final String roomName;
    private final boolean defaultRoom;
    private final List<GameMessageView> messages;
}
