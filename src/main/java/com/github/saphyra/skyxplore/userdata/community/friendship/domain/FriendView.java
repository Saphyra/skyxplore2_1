package com.github.saphyra.skyxplore.userdata.community.friendship.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class FriendView {
    @NonNull
    private final String friendshipId;

    @NonNull
    private final String friendId;

    @NonNull
    private final String friendName;

    @NonNull
    private final Boolean active;
}
