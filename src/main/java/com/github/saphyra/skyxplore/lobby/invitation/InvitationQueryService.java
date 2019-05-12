package com.github.saphyra.skyxplore.lobby.invitation;

import com.github.saphyra.skyxplore.lobby.invitation.domain.Invitation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class InvitationQueryService {
    private final InvitationStorage invitationStorage;

    public Optional<Invitation> findByCharacterIdAndInvitedCharacterIdAndLobbyId(String characterId, String invitedCharacterId, UUID lobbyId) {
        return invitationStorage.values().stream()
            .filter(invitation -> invitation.getLobbyId().equals(lobbyId)
                && invitation.getCharacterId().equals(characterId)
                && invitation.getInvitedCharacterId().equals(invitedCharacterId)
            )
            .findFirst();
    }
}
