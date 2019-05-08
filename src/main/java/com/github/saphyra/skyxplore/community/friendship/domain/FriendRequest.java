package com.github.saphyra.skyxplore.community.friendship.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendRequest {
    private String friendRequestId;
    private String characterId;
    private String friendId;
}
