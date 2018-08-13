package skyxplore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import skyxplore.controller.request.AddFriendRequest;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.service.community.FriendService;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CommunityFacade {
    private final CharacterQueryService characterQueryService;
    private final FriendService friendService;

    public void addFriendRequest(AddFriendRequest request, String userId) {
        friendService.addFriendRequest(request, userId);
    }

    public List<SkyXpCharacter> getBlockableCharacters(String name, String characterId, String userId) {
        return characterQueryService.getBlockableCharacters(name, characterId, userId);
    }

    public List<SkyXpCharacter> getCharactersCanBeFriend(String name, String characterId, String userId) {
        return characterQueryService.getCharactersCanBeFriend(name, characterId, userId);
    }
}
