package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequest;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.Friendship;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class FriendshipFactory {
    private final IdGenerator idGenerator;

    Friendship create(FriendRequest friendRequest) {
        return Friendship.builder()
            .friendshipId(idGenerator.generateRandomId())
            .characterId(friendRequest.getCharacterId())
            .friendId(friendRequest.getFriendId())
            .build();
    }
}
