package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequest;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.Friendship;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendrequest.FriendRequestDao;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendship.FriendshipDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
class AcceptFriendRequestService {
    private final FriendRequestDao friendRequestDao;
    private final FriendshipDao friendshipDao;
    private final FriendshipFactory friendshipFactory;
    private final FriendRequestQueryService friendRequestQueryService;

    @Transactional
    void acceptFriendRequest(String friendRequestId, String characterId) {
        FriendRequest friendRequest = friendRequestQueryService.findByFriendRequestId(friendRequestId);
        if (!friendRequest.getFriendId().equals(characterId)) {
            throw ExceptionFactory.invalidFriendRequestAccess(friendRequestId, characterId);
        }

        Friendship friendship = friendshipFactory.create(friendRequest);
        friendshipDao.save(friendship);
        friendRequestDao.delete(friendRequest);
    }
}
