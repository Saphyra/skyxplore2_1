package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendrequest.FriendRequestDao;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendship.FriendshipDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
//TODO unit test
class ContactRemovalService {
    private final FriendRequestDao friendRequestDao;
    private final FriendshipDao friendshipDao;

    void removeContactsBetween(String characterId, String blockedCharacterId) {
        friendRequestDao.getByCharacterIdOrFriendId(characterId, blockedCharacterId).forEach(friendRequestDao::delete);
        friendshipDao.getByCharacterIdOrFriendId(characterId, blockedCharacterId).forEach(friendshipDao::delete);
    }
}
