package com.github.saphyra.skyxplore.lobby.lobby;

import static com.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.skyxplore.common.domain.character.CharacterView;
import com.github.saphyra.skyxplore.lobby.lobby.creation.LobbyCreatorService;
import com.github.saphyra.skyxplore.lobby.lobby.domain.CreateLobbyRequest;
import com.github.saphyra.skyxplore.lobby.lobby.domain.LobbyEventView;
import com.github.saphyra.skyxplore.lobby.lobby.domain.LobbyView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@Slf4j
//TODO unit test (implementation in progress)
class LobbyController {
    private static final String CREATE_LOBBY_MAPPING = "lobby";
    private static final String EXIT_FROM_LOBBY_MAPPING = "lobby";
    private static final String GET_LOBBY_MAPPING = "lobby";
    private static final String GET_LOBBY_EVENTS_MAPPING = "lobby/event";
    private static final String GET_LOBBY_MEMBERS_MAPPING = "lobby/member";
    private static final String KICK_MEMBER_MAPPING = "lobby/member/{memberId}";
    private static final String TRANSFER_OWNERSHIP_MAPPING = "lobby/owner/{newOwnerId}";


    private final LobbyCreatorService lobbyCreatorService;
    private final LobbyMemberHandler lobbyMemberHandler;
    private final LobbyViewQueryService lobbyViewQueryService;
    private final KickFromLobbyService kickFromLobbyService;
    private final TransferOwnershipService transferOwnershipService;

    @PutMapping(CREATE_LOBBY_MAPPING)
    void createLobby(
        @RequestBody @Valid CreateLobbyRequest request,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to create a lobby with parameters {}", characterId, request);
        lobbyCreatorService.createLobby(request, characterId);
    }

    @DeleteMapping(EXIT_FROM_LOBBY_MAPPING)
    void exitFromLobby(@CookieValue(COOKIE_CHARACTER_ID) String characterId0) {
        lobbyMemberHandler.exitFromLobby(characterId0);
    }

    @GetMapping(GET_LOBBY_MAPPING)
    LobbyView getLobby(@CookieValue(COOKIE_CHARACTER_ID) String characterId) {
        log.info("{} wants to query his lobby.", characterId);
        return lobbyViewQueryService.getLobbyView(characterId);
    }

    @GetMapping(GET_LOBBY_EVENTS_MAPPING)
    List<LobbyEventView> getEvents(@CookieValue(COOKIE_CHARACTER_ID) String characterId) {
        log.info("{} wants to know the new events of his lobby.");
        return lobbyViewQueryService.getEvents(characterId);
    }

    @GetMapping(GET_LOBBY_MEMBERS_MAPPING)
    List<CharacterView> getLobbyMembers(@CookieValue(COOKIE_CHARACTER_ID) String characterId) {
        log.info("{} wants to know members of his lobby", characterId);
        return lobbyMemberHandler.getMembers(characterId);
    }

    @DeleteMapping(KICK_MEMBER_MAPPING)
    void kickMember(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId,
        @PathVariable("memberId") String memberId
    ) {
        log.info("{} wants to kick {} from lobby.", characterId, memberId);
        kickFromLobbyService.kickFromLobby(characterId, memberId);
    }

    @PostMapping(TRANSFER_OWNERSHIP_MAPPING)
    void transferOwnership(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId,
        @PathVariable("newOwnerId") String newOwnerId
    ) {
        log.info("{} wants to transfer lobby ownership to {}", characterId, newOwnerId);
        transferOwnershipService.transferOwnership(characterId, newOwnerId);
    }
}
