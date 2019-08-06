package com.github.saphyra.skyxplore.game.lobby.invitation;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.game.lobby.invitation.domain.Invitation;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InvitationFactory {
    private final DateTimeUtil dateTimeUtil;
    private final IdGenerator idGenerator;

    Invitation create(String characterId, String invitedCharacterId, UUID lobbyId) {
        return Invitation.builder()
            .invitationId(idGenerator.randomUUID())
            .characterId(characterId)
            .invitedCharacterId(invitedCharacterId)
            .lobbyId(lobbyId)
            .createdAt(dateTimeUtil.now())
            .build();
    }
}
