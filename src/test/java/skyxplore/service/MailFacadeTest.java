package skyxplore.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.request.community.SendMailRequest;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.community.mail.Mail;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.service.community.MailDeleteService;
import skyxplore.service.community.MailQueryService;
import skyxplore.service.community.MailSenderService;
import skyxplore.service.community.MailStatusUpdaterService;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@RunWith(MockitoJUnitRunner.class)
public class MailFacadeTest {
    private static final List<String> MAIL_IDS = Arrays.asList(MAIL_ID_1);

    @Mock
    private CharacterQueryService characterQueryService;

    @Mock
    private MailDeleteService mailDeleteService;

    @Mock
    private MailSenderService mailSenderService;

    @Mock
    private MailStatusUpdaterService mailStatusUpdaterService;

    @Mock
    private MailQueryService mailQueryService;

    @InjectMocks
    private MailFacade underTest;

    @Test
    public void testArchiveMailsShouldCallService() {
        //WHEN
        underTest.archiveMails(CHARACTER_ID_1, MAIL_IDS, true);
        //THEN
        verify(mailStatusUpdaterService).archiveMails(CHARACTER_ID_1, MAIL_IDS, true);
    }

    @Test
    public void testDeleteMailsShouldCallService() {
        //WHEN
        underTest.deleteMails(CHARACTER_ID_1, MAIL_IDS);
        //THEN
        verify(mailDeleteService).deleteMails(CHARACTER_ID_1, MAIL_IDS);
    }

    @Test
    public void testGetAddresseesShouldCallServiceAndReturn() {
        //GIVEN
        SkyXpCharacter character = createCharacter();
        List<SkyXpCharacter> characterList = Arrays.asList(character);
        when(characterQueryService.getCharactersCanBeAddressee(CHARACTER_NAME, CHARACTER_ID_1)).thenReturn(characterList);
        //WHEN
        List<SkyXpCharacter> result = underTest.getAddressees(CHARACTER_ID_1, CHARACTER_NAME);
        //THEN
        verify(characterQueryService).getCharactersCanBeAddressee(CHARACTER_NAME, CHARACTER_ID_1);
        assertEquals(characterList, result);
    }

    @Test
    public void testGetArchivedMails() {
        //GIVEN
        Mail mail = createMail();
        List<Mail> mailList = Arrays.asList(mail);
        when(mailQueryService.getArchivedMails(CHARACTER_ID_1)).thenReturn(mailList);
        //WHEN
        List<Mail> result = underTest.getArchivedMails(CHARACTER_ID_1);
        //THEN
        verify(mailQueryService).getArchivedMails(CHARACTER_ID_1);
        assertEquals(mailList, result);
    }

    @Test
    public void testGetMails() {
        //GIVEN
        Mail mail = createMail();
        List<Mail> mailList = Arrays.asList(mail);
        when(mailQueryService.getMails(CHARACTER_ID_1)).thenReturn(mailList);
        //WHEN
        List<Mail> result = underTest.getMails(CHARACTER_ID_1);
        //THEN
        verify(mailQueryService).getMails(CHARACTER_ID_1);
        assertEquals(mailList, result);
    }

    @Test
    public void testGetNumberOfUnreadMails() {
        //GIVEN
        when(mailQueryService.getNumberOfUnreadMails(CHARACTER_ID_1)).thenReturn(2);
        //WHEN
        Integer result = underTest.getNumberOfUnreadMails(CHARACTER_ID_1);
        //THEN
        verify(mailQueryService).getNumberOfUnreadMails(CHARACTER_ID_1);
        assertEquals((Integer) 2, result);
    }

    @Test
    public void testGetSentMails() {
        //GIVEN
        Mail mail = createMail();
        List<Mail> mailList = Arrays.asList(mail);
        when(mailQueryService.getSentMails(CHARACTER_ID_1)).thenReturn(mailList);
        //WHEN
        List<Mail> result = underTest.getSentMails(CHARACTER_ID_1);
        //THEN
        verify(mailQueryService).getSentMails(CHARACTER_ID_1);
        assertEquals(mailList, result);
    }

    @Test
    public void testSendMail(){
        //GIVEN
        SendMailRequest request = createSendMailRequest();
        //WHEN
        underTest.sendMail(request, CHARACTER_ID_1);
        //THEN
        verify(mailSenderService).sendMail(request, CHARACTER_ID_1);
    }

    @Test
    public void testSetMailReadStatus(){
        //WHEN
        underTest.setMailReadStatus(MAIL_IDS, CHARACTER_ID_1, true);
        //THEN
        verify(mailStatusUpdaterService).updateReadStatus(MAIL_IDS, CHARACTER_ID_1, true);
    }
}