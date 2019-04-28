package skyxplore.service.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.dataaccess.db.MailDao;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import skyxplore.domain.community.mail.Mail;
import skyxplore.exception.InvalidMailAccessException;
import skyxplore.service.character.CharacterQueryService;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID_1;
import static skyxplore.testutil.TestUtils.MAIL_ID_1;
import static skyxplore.testutil.TestUtils.MAIL_TO_ID;
import static skyxplore.testutil.TestUtils.createCharacter;
import static skyxplore.testutil.TestUtils.createMail;


@RunWith(MockitoJUnitRunner.class)
public class MailStatusUpdaterServiceTest {
    private static final List<String> MAIL_IDS = Arrays.asList(MAIL_ID_1);

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private MailDao mailDao;

    @Mock
    private MailQueryService mailQueryService;

    @InjectMocks
    private MailStatusUpdaterService underTest;

    @Test(expected = InvalidMailAccessException.class)
    public void testArchiveMailsShouldThrowExceptionWhenWrongId() {
        //GIVEN
        SkyXpCharacter character = createCharacter();
        when(characterQueryService.findByCharacterId(CHARACTER_ID_1)).thenReturn(character);

        Mail mail = createMail();
        when(mailQueryService.findMailById(MAIL_ID_1)).thenReturn(mail);
        //WHEN
        underTest.archiveMails(CHARACTER_ID_1, MAIL_IDS, true);
    }

    @Test
    public void testArchiveMailsShouldUpdate() {
        //GIVEN
        SkyXpCharacter character = createCharacter();
        character.setCharacterId(MAIL_TO_ID);
        when(characterQueryService.findByCharacterId(MAIL_TO_ID)).thenReturn(character);

        Mail mail = createMail();
        when(mailQueryService.findMailById(MAIL_ID_1)).thenReturn(mail);
        //WHEN
        underTest.archiveMails(MAIL_TO_ID, MAIL_IDS, true);
        //THEN
        verify(characterQueryService).findByCharacterId(MAIL_TO_ID);
        verify(mailQueryService).findMailById(MAIL_ID_1);
        verify(mailDao).save(mail);
        assertTrue(mail.getArchived());
    }

    @Test(expected = InvalidMailAccessException.class)
    public void testUpdateReadStatusShouldThrowExceptionWhenWrongId() {
        //GIVEN
        SkyXpCharacter character = createCharacter();
        when(characterQueryService.findByCharacterId(CHARACTER_ID_1)).thenReturn(character);

        Mail mail = createMail();
        when(mailQueryService.findMailById(MAIL_ID_1)).thenReturn(mail);
        //WHEN
        underTest.updateReadStatus(MAIL_IDS, CHARACTER_ID_1, true);
    }

    @Test
    public void testUpdateReadStatusShouldUpdate() {
        //GIVEN
        SkyXpCharacter character = createCharacter();
        character.setCharacterId(MAIL_TO_ID);
        when(characterQueryService.findByCharacterId(MAIL_TO_ID)).thenReturn(character);

        Mail mail = createMail();
        when(mailQueryService.findMailById(MAIL_ID_1)).thenReturn(mail);
        //WHEN
        underTest.updateReadStatus(MAIL_IDS, MAIL_TO_ID, true);
        //THEN
        verify(characterQueryService).findByCharacterId(MAIL_TO_ID);
        verify(mailQueryService).findMailById(MAIL_ID_1);
        verify(mailDao).save(mail);
        assertTrue(mail.getRead());
    }
}