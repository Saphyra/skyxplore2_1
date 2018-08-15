package skyxplore.controller;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.AddFriendRequest;
import skyxplore.controller.request.BlockCharacterRequest;
import skyxplore.controller.view.character.CharacterView;
import skyxplore.controller.view.character.CharacterViewConverter;
import skyxplore.controller.view.community.friend.FriendView;
import skyxplore.filter.AuthFilter;
import skyxplore.service.CommunityFacade;

@SuppressWarnings("unused")
@RestController
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class CommunityController {
    private static final String ACCEPT_FRIEND_REQUEST_MAPPING = "friend/{characterId}/request/accept/{requestId}";
    private static final String ADD_FRIEND_MAPPING = "friend/request/add";
    private static final String ALLOW_BLOCKED_CHARACTER_MAPPING = "blockedcharacter/{characterId}/allow/{blockedCharacterId}";
    private static final String BLOCK_CHARACTER_MAPPING = "blockcharacter/block";
    private static final String DECLINE_FRIEND_REQUEST_MAPPING = "friend/{characterId}/request/decline/{requestId}";
    private static final String DELETE_FRIEND_MAPPING = "friend/{characterId}/delete/{friendshipId}";
    private static final String GET_BLOCKED_CHARACTERS_MAPPING = "blockedcharacter/{characterId}";
    private static final String GET_CHARACTERS_CAN_BE_FRIEND_MAPPING = "friend/{characterId}/namelike/{charName}";
    private static final String GET_FRIENDS_MAPPING = "friend/{characterId}";
    private static final String GET_CHARACTERS_CAN_BE_BLOCKED_MAPPING = "blockcharacter/{characterId}/namelike/{charName}";
    private static final String GET_RECEIVED_FRIEND_REQUESTS_MaPPING = "friend/{characterId}/request/received";
    private static final String GET_SENT_FRIEND_REQUESTS_MaPPING = "friend/{characterId}/request/sent";

    private final CommunityFacade communityFacade;
    private final CharacterViewConverter characterViewConverter;

    @PostMapping(ACCEPT_FRIEND_REQUEST_MAPPING)
    public void acceptFriendRequest(
        @PathVariable("characterId") String characterId,
        @PathVariable("requestId") String requestId,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to accept friendRequest {}", characterId, requestId);
        //TODO implement
    }

    @PostMapping(ADD_FRIEND_MAPPING)
    public void addFriend(@Valid @RequestBody AddFriendRequest request, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId) {
        log.info("{} wants to add {} as a friend.", request.getCharacterId(), request.getFriendId());
        communityFacade.addFriendRequest(request, userId);
    }

    @PostMapping(ALLOW_BLOCKED_CHARACTER_MAPPING)
    public void allowBlockedCharacter(
        @PathVariable("characterId") String characterId,
        @PathVariable("blockedCharacterId") String blockedCharacterId,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to allow blockedCharacter {}", characterId, blockedCharacterId);
        //TODO implement
    }

    @PostMapping(BLOCK_CHARACTER_MAPPING)
    public void blockCharacter(@Valid @RequestBody BlockCharacterRequest request, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId) {
        log.info("{} wants to block {}", request.getCharacterId(), request.getBlockedCharacterId());
        communityFacade.blockCharacter(request, userId);
    }

    @PostMapping(DECLINE_FRIEND_REQUEST_MAPPING)
    public void declineFriendRequestMapping(
        @PathVariable("characterId") String characterId,
        @PathVariable("requestId") String requestId,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to decline / cancel friendRequest {}", characterId, requestId);
        //TODO implement
    }

    @DeleteMapping(DELETE_FRIEND_MAPPING)
    public void deleteFriend(
        @PathVariable("characterId") String characterId,
        @PathVariable("friendshipId") String friendshipId,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to delete friendship {}", characterId, friendshipId);
        //TODO implement
    }

    @GetMapping(GET_BLOCKED_CHARACTERS_MAPPING)
    public List<CharacterView> getBlockedCharacters(@PathVariable("characterId") String characterId, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId) {
        log.info("{} wants to know his blocked characters list.", characterId);
        return characterViewConverter.convertDomain(communityFacade.getBlockedCharacters(characterId, userId));
    }

    @GetMapping(GET_CHARACTERS_CAN_BE_BLOCKED_MAPPING)
    public List<CharacterView> getCharactersCanBeBlocked(@PathVariable("characterId") String characterId, @PathVariable("charName") String name, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId) throws ExecutionException {
        log.info("{} querying blockable characters by name like {}", characterId, name);
        return characterViewConverter.convertDomain(communityFacade.getCharactersCanBeBlocked(name, characterId, userId));
    }


    @GetMapping(GET_CHARACTERS_CAN_BE_FRIEND_MAPPING)
    public List<CharacterView> getCharactersCanBeFriend(@PathVariable("characterId") String characterId, @PathVariable("charName") String name, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId) throws ExecutionException {
        log.info("{} querying possible friend characters by name like {}", characterId, name);
        return characterViewConverter.convertDomain(communityFacade.getCharactersCanBeFriend(name, characterId, userId));
    }

    @GetMapping(GET_FRIENDS_MAPPING)
    public List<FriendView> getFriends(@PathVariable("characterId") String characterId, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId) {
        log.info("{} wants to know his friend list.", characterId);
        //TODO implement
        return Collections.emptyList();
    }

    @GetMapping(GET_RECEIVED_FRIEND_REQUESTS_MaPPING)
    public List<String> getReceivedFriendRequests(
        @PathVariable("characterId") String characterId,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to know his received friendRequests", characterId);
        //TODO implement
        return Collections.emptyList();
    }

    @GetMapping(GET_SENT_FRIEND_REQUESTS_MaPPING)
    public List<String> getSentFriendRequests(
        @PathVariable("characterId") String characterId,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to know his sent friendRequests", characterId);
        //TODO implement
        return Collections.emptyList();
    }
}
