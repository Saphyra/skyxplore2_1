package com.github.saphyra.skyxplore.common.domain.message;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MessageView {
    @NonNull
    private final String senderId;

    @NonNull
    private final String senderName;

    @NonNull
    private final String message;

    @NonNull
    private final Long createdAt;
}
