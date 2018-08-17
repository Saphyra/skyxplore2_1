package skyxplore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import skyxplore.controller.request.AddFriendRequest;
import skyxplore.controller.request.AllowBlockedCharacterRequest;
import skyxplore.controller.request.BlockCharacterRequest;
import skyxplore.controller.request.DeclineFriendRequestRequest;
import skyxplore.controller.view.character.CharacterView;
import skyxplore.controller.view.character.CharacterViewConverter;
import skyxplore.controller.view.community.friend.FriendView;
import skyxplore.controller.view.community.friend.FriendViewConverter;
import skyxplore.controller.view.community.friendrequest.FriendRequestView;
import skyxplore.controller.view.community.friendrequest.FriendRequestViewConverter;
import skyxplore.filter.AuthFilter;
import skyxplore.service.CommunityFacade;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("unused")
@RestController
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class CommunityController {
    private static final String ACCEPT_FRIEND_REQUEST_MAPPING = "friend/{characterId}/friendrequest/accept/{requestId}";
    private static final String ADD_FRIEND_MAPPING = "friend/friendrequest/add";
    private static final String ALLOW_BLOCKED_CHARACTER_MAPPING = "blockedcharacter/allow";
    private static final String BLOCK_CHARACTER_MAPPING = "blockcharacter/block";
    private static final String DECLINE_FRIEND_REQUEST_MAPPING = "friend//friendrequest/decline";
    private static final String DELETE_FRIEND_MAPPING = "friend/{characterId}/delete/{friendshipId}";
    private static final String GET_BLOCKED_CHARACTERS_MAPPING = "blockedcharacter/{characterId}";
    private static final String GET_CHARACTERS_CAN_BE_FRIEND_MAPPING = "friend/{characterId}/namelike/{charName}";
    private static final String GET_FRIENDS_MAPPING = "friend/{characterId}";
    private static final String GET_CHARACTERS_CAN_BE_BLOCKED_MAPPING = "blockcharacter/{characterId}/namelike/{charName}";
    private static final String GET_RECEIVED_FRIEND_REQUESTS_MAPPING = "friend//friendrequest/received/{characterId}";
    private static final String GET_SENT_FRIEND_REQUESTS_MAPPING = "friend/friendrequest/sent/{characterId}";

    private final CommunityFacade communityFacade;
    private final CharacterViewConverter characterViewConverter;
    private final FriendRequestViewConverter friendRequestViewConverter;
    private final FriendViewConverter friendViewConverter;

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
        log.info("{} wants to add {} as a community.", request.getCharacterId(), request.getFriendId());
        communityFacade.addFriendRequest(request, userId);
    }

    @PostMapping(ALLOW_BLOCKED_CHARACTER_MAPPING)
    public void allowBlockedCharacter(
        @RequestBody @Valid AllowBlockedCharacterRequest request,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to allow blockedCharacter {}", request.getCharacterId(), request.getBlockedCharacterId());
        communityFacade.allowBlockedCharacter(request, userId);
    }

    @PostMapping(BLOCK_CHARACTER_MAPPING)
    public void blockCharacter(@Valid @RequestBody BlockCharacterRequest request, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId) {
        log.info("{} wants to block {}", request.getCharacterId(), request.getBlockedCharacterId());
        communityFacade.blockCharacter(request, userId);
    }

    @PostMapping(DECLINE_FRIEND_REQUEST_MAPPING)
    public void declineFriendRequestMapping(
        @RequestBody @Valid DeclineFriendRequestRequest request,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to decline / cancel friendRequest {}", request.getCharacterId(), request.getFriendRequestId());
        communityFacade.declineFriendRequest(request, userId);
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
    public List<CharacterView> getCharactersCanBeBlocked(@PathVariable("characterId") String characterId, @PathVariable("charName") String name, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId) {
        log.info("{} querying blockable characters by name like {}", characterId, name);
        return characterViewConverter.convertDomain(communityFacade.getCharactersCanBeBlocked(name, characterId, userId));
    }


    @GetMapping(GET_CHARACTERS_CAN_BE_FRIEND_MAPPING)
    public List<CharacterView> getCharactersCanBeFriend(@PathVariable("characterId") String characterId, @PathVariable("charName") String name, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId) {
        log.info("{} querying possible community characters by name like {}", characterId, name);
        return characterViewConverter.convertDomain(communityFacade.getCharactersCanBeFriend(name, characterId, userId));
    }

    @GetMapping(GET_FRIENDS_MAPPING)
    public List<FriendView> getFriends(@PathVariable("characterId") String characterId, @CookieValue(AuthFilter.COOKIE_USER_ID) String userId) {
        log.info("{} wants to know his community list.", characterId);
        return friendViewConverter.convertDomain(communityFacade.getFriends(characterId, userId));
    }

    @GetMapping(GET_RECEIVED_FRIEND_REQUESTS_MAPPING)
    public List<FriendRequestView> getReceivedFriendRequests(
        @PathVariable("characterId") String characterId,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to know his received friendRequests", characterId);
        return friendRequestViewConverter.convertDomain(communityFacade.getReceivedFriendRequests(characterId, userId));
    }

    @GetMapping(GET_SENT_FRIEND_REQUESTS_MAPPING)
    public List<FriendRequestView> getSentFriendRequests(
        @PathVariable("characterId") String characterId,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to know his sent friendRequests", characterId);
        return friendRequestViewConverter.convertDomain(communityFacade.getSentFriendRequests(characterId, userId));
    }
}
