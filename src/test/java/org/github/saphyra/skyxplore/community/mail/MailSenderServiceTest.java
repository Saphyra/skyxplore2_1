package org.github.saphyra.skyxplore.community.mail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;

import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.common.DateTimeUtil;
import org.github.saphyra.skyxplore.common.exception.CharacterBlockedException;
import org.github.saphyra.skyxplore.community.blockedcharacter.BlockedCharacterQueryService;
import org.github.saphyra.skyxplore.community.blockedcharacter.domain.BlockedCharacter;
import org.github.saphyra.skyxplore.community.mail.domain.Mail;
import org.github.saphyra.skyxplore.community.mail.domain.SendMailRequest;
import org.github.saphyra.skyxplore.community.mail.repository.MailDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.util.IdGenerator;

@RunWith(MockitoJUnitRunner.class)
public class MailSenderServiceTest {
    private static final String CHARACTER_ID = "character_id";
    private static final String ADDRESSEE_ID = "addressee_id";
    private static final String SUBJECT = "subject";
    private static final String MESSAGE = "message";
    private static final String MAIL_ID = "mail_id";
    private static final OffsetDateTime SEND_TIME = OffsetDateTime.now(ZoneOffset.UTC);

    @Mock
    private BlockedCharacterQueryService blockedCharacterQueryService;

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private DateTimeUtil dateTimeUtil;

    @Mock
    private IdGenerator idGenerator;

    @Mock
    private MailDao mailDao;

    @InjectMocks
    private MailSenderService underTest;

    @Test(expected = CharacterBlockedException.class)
    public void testSendMailShouldThrowExceptionWhenBlocked() {
        //GIVEN
        SendMailRequest request = new SendMailRequest(ADDRESSEE_ID, SUBJECT, MESSAGE);

        BlockedCharacter blockedCharacter = BlockedCharacter.builder().build();
        when(blockedCharacterQueryService.findByCharacterIdOrBlockedCharacterId(CHARACTER_ID, ADDRESSEE_ID)).thenReturn(Arrays.asList(blockedCharacter));
        //WHEN
        underTest.sendMail(request, CHARACTER_ID);
    }

    @Test
    public void testSendMailShouldSave() {
        SendMailRequest request = new SendMailRequest(ADDRESSEE_ID, SUBJECT, MESSAGE);

        when(blockedCharacterQueryService.findByCharacterIdOrBlockedCharacterId(CHARACTER_ID, ADDRESSEE_ID)).thenReturn(Collections.emptyList());

        when(idGenerator.generateRandomId()).thenReturn(MAIL_ID);
        when(dateTimeUtil.now()).thenReturn(SEND_TIME);
        //WHEN
        underTest.sendMail(request, CHARACTER_ID);
        //THEN
        verify(characterQueryService).findByCharacterId(ADDRESSEE_ID);
        verify(blockedCharacterQueryService).findByCharacterIdOrBlockedCharacterId(CHARACTER_ID, ADDRESSEE_ID);

        ArgumentCaptor<Mail> argumentCaptor = ArgumentCaptor.forClass(Mail.class);
        verify(mailDao).save(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getMailId()).isEqualTo(MAIL_ID);
        assertThat(argumentCaptor.getValue().getFrom()).isEqualTo(CHARACTER_ID);
        assertThat(argumentCaptor.getValue().getSubject()).isEqualTo(SUBJECT);
        assertThat(argumentCaptor.getValue().getMessage()).isEqualTo(MESSAGE);
        assertThat(argumentCaptor.getValue().getSendTime()).isEqualTo(SEND_TIME);
        assertThat(argumentCaptor.getValue().getRead()).isFalse();
        assertThat(argumentCaptor.getValue().getArchived()).isFalse();
        assertThat(argumentCaptor.getValue().getDeletedByAddressee()).isFalse();
        assertThat(argumentCaptor.getValue().getDeletedBySender()).isFalse();
    }
}