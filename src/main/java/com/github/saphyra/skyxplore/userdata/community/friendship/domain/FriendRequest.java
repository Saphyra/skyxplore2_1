package com.github.saphyra.skyxplore.userdata.community.friendship.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class FriendRequest {
    @NonNull
    private final String friendRequestId;

    @NonNull
    private final String characterId;

    @NonNull
    private final String friendId;
}
