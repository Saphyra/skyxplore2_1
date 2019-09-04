package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import org.springframework.stereotype.Service;

import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.Friendship;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendship.FriendshipDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class DeleteFriendshipService {
    private final FriendshipDao friendshipDao;
    private final FriendshipQueryService friendshipQueryService;

    void deleteFriendship(String friendshipId, String characterId) {
        Friendship friendship = friendshipQueryService.findFriendshipById(friendshipId);
        if (!friendship.getFriendId().equals(characterId)
            && !friendship.getCharacterId().equals(characterId)) {
            //TODO replace with ExceptionFactory
            throw new UnauthorizedException(characterId + " has no access to friendship " + friendshipId);
        }
        friendshipDao.delete(friendship);
    }
}
