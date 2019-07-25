package com.github.saphyra.skyxplore.game.lobby.message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.common.domain.message.Message;
import com.github.saphyra.skyxplore.common.domain.message.MessageView;
import com.github.saphyra.skyxplore.common.domain.message.MessageViewConverter;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;

@RunWith(MockitoJUnitRunner.class)
public class LobbyMessageViewQueryServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String SENDER_ID = "sender_id";
    private static final String CHARACTER_NAME = "character_name";
    private static final String MESSAGE_1 = "message_1";
    private static final String MESSAGE_2 = "message_2";
    private static final Long CREATED_AT = 6461L;

    @Mock
    private LobbyQueryService lobbyQueryService;

    @Mock
    private MessageViewConverter messageViewConverter;

    @InjectMocks
    private LobbyMessageViewQueryService underTest;

    @Mock
    private Lobby lobby;

    @Mock
    private Message queriedMessage;

    @Mock
    private Message message;

    @Mock
    private MessageView messageView;

    @Before
    public void setup() {
        given(lobbyQueryService.findByCharacterIdValidated(CHARACTER_ID)).willReturn(lobby);
        given(lobby.getMessages()).willReturn(Arrays.asList(message, queriedMessage));

        given(queriedMessage.getQueriedBy()).willReturn(Arrays.asList(CHARACTER_ID));

        given(message.getQueriedBy()).willReturn(Collections.emptyList());


        given(messageViewConverter.convertDomain(message)).willReturn(messageView);
        given(messageViewConverter.convertDomain(queriedMessage)).willReturn(messageView);
    }

    @Test
    public void getMessages() {
        //WHEN
        List<MessageView> result = underTest.getMessages(CHARACTER_ID, false);
        //THEN
        verify(message).addQueriedBy(CHARACTER_ID);

        assertThat(result).containsExactly(messageView);
    }

    @Test
    public void getMessages_queryAll() {
        //WHEN
        List<MessageView> result = underTest.getMessages(CHARACTER_ID, true);
        //THEN
        verify(message).addQueriedBy(CHARACTER_ID);
        verify(queriedMessage).addQueriedBy(CHARACTER_ID);

        assertThat(result).hasSize(2);
        assertThat(result).containsOnly(messageView);
    }
}