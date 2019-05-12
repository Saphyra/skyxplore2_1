package com.github.saphyra.skyxplore.lobby.invitation;

import com.github.saphyra.skyxplore.lobby.invitation.domain.Invitation;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class AcceptInvitationService {
    private final InvitationQueryService invitationQueryService;
    private final LobbyQueryService lobbyQueryService;

    void acceptInvitation(String characterId, UUID invitationId) {
        Invitation invitation = invitationQueryService.findByIdValidated(invitationId, characterId);
        Lobby lobby = lobbyQueryService.findById(invitation.getLobbyId());
        lobby.addMember(characterId);
    }
}
