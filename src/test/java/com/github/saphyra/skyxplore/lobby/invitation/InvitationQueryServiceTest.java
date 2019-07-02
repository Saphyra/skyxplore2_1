package com.github.saphyra.skyxplore.lobby.invitation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.skyxplore.lobby.invitation.domain.Invitation;

@RunWith(MockitoJUnitRunner.class)
public class InvitationQueryServiceTest {
    private static final String CHARACTER_ID_1 = "character_id_1";
    private static final String CHARACTER_ID_2 = "character_id_2";
    private static final UUID LOBBY_ID_1 = UUID.randomUUID();
    private static final UUID INVITATION_ID = UUID.randomUUID();

    @Mock
    private InvitationStorage invitationStorage;

    @InjectMocks
    private InvitationQueryService underTest;

    @Mock
    private Invitation invitation;

    @Test
    public void findByCharacterIdAndInvitedCharacterIdAndLobbyId_found() {
        //GIVEN
        given(invitationStorage.values()).willReturn(Arrays.asList(invitation));

        given(invitation.getCharacterId()).willReturn(CHARACTER_ID_1);
        given(invitation.getLobbyId()).willReturn(LOBBY_ID_1);
        given(invitation.getInvitedCharacterId()).willReturn(CHARACTER_ID_2);
        //WHEN
        Optional<Invitation> result = underTest.findByCharacterIdAndInvitedCharacterIdAndLobbyId(CHARACTER_ID_1, CHARACTER_ID_2, LOBBY_ID_1);
        //THEN
        assertThat(result).contains(invitation);
    }

    @Test
    public void findByCharacterIdAndInvitedCharacterIdAndLobbyId_notFound() {
        //GIVEN
        given(invitationStorage.values()).willReturn(Arrays.asList(invitation));

        given(invitation.getCharacterId()).willReturn(CHARACTER_ID_2);
        given(invitation.getLobbyId()).willReturn(LOBBY_ID_1);
        //WHEN
        Optional<Invitation> result = underTest.findByCharacterIdAndInvitedCharacterIdAndLobbyId(CHARACTER_ID_1, CHARACTER_ID_2, LOBBY_ID_1);
        //THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void findByIdAndInvitedCharacterIdValidated_found() {
        //GIVEN
        given(invitationStorage.get(INVITATION_ID)).willReturn(invitation);

        given(invitation.getInvitedCharacterId()).willReturn(CHARACTER_ID_1);
        //WHEN
        Invitation result = underTest.findByIdAndInvitedCharacterIdValidated(INVITATION_ID, CHARACTER_ID_1);
        //THEN
        assertThat(result).isEqualTo(invitation);
    }

    @Test
    public void findByIdAndInvitedCharacterIdValidated_notFound() {
        //GIVEN
        given(invitationStorage.get(INVITATION_ID)).willReturn(invitation);

        given(invitation.getInvitedCharacterId()).willReturn(CHARACTER_ID_2);
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.findByIdAndInvitedCharacterIdValidated(INVITATION_ID, CHARACTER_ID_1));
        //THEN
        assertThat(ex).isInstanceOf(NotFoundException.class);
    }
}