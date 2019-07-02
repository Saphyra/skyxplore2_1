package com.github.saphyra.skyxplore.lobby.invitation;

import org.springframework.stereotype.Service;

import com.github.saphyra.exceptionhandling.exception.ConflictException;
import com.github.saphyra.exceptionhandling.exception.TooManyRequestsException;
import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.lobby.invitation.domain.Invitation;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
class SendInvitationService {
    private final DateTimeUtil dateTimeUtil;
    private final InvitationFactory invitationFactory;
    private final InvitationProperties invitationProperties;
    private final InvitationStorage invitationStorage;
    private final InvitationQueryService invitationQueryService;
    private final LobbyQueryService lobbyQueryService;

    void sendInvitation(String characterId, String invitedCharacterId) {
        if (isInvitedCharacterInLobby(invitedCharacterId)) {
            throw new ConflictException(invitedCharacterId + " is already in lobby.");
        }
        Lobby lobby = lobbyQueryService.findByCharacterIdValidated(characterId);

        invitationQueryService.findByCharacterIdAndInvitedCharacterIdAndLobbyId(characterId, invitedCharacterId, lobby.getLobbyId())
            .ifPresent(invitation -> {
                if (isSpamming(invitation)) {
                    throw new TooManyRequestsException("Character already invited.");
                }
                invitationStorage.remove(invitation.getInvitationId());
            });

        Invitation invitation = invitationFactory.create(characterId, invitedCharacterId, lobby.getLobbyId());

        log.info("Invitation created: ", invitation);
        invitationStorage.put(invitation.getInvitationId(), invitation);
    }

    private boolean isInvitedCharacterInLobby(String invitedCharacterId) {
        return lobbyQueryService.findByCharacterId(invitedCharacterId).isPresent();
    }

    private boolean isSpamming(Invitation invitation) {
        return invitation.getCreatedAt().isAfter(dateTimeUtil.now().minusSeconds(invitationProperties.getSpammingDelaySeconds()));
    }
}
