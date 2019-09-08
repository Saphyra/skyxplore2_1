package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.Friendship;
import com.github.saphyra.skyxplore.userdata.community.friendship.repository.friendship.FriendshipDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendshipQueryService {
    private final FriendshipDao friendshipDao;

    Friendship findFriendshipById(String friendshipId) {
        return friendshipDao.findById(friendshipId)
            .orElseThrow(() -> ExceptionFactory.friendshipNotFound(friendshipId));
    }

    public List<Friendship> getFriends(String characterId) {
        return friendshipDao.getFriendshipsOfCharacter(characterId);
    }

    public boolean isFriendshipAlreadyExists(String characterId, String friendId) {
        return friendshipDao.getByCharacterIdOrFriendId(characterId, friendId).size() > 0;
    }
}
