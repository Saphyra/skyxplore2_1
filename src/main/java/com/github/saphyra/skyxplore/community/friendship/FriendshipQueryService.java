package com.github.saphyra.skyxplore.community.friendship;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.exception.FriendRequestNotFoundException;
import com.github.saphyra.skyxplore.common.exception.FriendshipNotFoundException;
import com.github.saphyra.skyxplore.community.friendship.domain.FriendRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.community.friendship.repository.friendrequest.FriendRequestDao;
import com.github.saphyra.skyxplore.community.friendship.repository.friendship.FriendshipDao;
import com.github.saphyra.skyxplore.community.friendship.domain.Friendship;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendshipQueryService {
    private final FriendRequestDao friendRequestDao;
    private final FriendshipDao friendshipDao;

    public FriendRequest findFriendRequestById(String friendRequestId) {
        return friendRequestDao.findById(friendRequestId)
            .orElseThrow(() -> new FriendRequestNotFoundException("SeleniumFriendRequest not found with id " + friendRequestId));
    }

    public Friendship findFriendshipById(String friendshipId) {
        return friendshipDao.findById(friendshipId)
            .orElseThrow(() -> new FriendshipNotFoundException("Friendship not found with id " + friendshipId));
    }

    public List<Friendship> getFriends(String characterId) {
        return friendshipDao.getFriendshipsOfCharacter(characterId);
    }

    public Integer getNumberOfFriendRequests(String characterId) {
        return getReceivedFriendRequests(characterId).size();
    }

    public List<FriendRequest> getReceivedFriendRequests(String characterId) {
        return friendRequestDao.getByFriendId(characterId).stream()
            .map(this::swapIds)
            .collect(Collectors.toList());
    }

    private FriendRequest swapIds(FriendRequest request) {
        String characterId = request.getCharacterId();
        request.setCharacterId(request.getFriendId());
        request.setFriendId(characterId);
        return request;
    }

    public List<FriendRequest> getSentFriendRequests(String characterId) {
        return friendRequestDao.getByCharacterId(characterId);
    }

    public boolean isFriendshipAlreadyExists(String characterId, String friendId) {
        return friendshipDao.getByCharacterIdOrFriendId(characterId, friendId).size() > 0;
    }

    public boolean isFriendRequestAlreadyExists(String characterId, String friendId) {
        return friendRequestDao.getByCharacterIdOrFriendId(characterId, friendId).size() > 0;
    }

    public boolean isFriendshipOrFriendRequestAlreadyExists(String characterId, String friendId) {
        return isFriendRequestAlreadyExists(characterId, friendId)
            || isFriendshipAlreadyExists(characterId, friendId);
    }
}
