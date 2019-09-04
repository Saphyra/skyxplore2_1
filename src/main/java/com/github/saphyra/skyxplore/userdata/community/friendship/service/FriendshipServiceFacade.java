package com.github.saphyra.skyxplore.userdata.community.friendship.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    //TODO unit test
    public void acceptFriendRequest(String friendRequestId, String characterId) {
        acceptFriendRequestService.acceptFriendRequest(friendRequestId, characterId);
    }

    //TODO unit test
    public List<SkyXpCharacter> getActiveFriends(String characterId) {
        return activeFriendsQueryService.getActiveFriends(characterId);
    }

    @Transactional
    //TODO unit test
    public void addFriendRequest(String friendId, String characterId, String userId) {
        addFriendRequestService.addFriendRequest(friendId, characterId, userId);
    }

    @Transactional
    //TODO unit test
    public void declineFriendRequest(String friendRequestId, String characterId) {
        declineFriendRequestService.declineFriendRequest(friendRequestId, characterId);
    }

    @Transactional
    //TODO unit test
    public void deleteFriendship(String friendshipId, String characterId) {
        deleteFriendshipService.deleteFriendship(friendshipId, characterId);
    }

    @Transactional
    //TODO unit test
    public void removeContactsBetween(String characterId, String blockedCharacterId) {
        contactRemovalService.removeContactsBetween(characterId, blockedCharacterId);
    }
}
