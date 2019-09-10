package com.github.saphyra.skyxplore.game.game.domain.message;

import java.util.List;
import java.util.UUID;
import java.util.Vector;

import com.github.saphyra.skyxplore.common.domain.message.Message;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ChatRoom {
    @NonNull
    @Getter
    private final UUID roomId;

    @NonNull
    private final String displayName;

    private final boolean defaultRoom;

    @NonNull
    private final List<String> members;

    @Builder.Default
    private final List<Message> messages = new Vector<>();
}
