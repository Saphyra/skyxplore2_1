package com.github.saphyra.skyxplore.community.friendship.repository.friendship;

import com.github.saphyra.skyxplore.community.friendship.domain.Friendship;
import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;

@Component
public class FriendshipConverter extends ConverterBase<FriendshipEntity, Friendship> {
    @Override
    public Friendship processEntityConversion(FriendshipEntity entity) {
        Friendship friendship = new Friendship();
        friendship.setFriendshipId(entity.getFriendshipId());
        friendship.setCharacterId(entity.getCharacterId());
        friendship.setFriendId(entity.getFriendId());
        return friendship;
    }

    @Override
    public FriendshipEntity processDomainConversion(Friendship domain) {
        FriendshipEntity entity = new FriendshipEntity();
        entity.setFriendshipId(domain.getFriendshipId());
        entity.setCharacterId(domain.getCharacterId());
        entity.setFriendId(domain.getFriendId());
        return entity;
    }
}
