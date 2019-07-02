package com.github.saphyra.skyxplore.lobby.invitation;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.lobby.invitation.domain.Invitation;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
class AcceptInvitationService {
    private final InvitationQueryService invitationQueryService;
    private final LobbyQueryService lobbyQueryService;

    void acceptInvitation(String characterId, UUID invitationId) {
        Invitation invitation = invitationQueryService.findByIdAndInvitedCharacterIdValidated(invitationId, characterId);
        Lobby lobby = lobbyQueryService.findById(invitation.getLobbyId());
        lobby.addMember(characterId);
    }
}
