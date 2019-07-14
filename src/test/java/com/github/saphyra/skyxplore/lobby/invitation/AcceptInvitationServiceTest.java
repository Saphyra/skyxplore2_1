package com.github.saphyra.skyxplore.lobby.invitation;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.ConflictException;
import com.github.saphyra.skyxplore.lobby.invitation.domain.Invitation;
import com.github.saphyra.skyxplore.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.lobby.lobby.domain.Lobby;

@RunWith(MockitoJUnitRunner.class)
public class AcceptInvitationServiceTest {
    private static final UUID INVITATION_ID = UUID.randomUUID();
    private static final String CHARACTER_ID = "character_id";
    private static final UUID LOBBY_ID = UUID.randomUUID();

    @Mock
    private InvitationQueryService invitationQueryService;

    @Mock
    private LobbyQueryService lobbyQueryService;

    @InjectMocks
    private AcceptInvitationService underTest;

    @Mock
    private Invitation invitation;

    @Mock
    private Lobby lobby;

    @Test(expected = ConflictException.class)
    public void acceptInvitation_alreadyInLobby() {
        //GIVEN
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.of(lobby));
        //WHEN
        underTest.acceptInvitation(CHARACTER_ID, INVITATION_ID);
    }

    @Test
    public void acceptInvitation() {
        //GIVEN
        given(lobbyQueryService.findByCharacterId(CHARACTER_ID)).willReturn(Optional.empty());

        given(invitationQueryService.findByIdAndInvitedCharacterIdValidated(INVITATION_ID, CHARACTER_ID)).willReturn(invitation);
        given(invitation.getLobbyId()).willReturn(LOBBY_ID);
        given(lobbyQueryService.findByLobbyIdValidated(LOBBY_ID)).willReturn(lobby);
        //WHEN
        underTest.acceptInvitation(CHARACTER_ID, INVITATION_ID);
        //THEN
        verify(lobby).addMember(CHARACTER_ID);
    }
}