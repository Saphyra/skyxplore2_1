package skyxplore.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import skyxplore.controller.request.OneStringParamRequest;
import skyxplore.controller.request.community.SendMailRequest;
import skyxplore.controller.view.character.CharacterView;
import skyxplore.controller.view.character.CharacterViewConverter;
import skyxplore.controller.view.community.mail.MailView;
import skyxplore.controller.view.community.mail.MailViewConverter;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.community.mail.Mail;
import skyxplore.service.MailFacade;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.*;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@RunWith(MockitoJUnitRunner.class)
public class MailControllerTest {
    @Mock
    private CharacterViewConverter characterViewConverter;

    @Mock
    private MailFacade mailFacade;

    @Mock
    private MailViewConverter mailViewConverter;

    @InjectMocks
    private MailController underTest;

    @Test
    public void testArchiveMailsShouldCallFacade() {
        //GIVEN
        List<String> mailIds = createMailIdList(MAIL_ID_1);
        //WHEN
        underTest.archiveMails(mailIds, CHARACTER_ID_1);
        //THEN
        verify(mailFacade).archiveMails(CHARACTER_ID_1, mailIds, true);
    }

    @Test
    public void testDeleteMailsShouldCallFacade() {
        //GIVEN
        List<String> mailIds = createMailIdList(MAIL_ID_1);
        //WHEN
        underTest.deleteMails(mailIds, CHARACTER_ID_1);
        //THEN
        verify(mailFacade).deleteMails(CHARACTER_ID_1, mailIds);
    }

    @Test
    public void testGetAddresseesShouldCallFacadeAndReturnView() {
        //GIVEN
        SkyXpCharacter character = createCharacter();
        List<SkyXpCharacter> characterList = Arrays.asList(character);
        when(mailFacade.getAddressees(CHARACTER_ID_1, CHARACTER_NAME)).thenReturn(characterList);

        List<CharacterView> viewList = Arrays.asList(createCharacterView(character));
        when(characterViewConverter.convertDomain(characterList)).thenReturn(viewList);
        //WHEN
        List<CharacterView> result = underTest.getAddressees(new OneStringParamRequest(CHARACTER_NAME), CHARACTER_ID_1);
        //THEN
        verify(mailFacade).getAddressees(CHARACTER_ID_1, CHARACTER_NAME);
        verify(characterViewConverter).convertDomain(characterList);
        assertEquals(viewList, result);
    }

    @Test
    public void testGetArchivedMailsShouldCallFacadeAndReturnView() {
        //GIVEN
        Mail mail = createMail();
        List<Mail> mailList = Arrays.asList(mail);
        when(mailFacade.getArchivedMails(CHARACTER_ID_1)).thenReturn(mailList);

        MailView view = createMailView();
        List<MailView> viewList = Arrays.asList(view);
        when(mailViewConverter.convertDomain(mailList)).thenReturn(viewList);
        //WHEN
        List<MailView> result = underTest.getArchivedMails(CHARACTER_ID_1);
        //THEN
        verify(mailFacade).getArchivedMails(CHARACTER_ID_1);
        verify(mailViewConverter).convertDomain(mailList);
        assertEquals(viewList, result);
    }

    @Test
    public void testGetMailsShouldCallFacadeAndReturnView() {
        //GIVEN
        Mail mail = createMail();
        List<Mail> mailList = Arrays.asList(mail);
        when(mailFacade.getMails(CHARACTER_ID_1)).thenReturn(mailList);

        MailView view = createMailView();
        List<MailView> viewList = Arrays.asList(view);
        when(mailViewConverter.convertDomain(mailList)).thenReturn(viewList);
        //WHEN
        List<MailView> result = underTest.getMails(CHARACTER_ID_1);
        //THEN
        verify(mailFacade).getMails(CHARACTER_ID_1);
        verify(mailViewConverter).convertDomain(mailList);
        assertEquals(viewList, result);
    }

    @Test
    public void testGetSentMailsShouldCallFacadeAndReturnView() {
        //GIVEN
        Mail mail = createMail();
        List<Mail> mailList = Arrays.asList(mail);
        when(mailFacade.getSentMails(CHARACTER_ID_1)).thenReturn(mailList);

        MailView view = createMailView();
        List<MailView> viewList = Arrays.asList(view);
        when(mailViewConverter.convertDomain(mailList)).thenReturn(viewList);
        //WHEN
        List<MailView> result = underTest.getSentMails(CHARACTER_ID_1);
        //THEN
        verify(mailFacade).getSentMails(CHARACTER_ID_1);
        verify(mailViewConverter).convertDomain(mailList);
        assertEquals(viewList, result);
    }

    @Test
    public void testMarkMailsReadShouldCallFacade() {
        //GIVEN
        List<String> mailIds = Arrays.asList(MAIL_ID_1);
        //WHEN
        underTest.markMailsRead(mailIds, CHARACTER_ID_1);
        //THEN
        verify(mailFacade).setMailReadStatus(mailIds, CHARACTER_ID_1, true);
    }

    @Test
    public void testMarkMailsUnreadShouldCallFacade() {
        //GIVEN
        List<String> mailIds = Arrays.asList(MAIL_ID_1);
        //WHEN
        underTest.markMailsUnread(mailIds, CHARACTER_ID_1);
        //THEN
        verify(mailFacade).setMailReadStatus(mailIds, CHARACTER_ID_1, false);
    }

    @Test
    public void testSendMailShouldCallFacade() {
        //GIVEN
        SendMailRequest request = createSendMailRequest();
        //WHEN
        underTest.sendMail(request, CHARACTER_ID_1);
        //THEN
        verify(mailFacade).sendMail(request, CHARACTER_ID_1);
    }

    @Test
    public void testUnarchiveMailsShouldCallFacade() {
        //GIVEN
        List<String> mailIds = Arrays.asList(MAIL_ID_1);
        //WHEN
        underTest.unarchiveMails(mailIds, CHARACTER_ID_1);
        //THEN
        verify(mailFacade).archiveMails(CHARACTER_ID_1, mailIds, false);
    }
}
