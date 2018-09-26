package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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

    public void declineFriendRequest(String friendRequestId, String characterId) {
        friendshipService.declineFriendRequest(friendRequestId, characterId);
    }

    public void deleteFriendship(String friendshipId, String characterId) {
        friendshipService.deleteFriendship(friendshipId, characterId);
    }

    public List<SkyXpCharacter> getBlockedCharacters(String characterId) {
        return characterQueryService.getBlockedCharacters(characterId);
    }

    public List<SkyXpCharacter> getCharactersCanBeBlocked(String name, String characterId) {
        return characterQueryService.getCharactersCanBeBlocked(name, characterId);
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
