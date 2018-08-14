package skyxplore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import skyxplore.controller.request.AddFriendRequest;
import skyxplore.controller.request.BlockCharacterRequest;
import skyxplore.controller.view.character.CharacterView;
import skyxplore.controller.view.character.CharacterViewConverter;
import skyxplore.filter.AuthFilter;
import skyxplore.service.CommunityFacade;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

@SuppressWarnings("unused")
@RestController
@Slf4j
@RequiredArgsConstructor
public class CommunityController {
    private static final String ADD_FRIEND_MAPPING = "friend/add";
    private static final String BLOCK_CHARACTER_MAPPING = "blockcharacter/block";
    private static final String GET_CHARACTERS_CAN_BE_FRIEND_MAPPING = "friend/{characterId}/namelike/{charName}";
    private static final String GET_BLOCKABLE_CHARACTERS = "blockcharacter/{characterId}/namelike/{charName}";

    private final CommunityFacade communityFacade;
    private final CharacterViewConverter characterViewConverter;

    @PostMapping(ADD_FRIEND_MAPPING)
    public void addFriend(@Valid @RequestBody AddFriendRequest request, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId) {
        log.info("{} wants to add {} as a friend.", request.getCharacterId(), request.getFriendId());
        communityFacade.addFriendRequest(request, userId);
    }

    @PostMapping(BLOCK_CHARACTER_MAPPING)
    public void blockCharacter(@Valid @RequestBody BlockCharacterRequest request, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId){
        log.info("{} wants to block {}", request.getCharacterId(), request.getBlockedCharacterId());
        //TODO implement
    }

    @GetMapping(GET_BLOCKABLE_CHARACTERS)
    public List<CharacterView> getBlockableCharacters(@PathVariable("characterId") String characterId, @PathVariable("charName") String name, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId) throws ExecutionException {
        log.info("{} querying blockable characters by name like {}", characterId, name);
        return characterViewConverter.convertDomain(communityFacade.getBlockableCharacters(name, characterId, userId));
    }


    @GetMapping(GET_CHARACTERS_CAN_BE_FRIEND_MAPPING)
    public List<CharacterView> getCharactersCanBeFriend(@PathVariable("characterId") String characterId, @PathVariable("charName") String name, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId) throws ExecutionException {
        log.info("{} querying possible friend characters by name like {}", characterId, name);
        return characterViewConverter.convertDomain(communityFacade.getCharactersCanBeFriend(name, characterId, userId));
    }
}
