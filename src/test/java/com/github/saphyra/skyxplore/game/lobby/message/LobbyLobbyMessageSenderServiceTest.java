package com.github.saphyra.skyxplore.game.lobby.message;

import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.game.lobby.message.domain.LobbyMessage;
import com.github.saphyra.util.IdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LobbyLobbyMessageSenderServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String MESSAGE = "message";
    private static final UUID MESSAGE_ID = UUID.randomUUID();
    private static final OffsetDateTime NOW = OffsetDateTime.now();
    private static final Long CREATED_AT = 6464L;

    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private LobbyQueryService lobbyQueryService;

    @InjectMocks
    private LobbyMessageSenderService underTest;

    @Mock
    private Lobby lobby;

    @Test
    public void sendMessage() {
        //GIVEN
        given(lobbyQueryService.findByCharacterIdValidated(CHARACTER_ID)).willReturn(lobby);
        given(idGenerator.randomUUID()).willReturn(MESSAGE_ID);
        given(dateTimeUtil.now()).willReturn(NOW);
        given(dateTimeUtil.convertDomain(NOW)).willReturn(CREATED_AT);
        //WHEN
        underTest.sendMessage(CHARACTER_ID, MESSAGE);
        //THEN
        ArgumentCaptor<LobbyMessage> argumentCaptor = ArgumentCaptor.forClass(LobbyMessage.class);
        verify(lobby).addMessage(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue().getMessageId()).isEqualTo(MESSAGE_ID);
        assertThat(argumentCaptor.getValue().getSender()).isEqualTo(CHARACTER_ID);
        assertThat(argumentCaptor.getValue().getMessage()).isEqualTo(MESSAGE);
        assertThat(argumentCaptor.getValue().getCreatedAt()).isEqualTo(CREATED_AT);
        assertThat(argumentCaptor.getValue().getQueriedBy()).isEmpty();
    }
}