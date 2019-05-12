package com.github.saphyra.skyxplore.lobby.invitation;

import com.github.saphyra.skyxplore.lobby.invitation.domain.InvitationView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
//TODO unit test
public class InvitationFacade {
    private final InvitationViewQueryService invitationViewQueryService;
    private final SendInvitationService sendInvitationService;

    public List<InvitationView> getInvitations(String characterId) {
        return invitationViewQueryService.getInvitations(characterId);
    }

    public void sendInvitation(String characterId, String invitedCharacterId) {
        sendInvitationService.sendInvitation(characterId, invitedCharacterId);
    }
}
