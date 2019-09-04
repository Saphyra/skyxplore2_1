package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequest;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.Friendship;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendrequest.FriendRequestDao;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendship.FriendshipDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
//TODO unit test
class AcceptFriendRequestService {
    private final FriendRequestDao friendRequestDao;
    private final FriendshipDao friendshipDao;
    private final FriendshipFactory friendshipFactory;
    private final FriendshipQueryService friendshipQueryService;

    @Transactional
    void acceptFriendRequest(String friendRequestId, String characterId) {
        FriendRequest friendRequest = friendshipQueryService.findFriendRequestById(friendRequestId);
        if (!friendRequest.getFriendId().equals(characterId)) {
            throw new ForbiddenException(characterId + "has no rights to accept friendRequest " + friendRequestId);
        }
        Friendship friendship = friendshipFactory.create(friendRequest);
        friendshipDao.save(friendship);
        friendRequestDao.delete(friendRequest);
    }
}
