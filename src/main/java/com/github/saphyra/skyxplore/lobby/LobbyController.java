package com.github.saphyra.skyxplore.lobby;

import com.github.saphyra.skyxplore.lobby.creation.LobbyCreatorService;
import com.github.saphyra.skyxplore.lobby.domain.CreateLobbyRequest;
import com.github.saphyra.skyxplore.lobby.domain.LobbyView;
import com.github.saphyra.skyxplore.lobby.invitation.InvitationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.github.saphyra.skyxplore.filter.CustomFilterHelper.COOKIE_CHARACTER_ID;

@RequiredArgsConstructor
@RestController
@Slf4j
//TODO unit test (implementation in progress)
class LobbyController {
    private static final String CREATE_LOBBY_MAPPING = "lobby";
    private static final String GET_LOBBY_MAPPING = "lobby";
    private static final String INVITE_MAPPING = "lobby/invitation/{invitedId}";
    private static final String EXIT_FROM_LOBBY_MAPPING = "lobby";

    private final InvitationFacade invitationFacade;
    private final LobbyCreatorService lobbyCreatorService;
    private final LobbyMemberHandler lobbyMemberHandler;
    private final LobbyViewQueryService lobbyViewQueryService;

    @PutMapping(CREATE_LOBBY_MAPPING)
    void createLobby(
        @RequestBody @Valid CreateLobbyRequest request,
        @CookieValue(COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to create a lobby with parameters {}", characterId, request);
        lobbyCreatorService.createLobby(request, characterId);
    }

    @PutMapping(INVITE_MAPPING)
    void inviteToLobby(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId,
        @PathVariable("invitedId") String invitedCharacterId
    ){
        log.info("{} want to invite {} to lobby.", characterId, invitedCharacterId);
        invitationFacade.sendInvitation(characterId, invitedCharacterId);
    }

    @GetMapping(GET_LOBBY_MAPPING)
    LobbyView getLobby(@CookieValue(COOKIE_CHARACTER_ID) String characterId) {
        log.info("{} wants to query his lobby.", characterId);
        return lobbyViewQueryService.getLobbyView(characterId);
    }

    @DeleteMapping(EXIT_FROM_LOBBY_MAPPING)
    void exitFromLobby(@CookieValue(COOKIE_CHARACTER_ID) String characterId0) {
        lobbyMemberHandler.exitFromLobby(characterId0);
    }
}
