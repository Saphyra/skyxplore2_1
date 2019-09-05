package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.community.blockedcharacter.BlockedCharacterQueryService;
import com.github.saphyra.skyxplore.userdata.community.blockedcharacter.domain.BlockedCharacter;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequest;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendrequest.FriendRequestDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
class AddFriendRequestService {
    private final BlockedCharacterQueryService blockedCharacterQueryService;
    private final CharacterQueryService characterQueryService;
    private final FriendRequestDao friendRequestDao;
    private final FriendRequestFactory friendRequestFactory;
    private final FriendshipQueryService friendshipQueryService;

    void addFriendRequest(String friendId, String characterId, String userId) {
        //Check if character with the given characterId exists
        characterQueryService.findByCharacterIdValidated(friendId);
        List<BlockedCharacter> blockedCharacter = blockedCharacterQueryService.findByCharacterIdOrBlockedCharacterId(characterId, friendId);

        if (!blockedCharacter.isEmpty()) {
            throw ExceptionFactory.characterBlockedException(characterId, friendId);
        }
        if (friendshipQueryService.isFriendshipOrFriendRequestAlreadyExists(characterId, friendId)) {
            throw ExceptionFactory.friendshipAlreadyExists(characterId, friendId);
        }
        if (isOwnCharacter(friendId, userId)) {
            throw ExceptionFactory.wrongFriendId();
        }

        FriendRequest friendRequest = friendRequestFactory.create(characterId, friendId);
        friendRequestDao.save(friendRequest);
    }

    private boolean isOwnCharacter(String friendId, String userId) {
        return characterQueryService.getCharactersByUserId(userId).stream()
            .anyMatch(skyXpCharacter -> skyXpCharacter.getCharacterId().equals(friendId));
    }
}
