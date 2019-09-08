package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendshipServiceFacade {
    private final AcceptFriendRequestService acceptFriendRequestService;
    private final ActiveFriendsQueryService activeFriendsQueryService;
    private final AddFriendRequestService addFriendRequestService;
    private final ContactRemovalService contactRemovalService;
    private final DeclineFriendRequestService declineFriendRequestService;
    private final DeleteFriendshipService deleteFriendshipService;

    @Transactional
    public void acceptFriendRequest(String friendRequestId, String characterId) {
        acceptFriendRequestService.acceptFriendRequest(friendRequestId, characterId);
    }

    public List<SkyXpCharacter> getActiveFriends(String characterId) {
        return activeFriendsQueryService.getActiveFriends(characterId);
    }

    @Transactional
    public void addFriendRequest(String friendId, String characterId, String userId) {
        addFriendRequestService.addFriendRequest(friendId, characterId, userId);
    }

    @Transactional
    public void declineFriendRequest(String friendRequestId, String characterId) {
        declineFriendRequestService.declineFriendRequest(friendRequestId, characterId);
    }

    @Transactional
    public void deleteFriendship(String friendshipId, String characterId) {
        deleteFriendshipService.deleteFriendship(friendshipId, characterId);
    }

    @Transactional
    public void removeContactsBetween(String characterId, String blockedCharacterId) {
        contactRemovalService.removeContactsBetween(characterId, blockedCharacterId);
    }
}
