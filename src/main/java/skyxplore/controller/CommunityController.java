package skyxplore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.github.saphyra.skyxplore.common.OneStringParamRequest;
import skyxplore.controller.view.character.CharacterView;
import skyxplore.controller.view.character.CharacterViewConverter;
import skyxplore.controller.view.community.friend.FriendView;
import skyxplore.controller.view.community.friend.FriendViewConverter;
import skyxplore.controller.view.community.friendrequest.FriendRequestView;
import skyxplore.controller.view.community.friendrequest.FriendRequestViewConverter;
import skyxplore.service.CommunityFacade;

import javax.validation.Valid;
import java.util.List;

import static org.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;
import static org.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_USER_ID;

@RestController
@Slf4j
@RequiredArgsConstructor
class CommunityController {
    private static final String ACCEPT_FRIEND_REQUEST_MAPPING = "friend/request/accept";
    private static final String ADD_FRIEND_MAPPING = "friend/request";
    private static final String ALLOW_BLOCKED_CHARACTER_MAPPING = "blockedcharacter";
    private static final String BLOCK_CHARACTER_MAPPING = "blockcharacter";
    private static final String DECLINE_FRIEND_REQUEST_MAPPING = "friend/request"; //Also used for cancelling
    private static final String DELETE_FRIEND_MAPPING = "friend";
    private static final String GET_BLOCKED_CHARACTERS_MAPPING = "blockedcharacter";
    private static final String GET_CHARACTERS_CAN_BE_BLOCKED_MAPPING = "blockcharacter/name";
    private static final String GET_CHARACTERS_CAN_BE_FRIEND_MAPPING = "friend/name";
    private static final String GET_FRIENDS_MAPPING = "friend";
    private static final String GET_RECEIVED_FRIEND_REQUESTS_MAPPING = "friend/request/received";
    private static final String GET_SENT_FRIEND_REQUESTS_MAPPING = "friend/request/sent";

    private final CommunityFacade communityFacade;
    private final CharacterViewConverter characterViewConverter;
    private final FriendRequestViewConverter friendRequestViewConverter;
    private final FriendViewConverter friendViewConverter;

    @PostMapping(ACCEPT_FRIEND_REQUEST_MAPPING)
    void acceptFriendRequest(
        @RequestBody @Valid OneStringParamRequest request,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to accept friendRequest {}", characterId, request.getValue());
        communityFacade.acceptFriendRequest(request.getValue(), characterId);
    }

    @PutMapping(ADD_FRIEND_MAPPING)
    void addFriend(
        @Valid @RequestBody OneStringParamRequest request,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId,
        @CookieValue(COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to add {} as a community.", characterId, request.getValue());
        communityFacade.addFriendRequest(request.getValue(), characterId, userId);
    }

    @DeleteMapping(ALLOW_BLOCKED_CHARACTER_MAPPING)
    void allowBlockedCharacter(
        @RequestBody @Valid OneStringParamRequest request,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to allow blockedCharacter {}", characterId, request.getValue());
        communityFacade.allowBlockedCharacter(request.getValue(), characterId);
    }

    @PostMapping(BLOCK_CHARACTER_MAPPING)
    void blockCharacter(
        @Valid @RequestBody OneStringParamRequest request,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to block {}", characterId, request.getValue());
        communityFacade.blockCharacter(request.getValue(), characterId);
    }

    @DeleteMapping(DECLINE_FRIEND_REQUEST_MAPPING)
    void declineFriendRequestMapping(
        @RequestBody @Valid OneStringParamRequest request,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to decline / cancel friendRequest {}", characterId, request.getValue());
        communityFacade.declineFriendRequest(request.getValue(), characterId);
    }

    @DeleteMapping(DELETE_FRIEND_MAPPING)
    void deleteFriend(
        @RequestBody @Valid OneStringParamRequest request,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to deleteById friendship {}", characterId, request.getValue());
        communityFacade.deleteFriendship(request.getValue(), characterId);
    }

    @GetMapping(GET_BLOCKED_CHARACTERS_MAPPING)
    List<CharacterView> getBlockedCharacters(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his blocked characters list.", characterId);
        return characterViewConverter.convertDomain(communityFacade.getBlockedCharacters(characterId));
    }

    @PostMapping(GET_CHARACTERS_CAN_BE_BLOCKED_MAPPING)
    List<CharacterView> getCharactersCanBeBlocked(
        @RequestBody @Valid OneStringParamRequest request,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} querying blockable characters by name like {}", characterId, request.getValue());
        return characterViewConverter.convertDomain(communityFacade.getCharactersCanBeBlocked(request.getValue(), characterId));
    }

    @PostMapping(GET_CHARACTERS_CAN_BE_FRIEND_MAPPING)
    List<CharacterView> getCharactersCanBeFriend(
        @RequestBody @Valid OneStringParamRequest request,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} querying possible community characters by name like {}", characterId, request.getValue());
        return characterViewConverter.convertDomain(communityFacade.getCharactersCanBeFriend(request.getValue(), characterId));
    }

    @GetMapping(GET_FRIENDS_MAPPING)
    List<FriendView> getFriends(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his community list.", characterId);
        return friendViewConverter.convertDomain(communityFacade.getFriends(characterId), characterId);
    }

    @GetMapping(GET_RECEIVED_FRIEND_REQUESTS_MAPPING)
    List<FriendRequestView> getReceivedFriendRequests(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his received friendRequests", characterId);
        return friendRequestViewConverter.convertDomain(communityFacade.getReceivedFriendRequests(characterId));
    }

    @GetMapping(GET_SENT_FRIEND_REQUESTS_MAPPING)
    List<FriendRequestView> getSentFriendRequests(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his sent friendRequests", characterId);
        return friendRequestViewConverter.convertDomain(communityFacade.getSentFriendRequests(characterId));
    }
}
