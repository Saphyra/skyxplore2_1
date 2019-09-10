package com.github.saphyra.skyxplore.game.game.view;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class GameMessageView {
    @NonNull
    private final String senderId;

    @NonNull
    private final String senderName;

    @NonNull
    private final String message;

    @NonNull
    private final Long createdAt;
}
