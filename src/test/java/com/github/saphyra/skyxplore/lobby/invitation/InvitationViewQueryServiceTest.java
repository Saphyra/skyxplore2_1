package com.github.saphyra.skyxplore.lobby.invitation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.lobby.invitation.domain.Invitation;
import com.github.saphyra.skyxplore.lobby.invitation.domain.InvitationView;

@RunWith(MockitoJUnitRunner.class)
public class InvitationViewQueryServiceTest {
    private static final String FAKE_CHARACTER_ID = "fake_character_id";
    private static final String CHARACTER_ID = "character_id";

    @Mock
    private InvitationStorage invitationStorage;

    @Mock
    private InvitationViewConverter invitationViewConverter;

    @InjectMocks
    private InvitationViewQueryService underTest;

    @Mock
    private Invitation invitationWithDifferentCharacterId;

    @Mock
    private Invitation queriedInvitation;

    @Mock
    private Invitation queryableInvitation;

    @Mock
    private InvitationView invitationView;

    @Test
    public void getInvitations() {
        //GIVEN
        given(invitationStorage.values()).willReturn(Arrays.asList(
            invitationWithDifferentCharacterId,
            queriedInvitation,
            queryableInvitation
        ));

        given(invitationWithDifferentCharacterId.getInvitedCharacterId()).willReturn(FAKE_CHARACTER_ID);

        given(queriedInvitation.getInvitedCharacterId()).willReturn(CHARACTER_ID);
        given(queriedInvitation.isQueried()).willReturn(true);

        given(queryableInvitation.getInvitedCharacterId()).willReturn(CHARACTER_ID);
        given(queryableInvitation.isQueried()).willReturn(false);

        given(invitationViewConverter.convertDomain(queryableInvitation)).willReturn(invitationView);
        //WHEN
        List<InvitationView> result = underTest.getInvitations(CHARACTER_ID);
        //THEN
        assertThat(result).containsOnly(invitationView);
        verify(queryableInvitation).setQueried(true);
    }
}