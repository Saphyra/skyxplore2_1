package com.github.saphyra.skyxplore.community.friendship;

import com.github.saphyra.skyxplore.auth.repository.AccessTokenDao;
import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.github.saphyra.skyxplore.common.AbstractViewConverter;
import com.github.saphyra.skyxplore.community.friendship.domain.FriendView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.community.friendship.domain.Friendship;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class FriendViewConverter extends AbstractViewConverter<Friendship, FriendView> {
    private final AccessTokenDao accessTokenDao;
    private final CharacterQueryService characterQueryService;

    @Override
    public FriendView convertDomain(Friendship domain) {
        FriendView view = new FriendView();
        view.setFriendshipId(domain.getFriendshipId());
        view.setFriendId(domain.getFriendId());
        view.setFriendName(characterQueryService.findByCharacterId(domain.getFriendId()).getCharacterName());
        view.setActive(accessTokenDao.findByCharacterId(domain.getFriendId()).isPresent());
        return view;
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