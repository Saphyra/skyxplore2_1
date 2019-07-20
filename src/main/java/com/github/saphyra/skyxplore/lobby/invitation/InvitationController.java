package com.github.saphyra.skyxplore.lobby.invitation;

import com.github.saphyra.skyxplore.lobby.invitation.domain.InvitationView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;
import static com.github.saphyra.skyxplore.common.RequestConstants.COOKIE_CHARACTER_ID;

@RestController
@Slf4j
@RequiredArgsConstructor
public class InvitationController {
    private static final String ACCEPT_INVITATION_MAPPING = API_PREFIX + "/lobby/invitation/{invitationId}";
    public static final String GET_INVITATIONS_MAPPING = API_PREFIX + "/lobby/invitation";
    private static final String INVITE_MAPPING = API_PREFIX + "/lobby/invitation/{invitedId}";

    private final AcceptInvitationService acceptInvitationService;
    private final InvitationViewQueryService invitationViewQueryService;
    private final SendInvitationService sendInvitationService;

    @PostMapping(ACCEPT_INVITATION_MAPPING)
    void acceptInvitation(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId,
        @PathVariable("invitationId") UUID invitationId
    ) {
        log.info("{} wants to accept invitation {}", characterId, invitationId);
        acceptInvitationService.acceptInvitation(characterId, invitationId);
    }

    @GetMapping(GET_INVITATIONS_MAPPING)
    List<InvitationView> getInvitations(@CookieValue(COOKIE_CHARACTER_ID) String characterId) {
        log.info("{} wants to know his invitations", characterId);
        return invitationViewQueryService.getInvitations(characterId);
    }

    @PutMapping(INVITE_MAPPING)
    void inviteToLobby(
        @CookieValue(COOKIE_CHARACTER_ID) String characterId,
        @PathVariable("invitedId") String invitedCharacterId
    ) {
        log.info("{} want to invite {} to lobby.", characterId, invitedCharacterId);
        sendInvitationService.sendInvitation(characterId, invitedCharacterId);
    }
}
