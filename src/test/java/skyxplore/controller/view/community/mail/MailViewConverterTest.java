package skyxplore.controller.view.community.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.community.mail.domain.Mail;
import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.common.DateTimeUtil;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class MailViewConverterTest {
    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private DateTimeUtil dateTimeUtil;

    @InjectMocks
    private MailViewConverter underTest;

    @Test
    public void testConvertDomainShouldConvertAndReturn() {
        //GIVEN
        Mail mail = createMail();

        SkyXpCharacter from = createCharacter();
        from.setCharacterName(MAIL_FROM_NAME);

        SkyXpCharacter to = createCharacter();
        to.setCharacterName(MAIL_TO_NAME);

        when(characterQueryService.findByCharacterId(MAIL_FROM_ID)).thenReturn(from);
        when(characterQueryService.findByCharacterId(MAIL_TO_ID)).thenReturn(to);
        when(dateTimeUtil.convertDomain(MAIL_SEND_TIME)).thenReturn(MAIL_SEND_TIME_EPOCH);
        //WHEN
        MailView result = underTest.convertDomain(mail);
        //THEN
        verify(characterQueryService).findByCharacterId(MAIL_FROM_ID);
        verify(characterQueryService).findByCharacterId(MAIL_TO_ID);
        verify(dateTimeUtil).convertDomain(MAIL_SEND_TIME);
        assertEquals(MAIL_ID_1, result.getMailId());
        assertEquals(MAIL_FROM_ID, result.getFrom());
        assertEquals(MAIL_TO_ID, result.getTo());
        assertEquals(MAIL_FROM_NAME, result.getFromName());
        assertEquals(MAIL_TO_NAME, result.getToName());
        assertEquals(MAIL_SUBJECT, result.getSubject());
        assertEquals(MAIL_MESSAGE, result.getMessage());
        assertEquals(mail.getRead(), result.getRead());
        assertEquals(MAIL_SEND_TIME_EPOCH, result.getSendTime());
    }
}
