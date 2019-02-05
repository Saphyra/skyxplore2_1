package skyxplore.service.community;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.MAIL_FROM_ID;
import static skyxplore.testutil.TestUtils.MAIL_ID_1;
import static skyxplore.testutil.TestUtils.MAIL_TO_ID;
import static skyxplore.testutil.TestUtils.createCharacter;
import static skyxplore.testutil.TestUtils.createMail;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.dataaccess.db.MailDao;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.community.mail.Mail;
import skyxplore.exception.InvalidMailAccessException;
import skyxplore.service.character.CharacterQueryService;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@RunWith(MockitoJUnitRunner.class)
public class MailDeleteServiceTest {
    private static final List<String> MAIL_IDS = Arrays.asList(MAIL_ID_1);

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private MailDao mailDao;

    @Mock
    private MailQueryService mailQueryService;

    private SkyXpCharacter character;

    private Mail mail;

    @InjectMocks
    private MailDeleteService underTest;

    @Before
    public void init() {
        character = createCharacter();
        when(characterQueryService.findByCharacterId(anyString())).thenReturn(character);

        mail = createMail();
        when(mailQueryService.findMailById(anyString())).thenReturn(mail);
    }

    @After
    public void verifyInteractions() {
        verify(characterQueryService).findByCharacterId(anyString());
        verify(mailQueryService).findMailById(anyString());
    }

    @Test(expected = InvalidMailAccessException.class)
    public void testDeleteMailShouldThrowExceptionWhenWrongId() {
        //WHEN
        underTest.deleteMails(CHARACTER_ID_1, MAIL_IDS);
    }

    @Test
    public void testDeleteMailShouldUpdateDeletedByAddressee() {
        //GIVEN
        character.setCharacterId(MAIL_TO_ID);
        //WHEN
        underTest.deleteMails(MAIL_TO_ID, MAIL_IDS);
        //THEN
        assertEquals(true, mail.getDeletedByAddressee());
        verify(mailDao).save(mail);
    }

    @Test
    public void testDeleteMailShouldUpdateDeletedBySender() {
        //GIVEN
        character.setCharacterId(MAIL_FROM_ID);
        //WHEN
        underTest.deleteMails(MAIL_FROM_ID, MAIL_IDS);
        //THEN
        assertEquals(true, mail.getDeletedBySender());
        verify(mailDao).save(mail);
    }
}