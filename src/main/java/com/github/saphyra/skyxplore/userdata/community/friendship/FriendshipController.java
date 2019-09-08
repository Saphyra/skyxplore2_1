package com.github.saphyra.skyxplore.userdata.community.friendship;

import com.github.saphyra.skyxplore.common.OneStringParamRequest;
import com.github.saphyra.skyxplore.common.RequestConstants;
import com.github.saphyra.skyxplore.common.domain.character.CharacterView;
import com.github.saphyra.skyxplore.common.domain.character.CharacterViewConverter;
import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendRequestView;
import com.github.saphyra.skyxplore.userdata.community.friendship.domain.FriendView;
import com.github.saphyra.skyxplore.userdata.community.friendship.service.FriendRequestQueryService;
import com.github.saphyra.skyxplore.userdata.community.friendship.service.FriendshipQueryService;
import com.github.saphyra.skyxplore.userdata.community.friendship.service.FriendshipServiceFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

@RestController
@Slf4j
@RequiredArgsConstructor
class FriendshipController {
    private static final String ACCEPT_FRIEND_REQUEST_MAPPING = API_PREFIX + "/friend/request/accept";
    private static final String ADD_FRIEND_MAPPING = API_PREFIX + "/friend/request";
    private static final String DECLINE_FRIEND_REQUEST_MAPPING = API_PREFIX + "/friend/request"; //Also used for cancelling
    private static final String DELETE_FRIEND_MAPPING = API_PREFIX + "/friend";
    private static final String GET_CHARACTERS_CAN_BE_FRIEND_MAPPING = API_PREFIX + "/friend/name";
    private static final String GET_ACTIVE_FRIENDS_MAPPING = API_PREFIX + "/friend/active";
    private static final String GET_FRIENDS_MAPPING = API_PREFIX + "/friend";
    private static final String GET_RECEIVED_FRIEND_REQUESTS_MAPPING = API_PREFIX + "/friend/request/received";
    private static final String GET_SENT_FRIEND_REQUESTS_MAPPING = API_PREFIX + "/friend/request/sent";

    private final CharacterQueryService characterQueryService;
    private final FriendshipServiceFacade friendshipServiceFacade;
    private final FriendshipQueryService friendshipQueryService;
    private final FriendRequestQueryService friendRequestQueryService;
    private final CharacterViewConverter characterViewConverter;
    private final FriendViewConverter friendViewConverter;
    private final FriendRequestViewConverter friendRequestViewConverter;

    @PostMapping(ACCEPT_FRIEND_REQUEST_MAPPING)
    void acceptFriendRequest(
        @RequestBody @Valid OneStringParamRequest request,
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to accept friendRequest {}", characterId, request.getValue());
        friendshipServiceFacade.acceptFriendRequest(request.getValue(), characterId);
    }

    @PutMapping(ADD_FRIEND_MAPPING)
    void addFriend(
        @Valid @RequestBody OneStringParamRequest request,
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId,
        @CookieValue(RequestConstants.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to add {} as a community.", characterId, request.getValue());
        friendshipServiceFacade.addFriendRequest(request.getValue(), characterId, userId);
    }

    @DeleteMapping(DECLINE_FRIEND_REQUEST_MAPPING)
    void declineFriendRequestMapping(
        @RequestBody @Valid OneStringParamRequest request,
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to decline / cancel friendRequest {}", characterId, request.getValue());
        friendshipServiceFacade.declineFriendRequest(request.getValue(), characterId);
    }

    @DeleteMapping(DELETE_FRIEND_MAPPING)
    void deleteFriend(
        @RequestBody @Valid OneStringParamRequest request,
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to deleteById friendship {}", characterId, request.getValue());
        friendshipServiceFacade.deleteFriendship(request.getValue(), characterId);
    }

    @PostMapping(GET_CHARACTERS_CAN_BE_FRIEND_MAPPING)
    List<CharacterView> getCharactersCanBeFriend(
        @RequestBody @Valid OneStringParamRequest request,
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} querying possible community characters by name like {}", characterId, request.getValue());
        return characterViewConverter.convertDomain(characterQueryService.getCharactersCanBeFriend(request.getValue(), characterId));
    }

    @GetMapping(GET_ACTIVE_FRIENDS_MAPPING)
    List<CharacterView> getActiveFriends(@CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId){
        log.info("{} wants to know his active friends.", characterId);
        return characterViewConverter.convertDomain(friendshipServiceFacade.getActiveFriends(characterId));
    }

    @GetMapping(GET_FRIENDS_MAPPING)
    List<FriendView> getFriends(
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his community list.", characterId);
        return friendViewConverter.convertDomain(friendshipQueryService.getFriends(characterId), characterId);
    }

    @GetMapping(GET_RECEIVED_FRIEND_REQUESTS_MAPPING)
    List<FriendRequestView> getReceivedFriendRequests(
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his received friendRequests", characterId);
        return friendRequestViewConverter.convertDomain(friendRequestQueryService.getReceivedFriendRequests(characterId));
    }

    @GetMapping(GET_SENT_FRIEND_REQUESTS_MAPPING)
    List<FriendRequestView> getSentFriendRequests(
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his sent friendRequests", characterId);
        return friendRequestViewConverter.convertDomain(friendRequestQueryService.getSentFriendRequests(characterId));
    }
}
