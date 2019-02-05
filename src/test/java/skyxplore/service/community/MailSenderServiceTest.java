package skyxplore.service.community;

import com.github.saphyra.util.IdGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.request.community.SendMailRequest;
import skyxplore.dataaccess.db.MailDao;
import skyxplore.domain.community.blockedcharacter.BlockedCharacter;
import skyxplore.domain.community.mail.Mail;
import skyxplore.exception.CharacterBlockedException;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.util.DateTimeUtil;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.MAILS_ADDRESSEE_ID;
import static skyxplore.testutil.TestUtils.MAIL_ID_1;
import static skyxplore.testutil.TestUtils.MAIL_MESSAGE;
import static skyxplore.testutil.TestUtils.MAIL_SEND_TIME;
import static skyxplore.testutil.TestUtils.MAIL_SUBJECT;
import static skyxplore.testutil.TestUtils.createBlockedCharacter;
import static skyxplore.testutil.TestUtils.createSendMailRequest;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@RunWith(MockitoJUnitRunner.class)
public class MailSenderServiceTest {
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
        SendMailRequest request = createSendMailRequest();

        BlockedCharacter blockedCharacter = createBlockedCharacter();
        when(blockedCharacterQueryService.findByCharacterIdOrBlockedCharacterId(CHARACTER_ID_1, MAILS_ADDRESSEE_ID)).thenReturn(Arrays.asList(blockedCharacter));
        //WHEN
        underTest.sendMail(request, CHARACTER_ID_1);
    }

    @Test
    public void testSendMailShouldSave() {
        SendMailRequest request = createSendMailRequest();

        when(blockedCharacterQueryService.findByCharacterIdOrBlockedCharacterId(CHARACTER_ID_1, MAILS_ADDRESSEE_ID)).thenReturn(Collections.emptyList());

        when(idGenerator.generateRandomId()).thenReturn(MAIL_ID_1);
        when(dateTimeUtil.now()).thenReturn(MAIL_SEND_TIME);
        //WHEN
        underTest.sendMail(request, CHARACTER_ID_1);
        //THEN
        verify(characterQueryService).findByCharacterId(MAILS_ADDRESSEE_ID);
        verify(blockedCharacterQueryService).findByCharacterIdOrBlockedCharacterId(CHARACTER_ID_1, MAILS_ADDRESSEE_ID);

        ArgumentCaptor<Mail> argumentCaptor = ArgumentCaptor.forClass(Mail.class);
        verify(mailDao).save(argumentCaptor.capture());
        assertEquals(MAIL_ID_1, argumentCaptor.getValue().getMailId());
        assertEquals(CHARACTER_ID_1, argumentCaptor.getValue().getFrom());
        assertEquals(MAIL_SUBJECT, argumentCaptor.getValue().getSubject());
        assertEquals(MAIL_MESSAGE, argumentCaptor.getValue().getMessage());
        assertEquals(MAIL_SEND_TIME, argumentCaptor.getValue().getSendTime());
        assertFalse(argumentCaptor.getValue().getRead());
        assertFalse(argumentCaptor.getValue().getArchived());
        assertFalse(argumentCaptor.getValue().getDeletedByAddressee());
        assertFalse(argumentCaptor.getValue().getDeletedBySender());
    }
}