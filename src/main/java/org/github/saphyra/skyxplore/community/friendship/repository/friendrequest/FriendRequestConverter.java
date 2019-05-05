package org.github.saphyra.skyxplore.community.friendship.repository.friendrequest;

import org.github.saphyra.skyxplore.community.friendship.domain.FriendRequest;
import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;

@Component
class FriendRequestConverter extends ConverterBase<FriendRequestEntity, FriendRequest> {
    @Override
    public FriendRequest processEntityConversion(FriendRequestEntity entity) {
        return FriendRequest.builder()
            .friendRequestId(entity.getFriendRequestId())
            .characterId(entity.getCharacterId())
            .friendId(entity.getFriendId())
            .build();
    }

    @Override
    public FriendRequestEntity processDomainConversion(FriendRequest domain) {
        return FriendRequestEntity.builder()
            .friendRequestId(domain.getFriendRequestId())
            .characterId(domain.getCharacterId())
            .friendId(domain.getFriendId())
            .build();
    }
}
