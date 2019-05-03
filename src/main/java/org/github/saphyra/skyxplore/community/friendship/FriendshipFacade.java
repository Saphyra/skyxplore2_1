package org.github.saphyra.skyxplore.community.friendship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class FriendshipFacade {
    private final FriendshipQueryService friendshipQueryService;

    public Integer getNumberOfFriendRequests(String characterId) {
        return friendshipQueryService.getNumberOfFriendRequests(characterId);
    }
}
