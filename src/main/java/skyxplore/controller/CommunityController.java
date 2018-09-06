package skyxplore.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.OneStringParamRequest;
import skyxplore.controller.view.character.CharacterView;
import skyxplore.controller.view.character.CharacterViewConverter;
import skyxplore.controller.view.community.friend.FriendView;
import skyxplore.controller.view.community.friend.FriendViewConverter;
import skyxplore.controller.view.community.friendrequest.FriendRequestView;
import skyxplore.controller.view.community.friendrequest.FriendRequestViewConverter;
import skyxplore.filter.AuthFilter;
import skyxplore.filter.CharacterAuthFilter;
import skyxplore.service.CommunityFacade;

@SuppressWarnings("WeakerAccess")
@RestController
@Slf4j
@RequiredArgsConstructor
public class CommunityController {
    private static final String ACCEPT_FRIEND_REQUEST_MAPPING = "friend/request/accept";
    private static final String ADD_FRIEND_MAPPING = "friend/request/add";
    private static final String ALLOW_BLOCKED_CHARACTER_MAPPING = "blockedcharacter/allow";
    private static final String BLOCK_CHARACTER_MAPPING = "blockcharacter/block";
    private static final String DECLINE_FRIEND_REQUEST_MAPPING = "friend/request/decline";
    private static final String DELETE_FRIEND_MAPPING = "friend";
    private static final String GET_BLOCKED_CHARACTERS_MAPPING = "blockedcharacter";
    private static final String GET_CHARACTERS_CAN_BE_BLOCKED_MAPPING = "blockcharacter/namelike";
    private static final String GET_CHARACTERS_CAN_BE_FRIEND_MAPPING = "friend/namelike";
    private static final String GET_FRIENDS_MAPPING = "friend";
    private static final String GET_NUMBER_OF_FRIEND_REQUESTS_MAPPING = "friend/request/num";
    private static final String GET_RECEIVED_FRIEND_REQUESTS_MAPPING = "friend//request/received";
    private static final String GET_SENT_FRIEND_REQUESTS_MAPPING = "friend/request/sent";

    private final CommunityFacade communityFacade;
    private final CharacterViewConverter characterViewConverter;
    private final FriendRequestViewConverter friendRequestViewConverter;
    private final FriendViewConverter friendViewConverter;

    @PostMapping(ACCEPT_FRIEND_REQUEST_MAPPING)
    public void acceptFriendRequest(
        @RequestBody @Valid OneStringParamRequest request,
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to accept friendRequest {}", characterId, request.getValue());
        communityFacade.acceptFriendRequest(request.getValue(), characterId);
    }

    @PostMapping(ADD_FRIEND_MAPPING)
    public void addFriend(
        @Valid @RequestBody OneStringParamRequest request,
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to add {} as a community.", characterId, request.getValue());
        communityFacade.addFriendRequest(request.getValue(), characterId);
    }

    @PostMapping(ALLOW_BLOCKED_CHARACTER_MAPPING)
    public void allowBlockedCharacter(
        @RequestBody @Valid OneStringParamRequest request,
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to allow blockedCharacter {}", characterId, request.getValue());
        communityFacade.allowBlockedCharacter(request.getValue(), characterId);
    }

    @PostMapping(BLOCK_CHARACTER_MAPPING)
    public void blockCharacter(
        @Valid @RequestBody OneStringParamRequest request,
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to block {}", characterId, request.getValue());
        communityFacade.blockCharacter(request.getValue(), characterId);
    }

    @PostMapping(DECLINE_FRIEND_REQUEST_MAPPING)
    public void declineFriendRequestMapping(
        @RequestBody @Valid OneStringParamRequest request,
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to decline / cancel friendRequest {}", characterId, request.getValue());
        communityFacade.declineFriendRequest(request.getValue(), characterId);
    }

    @DeleteMapping(DELETE_FRIEND_MAPPING)
    public void deleteFriend(
        @RequestBody @Valid OneStringParamRequest request,
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to delete friendship {}", characterId, request.getValue());
        communityFacade.deleteFriendship(request.getValue(), characterId);
    }

    @GetMapping(GET_BLOCKED_CHARACTERS_MAPPING)
    public List<CharacterView> getBlockedCharacters(
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his blocked characters list.", characterId);
        return characterViewConverter.convertDomain(communityFacade.getBlockedCharacters(characterId));
    }

    @GetMapping(GET_CHARACTERS_CAN_BE_BLOCKED_MAPPING)
    public List<CharacterView> getCharactersCanBeBlocked(
        @RequestBody @Valid OneStringParamRequest request,
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ) {
        log.info("{} querying blockable characters by name like {}", characterId, request.getValue());
        return characterViewConverter.convertDomain(communityFacade.getCharactersCanBeBlocked(request.getValue(), characterId, userId));
    }

    @GetMapping(GET_CHARACTERS_CAN_BE_FRIEND_MAPPING)
    public List<CharacterView> getCharactersCanBeFriend(
        @RequestBody @Valid OneStringParamRequest request,
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId) {
        log.info("{} querying possible community characters by name like {}", characterId, request.getValue());
        return characterViewConverter.convertDomain(communityFacade.getCharactersCanBeFriend(request.getValue(), characterId, userId));
    }

    @GetMapping(GET_FRIENDS_MAPPING)
    public List<FriendView> getFriends(
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his community list.", characterId);
        return friendViewConverter.convertDomain(communityFacade.getFriends(characterId), characterId);
    }

    @GetMapping(GET_NUMBER_OF_FRIEND_REQUESTS_MAPPING)
    public Integer getNumberOfFriendRequests(
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know the number of his friend requests.");
        return communityFacade.getNumberOfFriendRequests(characterId);
    }

    @GetMapping(GET_RECEIVED_FRIEND_REQUESTS_MAPPING)
    public List<FriendRequestView> getReceivedFriendRequests(
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his received friendRequests", characterId);
        return friendRequestViewConverter.convertDomain(communityFacade.getReceivedFriendRequests(characterId));
    }

    @GetMapping(GET_SENT_FRIEND_REQUESTS_MAPPING)
    public List<FriendRequestView> getSentFriendRequests(
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his sent friendRequests", characterId);
        return friendRequestViewConverter.convertDomain(communityFacade.getSentFriendRequests(characterId));
    }
}
