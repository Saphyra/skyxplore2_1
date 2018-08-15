package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.controller.request.AddFriendRequest;
import skyxplore.controller.request.AllowBlockedCharacterRequest;
import skyxplore.controller.request.BlockCharacterRequest;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.community.friendrequest.FriendRequest;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.service.community.BlockCharacterService;
import skyxplore.service.community.FriendshipService;
import skyxplore.service.community.FriendshipQueryService;

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


    public void allowBlockedCharacter(AllowBlockedCharacterRequest request, String userId) {
        blockCharacterService.allowBlockedCharacter(request, userId);
    }

    public void addFriendRequest(AddFriendRequest request, String userId) {
        friendshipService.addFriendRequest(request, userId);
    }

    public void blockCharacter(BlockCharacterRequest request, String userId) {
        blockCharacterService.blockCharacter(request, userId);
    }

    public List<SkyXpCharacter> getBlockedCharacters(String characterId, String userId) {
        return characterQueryService.getBlockedCharacters(characterId, userId);
    }

    public List<SkyXpCharacter> getCharactersCanBeBlocked(String name, String characterId, String userId) {
        return characterQueryService.getCharactersCanBeBlocked(name, characterId, userId);
    }

    public List<SkyXpCharacter> getCharactersCanBeFriend(String name, String characterId, String userId) {
        return characterQueryService.getCharactersCanBeFriend(name, characterId, userId);
    }

    public List<FriendRequest> getReceivedFriendRequests(String characterId, String userId) {
        return friendshipQueryService.getReceivedFriendRequests(characterId, userId);
    }

    public List<FriendRequest> getSentFriendRequests(String characterId, String userId) {
        return friendshipQueryService.getSentFriendRequests(characterId, userId);
    }
}
