package com.github.saphyra.skyxplore.userdata.community.friendship;

import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.common.AbstractViewConverter;
import lombok.RequiredArgsConstructor;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequestView;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequest;

@Component
@RequiredArgsConstructor
public class FriendRequestViewConverter extends AbstractViewConverter<FriendRequest, FriendRequestView> {
    private final CharacterQueryService characterQueryService;

    @Override
    public FriendRequestView convertDomain(FriendRequest domain) {
        return FriendRequestView.builder()
            .characterId(domain.getCharacterId())
            .friendRequestId(domain.getFriendRequestId())
            .friendId(domain.getFriendId())
            .friendName(characterQueryService.findByCharacterId(domain.getFriendId()).getCharacterName())
            .build();
    }
}
