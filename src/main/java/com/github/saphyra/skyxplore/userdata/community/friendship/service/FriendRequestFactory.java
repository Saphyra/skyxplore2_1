package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequest;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class FriendRequestFactory {
    private final IdGenerator idGenerator;

    FriendRequest create(String characterId, String friendId) {
        return FriendRequest.builder()
            .friendRequestId(idGenerator.generateRandomId())
            .characterId(characterId)
            .friendId(friendId)
            .build();
    }
}
