package com.github.saphyra.skyxplore.game.lobby.invitation;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.skyxplore.game.lobby.invitation.domain.Invitation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
class InvitationQueryService {
    private final InvitationStorage invitationStorage;

    Optional<Invitation> findByCharacterIdAndInvitedCharacterIdAndLobbyId(String characterId, String invitedCharacterId, UUID lobbyId) {
        return invitationStorage.values().stream()
            .filter(invitation -> invitation.getLobbyId().equals(lobbyId))
            .filter(invitation -> invitation.getCharacterId().equals(characterId))
            .filter(invitation -> invitation.getInvitedCharacterId().equals(invitedCharacterId))
            .findFirst();
    }

    Invitation findByIdAndInvitedCharacterIdValidated(UUID invitationId, String characterId) {
        return Optional.ofNullable(invitationStorage.get(invitationId))
            .filter(invitation -> invitation.getInvitedCharacterId().equals(characterId))
            .orElseThrow(() -> new NotFoundException("Invitation not found with id " + invitationId + " for character " + characterId));
    }
}
