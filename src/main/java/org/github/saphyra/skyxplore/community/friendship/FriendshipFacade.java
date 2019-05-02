package org.github.saphyra.skyxplore.community.friendship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.community.friendship.domain.FriendRequest;
import org.github.saphyra.skyxplore.community.friendship.domain.Friendship;
import org.springframework.stereotype.Component;
import skyxplore.service.community.FriendshipService;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class FriendshipFacade {
    private final CharacterQueryService characterQueryService;
    private final FriendshipService friendshipService;
    private final FriendshipQueryService friendshipQueryService;

    public void acceptFriendRequest(String friendRequestId, String characterId) {
        friendshipService.acceptFriendRequest(friendRequestId, characterId);
    }

    public void addFriendRequest(String friendId, String characterId, String userId) {
        friendshipService.addFriendRequest(friendId, characterId, userId);
    }

    public void declineFriendRequest(String friendRequestId, String characterId) {
        friendshipService.declineFriendRequest(friendRequestId, characterId);
    }

    public void deleteFriendship(String friendshipId, String characterId) {
        friendshipService.deleteFriendship(friendshipId, characterId);
    }

    public List<SkyXpCharacter> getCharactersCanBeFriend(String name, String characterId) {
        return characterQueryService.getCharactersCanBeFriend(name, characterId);
    }

    public List<Friendship> getFriends(String characterId) {
        return friendshipQueryService.getFriends(characterId);
    }

    public Integer getNumberOfFriendRequests(String characterId) {
        return friendshipQueryService.getNumberOfFriendRequests(characterId);
    }

    public List<FriendRequest> getReceivedFriendRequests(String characterId) {
        return friendshipQueryService.getReceivedFriendRequests(characterId);
    }

    public List<FriendRequest> getSentFriendRequests(String characterId) {
        return friendshipQueryService.getSentFriendRequests(characterId);
    }

}
