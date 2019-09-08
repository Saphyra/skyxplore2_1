package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class ContactQueryService {
    private final FriendshipQueryService friendshipQueryService;
    private final FriendRequestQueryService friendRequestQueryService;


    boolean isFriendshipOrFriendRequestAlreadyExists(String characterId, String friendId) {
        return friendRequestQueryService.isFriendRequestAlreadyExists(characterId, friendId)
            || friendshipQueryService.isFriendshipAlreadyExists(characterId, friendId);
    }
}
