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

import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.game.lobby.lobby.LobbyQueryService;
import com.github.saphyra.skyxplore.game.lobby.lobby.domain.Lobby;
import com.github.saphyra.skyxplore.game.lobby.message.domain.Message;
import com.github.saphyra.skyxplore.game.lobby.message.domain.MessageView;

@RunWith(MockitoJUnitRunner.class)
public class MessageViewQueryServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String SENDER_ID = "sender_id";
    private static final String CHARACTER_NAME = "character_name";
    private static final String MESSAGE_1 = "message_1";
    private static final String MESSAGE_2 = "message_2";
    private static final Long CREATED_AT = 6461L;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private LobbyQueryService lobbyQueryService;

    @InjectMocks
    private MessageViewQueryService underTest;

    @Mock
    private Lobby lobby;

    @Mock
    private Message queriedMessage;

    @Mock
    private Message message;

    @Mock
    private SkyXpCharacter character;

    @Before
    public void setup() {
        given(lobbyQueryService.findByCharacterIdValidated(CHARACTER_ID)).willReturn(lobby);
        given(lobby.getMessages()).willReturn(Arrays.asList(message, queriedMessage));

        given(queriedMessage.getQueriedBy()).willReturn(Arrays.asList(CHARACTER_ID));
        given(queriedMessage.getSender()).willReturn(SENDER_ID);
        given(queriedMessage.getMessage()).willReturn(MESSAGE_1);
        given(queriedMessage.getCreatedAt()).willReturn(CREATED_AT);

        given(message.getQueriedBy()).willReturn(Collections.emptyList());
        given(message.getSender()).willReturn(SENDER_ID);
        given(message.getMessage()).willReturn(MESSAGE_2);
        given(message.getCreatedAt()).willReturn(CREATED_AT);

        given(characterQueryService.findByCharacterId(SENDER_ID)).willReturn(character);
        given(character.getCharacterName()).willReturn(CHARACTER_NAME);
    }

    @Test
    public void getMessages() {
        //WHEN
        List<MessageView> result = underTest.getMessages(CHARACTER_ID, false);
        //THEN
        verify(message).addQueriedBy(CHARACTER_ID);

        assertThat(result).hasSize(1);
        MessageView messageView = result.get(0);
        assertThat(messageView.getSenderId()).isEqualTo(SENDER_ID);
        assertThat(messageView.getSenderName()).isEqualTo(CHARACTER_NAME);
        assertThat(messageView.getMessage()).isEqualTo(MESSAGE_2);
        assertThat(messageView.getCreatedAt()).isEqualTo(CREATED_AT);
    }

    @Test
    public void getMessages_queryAll() {
        //WHEN
        List<MessageView> result = underTest.getMessages(CHARACTER_ID, true);
        //THEN
        verify(message).addQueriedBy(CHARACTER_ID);
        verify(queriedMessage).addQueriedBy(CHARACTER_ID);

        assertThat(result).hasSize(2);
    }
}