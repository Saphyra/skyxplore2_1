package com.github.saphyra.skyxplore.userdata.community.friendship;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.platform.auth.repository.AccessTokenDao;
import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.common.AbstractViewConverter;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendView;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.Friendship;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class FriendViewConverter extends AbstractViewConverter<Friendship, FriendView> {
    private final AccessTokenDao accessTokenDao;
    private final CharacterQueryService characterQueryService;

    @Override
    public FriendView convertDomain(Friendship domain) {
        return FriendView.builder()
            .friendshipId(domain.getFriendshipId())
            .friendId(domain.getFriendId())
            .friendName(characterQueryService.findByCharacterIdValidated(domain.getFriendId()).getCharacterName())
            .active(accessTokenDao.findByCharacterId(domain.getFriendId()).isPresent())
            .build();
    }

    public List<FriendView> convertDomain(List<Friendship> domain, String characterId) {
        return domain
            .stream()
            .map(f -> checkIds(f, characterId))
            .map(this::convertDomain)
            .collect(Collectors.toList());
    }

    private Friendship checkIds(Friendship friendship, String characterId) {
        if (friendship.getFriendId().equals(characterId)) {
            swapIds(friendship);
        }
        return friendship;
    }

    private void swapIds(Friendship friendship) {
        String characterId = friendship.getCharacterId();
        friendship.setCharacterId(friendship.getFriendId());
        friendship.setFriendId(characterId);
    }
}
