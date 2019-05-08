package com.github.saphyra.skyxplore.community.friendship.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendRequestView {
    private String characterId;
    private String friendRequestId;
    private String friendId;
    private String friendName;
}
