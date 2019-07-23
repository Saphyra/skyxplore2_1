package com.github.saphyra.skyxplore.game.lobby.invitation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.game.lobby.invitation.domain.Invitation;
import com.github.saphyra.util.IdGenerator;

@RunWith(MockitoJUnitRunner.class)
public class InvitationFactoryTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String INVITED_CHARACTER_ID = "invited_character_id";
    private static final UUID LOBBY_ID = UUID.randomUUID();
    private static final UUID INVITATION_ID = UUID.randomUUID();
    private static final OffsetDateTime NOW = OffsetDateTime.now();

    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private InvitationFactory underTest;

    @Test
    public void create() {
        //GIVEN
        given(idGenerator.randomUUID()).willReturn(INVITATION_ID);
        given(dateTimeUtil.now()).willReturn(NOW);
        //WHEN
        Invitation result = underTest.create(CHARACTER_ID, INVITED_CHARACTER_ID, LOBBY_ID);
        //THEN
        assertThat(result.getInvitationId()).isEqualTo(INVITATION_ID);
        assertThat(result.getCharacterId()).isEqualTo(CHARACTER_ID);
        assertThat(result.getInvitedCharacterId()).isEqualTo(INVITED_CHARACTER_ID);
        assertThat(result.getLobbyId()).isEqualTo(LOBBY_ID);
        assertThat(result.getCreatedAt()).isEqualTo(NOW);
        assertThat(result.isQueried()).isFalse();
    }
}