package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import org.springframework.stereotype.Service;

import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequest;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendrequest.FriendRequestDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class DeclineFriendRequestService {
    private final FriendRequestDao friendRequestDao;
    private final FriendshipQueryService friendshipQueryService;

    void declineFriendRequest(String friendRequestId, String characterId) {
        FriendRequest friendRequest = friendshipQueryService.findFriendRequestById(friendRequestId);
        if (!friendRequest.getCharacterId().equals(characterId)
            && !friendRequest.getFriendId().equals(characterId)) {
            //TODO replace with ExceptionFactory
            throw new UnauthorizedException(friendRequestId + " is not a friendRequest of character " + characterId);
        }
        friendRequestDao.delete(friendRequest);
    }
}
