package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequest;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendrequest.FriendRequestDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
class DeclineFriendRequestService {
    private final FriendRequestDao friendRequestDao;
    private final FriendRequestQueryService friendRequestQueryService;

    void declineFriendRequest(String friendRequestId, String characterId) {
        FriendRequest friendRequest = friendRequestQueryService.findByFriendRequestId(friendRequestId);
        if (!friendRequest.getCharacterId().equals(characterId)
            && !friendRequest.getFriendId().equals(characterId)) {
            throw ExceptionFactory.invalidFriendRequestAccess(friendRequestId, characterId);
        }
        friendRequestDao.delete(friendRequest);
    }
}
