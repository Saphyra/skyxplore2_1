package com.github.saphyra.skyxplore.game.lobby.invitation;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.game.lobby.invitation.domain.Invitation;

@RunWith(MockitoJUnitRunner.class)
public class InvitationCleanupServiceTest {
    private static final OffsetDateTime CURRENT_DATE = OffsetDateTime.now(ZoneOffset.UTC);
    private static final Integer EXPIRATION_SECONDS = 60;
    private static final Integer EXTENDED_EXPIRATION_SECONDS = 120;
    private static final UUID INVITATION_ID_1 = UUID.randomUUID();
    private static final UUID INVITATION_ID_2 = UUID.randomUUID();

    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private InvitationProperties invitationProperties;

    @Mock
    private InvitationStorage invitationStorage;

    @InjectMocks
    private InvitationCleanupService underTest;

    @Mock
    private Invitation expiredQueriedInvitation;

    @Mock
    private Invitation validQueriedInvitation;

    @Mock
    private Invitation expiredInvitation;

    @Mock
    private Invitation validInvitation;

    @Test
    public void deleteExpiredInvitations() {
        //GIVEN
        given(invitationStorage.values()).willReturn(Arrays.asList(expiredInvitation, validInvitation, expiredQueriedInvitation, validQueriedInvitation));
        given(dateTimeUtil.now()).willReturn(CURRENT_DATE);

        given(expiredQueriedInvitation.isQueried()).willReturn(true);
        given(validQueriedInvitation.isQueried()).willReturn(true);

        given(expiredQueriedInvitation.getCreatedAt()).willReturn(CURRENT_DATE.minusSeconds(EXPIRATION_SECONDS + EXTENDED_EXPIRATION_SECONDS + 1));
        given(validQueriedInvitation.getCreatedAt()).willReturn(CURRENT_DATE.minusSeconds(EXPIRATION_SECONDS + EXTENDED_EXPIRATION_SECONDS - 1));
        given(expiredInvitation.getCreatedAt()).willReturn(CURRENT_DATE.minusSeconds(EXPIRATION_SECONDS + 1));
        given(validInvitation.getCreatedAt()).willReturn(CURRENT_DATE.minusSeconds(EXPIRATION_SECONDS - 1));

        given(expiredQueriedInvitation.getInvitationId()).willReturn(INVITATION_ID_1);
        given(expiredInvitation.getInvitationId()).willReturn(INVITATION_ID_2);

        given(invitationProperties.getExpirationSeconds()).willReturn(EXPIRATION_SECONDS);
        given(invitationProperties.getExtendedExpirationSeconds()).willReturn(EXTENDED_EXPIRATION_SECONDS);
        //WHEN
        underTest.deleteExpiredInvitations();
        //THEN
        verify(invitationStorage).values();
        verify(invitationStorage).remove(INVITATION_ID_1);
        verify(invitationStorage).remove(INVITATION_ID_2);
        verifyNoMoreInteractions(invitationStorage);
    }
}