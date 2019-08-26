package com.github.saphyra.skyxplore.game.lobby.invitation;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.common.AbstractViewConverter;
import com.github.saphyra.skyxplore.game.lobby.invitation.domain.Invitation;
import com.github.saphyra.skyxplore.game.lobby.invitation.domain.InvitationView;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InvitationViewConverter extends AbstractViewConverter<Invitation, InvitationView> {
    private final CharacterQueryService characterQueryService;
    private final LobbyQueryService lobbyQueryService;

    @Override
    public InvitationView convertDomain(Invitation invitation) {
        Lobby lobby = lobbyQueryService.findByLobbyIdValidated(invitation.getLobbyId());
        return InvitationView.builder()
            .gameMode(lobby.getGameMode())
            .data(lobby.getData())
            .characterName(characterQueryService.findByCharacterIdValidated(invitation.getCharacterId()).getCharacterName())
            .characterId(invitation.getCharacterId())
            .invitationId(invitation.getInvitationId())
            .build();
    }
}
