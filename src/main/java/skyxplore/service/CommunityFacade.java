package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.controller.request.community.*;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.community.friendrequest.FriendRequest;
import skyxplore.domain.community.friendship.Friendship;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.service.community.BlockCharacterService;
import skyxplore.service.community.FriendshipQueryService;
import skyxplore.service.community.FriendshipService;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class CommunityFacade {
    private final BlockCharacterService blockCharacterService;
    private final CharacterQueryService characterQueryService;
    private final FriendshipService friendshipService;
    private final FriendshipQueryService friendshipQueryService;

    public void acceptFriendRequest(String friendRequestId, String characterId) {
        friendshipService.acceptFriendRequest(friendRequestId, characterId);
    }

    public void allowBlockedCharacter(String blockedCharacterId, String characterId) {
        blockCharacterService.allowBlockedCharacter(blockedCharacterId, characterId);
    }

    public void addFriendRequest(String friendId, String characterId) {
        friendshipService.addFriendRequest(friendId, characterId);
    }

    public void blockCharacter(String blockedCharacterId, String characterId) {
        blockCharacterService.blockCharacter(blockedCharacterId, characterId);
    }

    public void declineFriendRequest(DeclineFriendRequestRequest request, String userId) {
        friendshipService.declineFriendRequest(request, userId);
    }

    public void deleteFriendship(DeleteFriendRequest request, String userId) {
        friendshipService.deleteFriendship(request, userId);
    }

    public List<SkyXpCharacter> getBlockedCharacters(String characterId) {
        return characterQueryService.getBlockedCharacters(characterId);
    }

    public List<SkyXpCharacter> getCharactersCanBeBlocked(String name, String characterId, String userId) {
        return characterQueryService.getCharactersCanBeBlocked(name, characterId, userId);
    }

    public List<SkyXpCharacter> getCharactersCanBeFriend(String name, String characterId, String userId) {
        return characterQueryService.getCharactersCanBeFriend(name, characterId, userId);
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
