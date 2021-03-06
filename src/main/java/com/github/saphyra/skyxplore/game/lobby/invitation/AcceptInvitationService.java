package com.github.saphyra.skyxplore.game.lobby.invitation;

import com.github.saphyra.exceptionhandling.exception.ConflictException;
import com.github.saphyra.skyxplore.game.lobby.invitation.domain.Invitation;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
class AcceptInvitationService {
    private final InvitationQueryService invitationQueryService;
    private final LobbyQueryService lobbyQueryService;

    void acceptInvitation(String characterId, UUID invitationId) {
        if (lobbyQueryService.findByCharacterId(characterId).isPresent()) {
            throw new ConflictException(characterId + " is already in lobby.");
        }
        Invitation invitation = invitationQueryService.findByIdAndInvitedCharacterIdValidated(invitationId, characterId);
        Lobby lobby = lobbyQueryService.findByLobbyIdValidated(invitation.getLobbyId());
        lobby.addMember(characterId);
    }
}
