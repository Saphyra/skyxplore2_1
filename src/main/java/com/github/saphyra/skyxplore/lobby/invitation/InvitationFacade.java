package com.github.saphyra.skyxplore.lobby.invitation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
//TODO unit test
public class InvitationFacade {
    private final SendInvitationService sendInvitationService;

    public void sendInvitation(String characterId, String invitedCharacterId) {
        sendInvitationService.sendInvitation(characterId, invitedCharacterId);
    }
}
