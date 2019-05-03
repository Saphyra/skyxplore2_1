package org.github.saphyra.skyxplore.community.friendship;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.common.OneStringParamRequest;
import org.github.saphyra.skyxplore.common.domain.character.CharacterView;
import org.github.saphyra.skyxplore.common.domain.character.CharacterViewConverter;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.github.saphyra.skyxplore.community.friendship.domain.FriendView;
import org.github.saphyra.skyxplore.community.friendship.domain.FriendRequestView;

import javax.validation.Valid;

import java.util.List;

import static org.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;
import static org.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_USER_ID;

@RestController
@Slf4j
@RequiredArgsConstructor
class FriendshipController {
    private static final String ACCEPT_FRIEND_REQUEST_MAPPING = "friend/request/accept";
    private static final String ADD_FRIEND_MAPPING = "friend/request";
    private static final String DECLINE_FRIEND_REQUEST_MAPPING = "friend/request"; //Also used for cancelling
    private static final String DELETE_FRIEND_MAPPING = "friend";
    private static final String GET_CHARACTERS_CAN_BE_FRIEND_MAPPING = "friend/name";
    private static final String GET_FRIENDS_MAPPING = "friend";
    private static final String GET_RECEIVED_FRIEND_REQUESTS_MAPPING = "friend/request/received";
    private static final String GET_SENT_FRIEND_REQUESTS_MAPPING = "friend/request/sent";

    private final CharacterQueryService characterQueryService;
    private final FriendshipService friendshipService;
    private final FriendshipQueryService friendshipQueryService;
    private final CharacterViewConverter characterViewConverter;
    private final FriendViewConverter friendViewConverter;
    private final FriendRequestViewConverter friendRequestViewConverter;

    @PostMapping(ACCEPT_FRIEND_REQUEST_MAPPING)
    void acceptFriendRequest(
        @RequestBody @Valid OneStringParamRequest request,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to accept friendRequest {}", characterId, request.getValue());
        friendshipService.acceptFriendRequest(request.getValue(), characterId);
    }

    @PutMapping(ADD_FRIEND_MAPPING)
    void addFriend(
        @Valid @RequestBody OneStringParamRequest request,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId,
        @CookieValue(COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to add {} as a community.", characterId, request.getValue());
        friendshipService.addFriendRequest(request.getValue(), characterId, userId);
    }

    @DeleteMapping(DECLINE_FRIEND_REQUEST_MAPPING)
    void declineFriendRequestMapping(
        @RequestBody @Valid OneStringParamRequest request,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to decline / cancel friendRequest {}", characterId, request.getValue());
        friendshipService.declineFriendRequest(request.getValue(), characterId);
    }

    @DeleteMapping(DELETE_FRIEND_MAPPING)
    void deleteFriend(
        @RequestBody @Valid OneStringParamRequest request,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to deleteById friendship {}", characterId, request.getValue());
        friendshipService.deleteFriendship(request.getValue(), characterId);
    }

    @PostMapping(GET_CHARACTERS_CAN_BE_FRIEND_MAPPING)
    List<CharacterView> getCharactersCanBeFriend(
        @RequestBody @Valid OneStringParamRequest request,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} querying possible community characters by name like {}", characterId, request.getValue());
        return characterViewConverter.convertDomain(characterQueryService.getCharactersCanBeFriend(request.getValue(), characterId));
    }

    @GetMapping(GET_FRIENDS_MAPPING)
    List<FriendView> getFriends(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his community list.", characterId);
        return friendViewConverter.convertDomain(friendshipQueryService.getFriends(characterId), characterId);
    }

    @GetMapping(GET_RECEIVED_FRIEND_REQUESTS_MAPPING)
    List<FriendRequestView> getReceivedFriendRequests(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his received friendRequests", characterId);
        return friendRequestViewConverter.convertDomain(friendshipQueryService.getReceivedFriendRequests(characterId));
    }

    @GetMapping(GET_SENT_FRIEND_REQUESTS_MAPPING)
    List<FriendRequestView> getSentFriendRequests(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his sent friendRequests", characterId);
        return friendRequestViewConverter.convertDomain(friendshipQueryService.getSentFriendRequests(characterId));
    }
}
