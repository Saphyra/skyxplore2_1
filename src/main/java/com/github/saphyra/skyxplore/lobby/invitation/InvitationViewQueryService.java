package com.github.saphyra.skyxplore.lobby.invitation;

import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.lobby.invitation.domain.Invitation;
import com.github.saphyra.skyxplore.lobby.invitation.domain.InvitationView;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class InvitationViewQueryService {
    private final CharacterQueryService characterQueryService;
    private final InvitationStorage invitationStorage;
    private final LobbyQueryService lobbyQueryService;

    List<InvitationView> getInvitations(String characterId) {
        return invitationStorage.values().stream()
            .filter(invitation -> invitation.getInvitedCharacterId().equals(characterId))
            .filter(invitation -> !invitation.isQueried())
            .peek(invitation -> invitation.setQueried(true))
            .map(this::convertToView)
            .collect(Collectors.toList());
    }

    private InvitationView convertToView(Invitation invitation) {
        Lobby lobby = lobbyQueryService.findById(invitation.getLobbyId());
        return InvitationView.builder()
            .gameMode(lobby.getGameMode())
            .data(lobby.getData())
            .characterName(characterQueryService.findByCharacterId(invitation.getCharacterId()).getCharacterName())
            .characterId(invitation.getCharacterId())
            .lobbyId(lobby.getLobbyId())
            .build();
    }
}
