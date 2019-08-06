package com.github.saphyra.skyxplore.game.lobby.invitation;

import com.github.saphyra.skyxplore.game.lobby.invitation.domain.InvitationView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class InvitationControllerTest {
    private static final String CHARACTER_ID = "character_id";
    private static final UUID INVITATION_ID = UUID.randomUUID();
    private static final String INVITED_CHARACTER_ID = "invited_character_id";

    @Mock
    private AcceptInvitationService acceptInvitationService;

    @Mock
    private InvitationViewQueryService invitationViewQueryService;

    @Mock
    private SendInvitationService sendInvitationService;

    @InjectMocks
    private InvitationController underTest;

    @Mock
    private InvitationView invitationView;

    @Test
    public void acceptInvitation() {
        //WHEN
        underTest.acceptInvitation(CHARACTER_ID, INVITATION_ID);
        //THEN
        verify(acceptInvitationService).acceptInvitation(CHARACTER_ID, INVITATION_ID);
    }

    @Test
    public void getInvitations() {
        //GIVEN
        given(invitationViewQueryService.getInvitations(CHARACTER_ID)).willReturn(Arrays.asList(invitationView));
        //WHEN
        List<InvitationView> result = underTest.getInvitations(CHARACTER_ID);
        //THEN
        assertThat(result).containsExactly(invitationView);
    }

    @Test
    public void inviteToLobby() {
        //WHEN
        underTest.inviteToLobby(CHARACTER_ID, INVITED_CHARACTER_ID);
        //THEN
        verify(sendInvitationService).sendInvitation(CHARACTER_ID, INVITED_CHARACTER_ID);
    }
}