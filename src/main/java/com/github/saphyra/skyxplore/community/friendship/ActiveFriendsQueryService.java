package com.github.saphyra.skyxplore.community.friendship;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.auth.repository.AccessTokenDao;
import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.community.friendship.domain.Friendship;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class ActiveFriendsQueryService {
    private final AccessTokenDao accessTokenDao;
    private final CharacterQueryService characterQueryService;
    private final FriendshipQueryService friendshipQueryService;
    private final LobbyQueryService lobbyQueryService;

    List<SkyXpCharacter> getActiveFriends(String characterId) {
        List<Friendship> friendships = friendshipQueryService.getFriends(characterId);
        List<String> friendIds = friendships.stream()
            .map(friendship -> fetchCharacterId(friendship, characterId))
            .collect(Collectors.toList());

        return friendIds.stream()
            .map(characterQueryService::findByCharacterId)
            .filter(skyXpCharacter -> accessTokenDao.findByCharacterId(skyXpCharacter.getCharacterId()).isPresent())
            .filter(skyXpCharacter -> !lobbyQueryService.findByCharacterId(skyXpCharacter.getCharacterId()).isPresent())
            .collect(Collectors.toList());
    }

    private String fetchCharacterId(Friendship friendship, String characterId) {
        return friendship.getCharacterId().equals(characterId) ? friendship.getFriendId() : friendship.getCharacterId();
    }
}
