package org.github.saphyra.skyxplore.community.friendship;

import lombok.RequiredArgsConstructor;
import org.github.saphyra.skyxplore.community.friendship.domain.FriendRequestView;
import org.springframework.stereotype.Component;
import org.github.saphyra.skyxplore.common.AbstractViewConverter;
import org.github.saphyra.skyxplore.community.friendship.domain.FriendRequest;
import org.github.saphyra.skyxplore.character.CharacterQueryService;

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
