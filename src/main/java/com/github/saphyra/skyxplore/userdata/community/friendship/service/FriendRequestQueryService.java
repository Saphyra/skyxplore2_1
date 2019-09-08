package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequest;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendrequest.FriendRequestDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendRequestQueryService {

    private final FriendRequestDao friendRequestDao;

    FriendRequest findByFriendRequestId(String friendRequestId) {
        return friendRequestDao.findById(friendRequestId)
            .orElseThrow(() -> ExceptionFactory.friendRequestNotFound(friendRequestId));
    }

    public Integer getNumberOfFriendRequests(String characterId) {
        return friendRequestDao.getByFriendId(characterId).size();
    }

    public List<FriendRequest> getReceivedFriendRequests(String characterId) {
        return friendRequestDao.getByFriendId(characterId).stream()
            .map(this::swapIds)
            .collect(Collectors.toList());
    }

    private FriendRequest swapIds(FriendRequest request) {
        return FriendRequest.builder()
            .friendRequestId(request.getFriendRequestId())
            .characterId(request.getFriendId())
            .friendId(request.getCharacterId())
            .build();
    }

    public List<FriendRequest> getSentFriendRequests(String characterId) {
        return friendRequestDao.getByCharacterId(characterId);
    }

    public boolean isFriendRequestAlreadyExists(String characterId, String friendId) {
        return friendRequestDao.getByCharacterIdOrFriendId(characterId, friendId).size() > 0;
    }
}
