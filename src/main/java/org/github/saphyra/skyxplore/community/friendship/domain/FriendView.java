package org.github.saphyra.skyxplore.community.friendship.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendView {
    private String friendshipId;
    private String friendId;
    private String friendName;
    private Boolean active;
}
