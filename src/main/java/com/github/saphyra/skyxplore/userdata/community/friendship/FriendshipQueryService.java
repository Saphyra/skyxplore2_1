package com.github.saphyra.skyxplore.userdata.community.friendship;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.exception.FriendRequestNotFoundException;
import com.github.saphyra.skyxplore.common.exception.FriendshipNotFoundException;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequest;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.Friendship;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendrequest.FriendRequestDao;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendship.FriendshipDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendshipQueryService {
    private final FriendRequestDao friendRequestDao;
    private final FriendshipDao friendshipDao;

    FriendRequest findFriendRequestById(String friendRequestId) {
        return friendRequestDao.findById(friendRequestId)
            .orElseThrow(() -> new FriendRequestNotFoundException("SeleniumFriendRequest not found with id " + friendRequestId));
    }

    Friendship findFriendshipById(String friendshipId) {
        return friendshipDao.findById(friendshipId)
            .orElseThrow(() -> new FriendshipNotFoundException("Friendship not found with id " + friendshipId));
    }

    List<Friendship> getFriends(String characterId) {
        return friendshipDao.getFriendshipsOfCharacter(characterId);
    }

    public Integer getNumberOfFriendRequests(String characterId) {
        return friendRequestDao.getByFriendId(characterId).size();
    }

    List<FriendRequest> getReceivedFriendRequests(String characterId) {
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

    public boolean isFriendshipAlreadyExists(String characterId, String friendId) {
        return friendshipDao.getByCharacterIdOrFriendId(characterId, friendId).size() > 0;
    }

    public boolean isFriendRequestAlreadyExists(String characterId, String friendId) {
        return friendRequestDao.getByCharacterIdOrFriendId(characterId, friendId).size() > 0;
    }

    boolean isFriendshipOrFriendRequestAlreadyExists(String characterId, String friendId) {
        return isFriendRequestAlreadyExists(characterId, friendId)
            || isFriendshipAlreadyExists(characterId, friendId);
    }
}
