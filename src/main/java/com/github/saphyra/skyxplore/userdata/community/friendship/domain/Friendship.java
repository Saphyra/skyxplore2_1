package com.github.saphyra.skyxplore.userdata.community.friendship.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Friendship {
    private String friendshipId;
    private String characterId;
    private String friendId;
}
