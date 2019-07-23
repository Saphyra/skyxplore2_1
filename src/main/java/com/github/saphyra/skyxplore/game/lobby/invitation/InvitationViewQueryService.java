package com.github.saphyra.skyxplore.game.lobby.invitation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.game.lobby.invitation.domain.InvitationView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
class InvitationViewQueryService {
    private final InvitationStorage invitationStorage;
    private final InvitationViewConverter invitationViewConverter;

    List<InvitationView> getInvitations(String characterId) {
        return invitationStorage.values().stream()
            .filter(invitation -> invitation.getInvitedCharacterId().equals(characterId))
            .filter(invitation -> !invitation.isQueried())
            .peek(invitation -> invitation.setQueried(true))
            .map(invitationViewConverter::convertDomain)
            .collect(Collectors.toList());
    }
}
