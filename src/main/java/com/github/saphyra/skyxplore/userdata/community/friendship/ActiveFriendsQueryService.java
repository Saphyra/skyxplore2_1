package com.github.saphyra.skyxplore.userdata.community.friendship;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.platform.auth.repository.AccessTokenDao;
import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.Friendship;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
class ActiveFriendsQueryService {
    private final AccessTokenDao accessTokenDao;
    private final CharacterQueryService characterQueryService;
    private final FriendshipQueryService friendshipQueryService;
    private final LobbyQueryService lobbyQueryService;

    List<SkyXpCharacter> getActiveFriends(String characterId) {
        List<Friendship> friendships = friendshipQueryService.getFriends(characterId);
        return friendships.stream()
            .map(friendship -> fetchCharacterId(friendship, characterId))
            .filter(friendId -> accessTokenDao.findByCharacterId(friendId).isPresent())
            .filter(friendId -> !lobbyQueryService.findByCharacterId(friendId).isPresent())
            .map(characterQueryService::findByCharacterId)
            .collect(Collectors.toList());
    }

    private String fetchCharacterId(Friendship friendship, String characterId) {
        return friendship.getCharacterId().equals(characterId) ? friendship.getFriendId() : friendship.getCharacterId();
    }
}
