package skyxplore.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static skyxplore.testutil.TestUtils.CHARACTER_ID;
import static skyxplore.testutil.TestUtils.CHARACTER_NAME;
import static skyxplore.testutil.TestUtils.MAIL_ID_1;
import static skyxplore.testutil.TestUtils.USER_ID;
import static skyxplore.testutil.TestUtils.createCharacter;
import static skyxplore.testutil.TestUtils.createCharacterView;
import static skyxplore.testutil.TestUtils.createMail;
import static skyxplore.testutil.TestUtils.createMailIdList;
import static skyxplore.testutil.TestUtils.createMailView;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import skyxplore.controller.request.OneStringParamRequest;
import skyxplore.controller.view.character.CharacterView;
import skyxplore.controller.view.character.CharacterViewConverter;
import skyxplore.controller.view.community.mail.MailView;
import skyxplore.controller.view.community.mail.MailViewConverter;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.community.mail.Mail;
import skyxplore.service.MailFacade;

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
    public void testArchiveMailsShouldCallFacade(){
        //GIVEN
        List<String> mailIds = createMailIdList(MAIL_ID_1);
        //WHEN
        underTest.archiveMails(mailIds, CHARACTER_ID);
        //THEN
        verify(mailFacade).archiveMails(CHARACTER_ID, mailIds, true);
    }

    @Test
    public void testDeleteMailsShouldCallFacade(){
        //GIVEN
        List<String> mailIds = createMailIdList(MAIL_ID_1);
        //WHEN
        underTest.deleteMails(mailIds, CHARACTER_ID);
        //THEN
        verify(mailFacade).deleteMails(CHARACTER_ID, mailIds);
    }

    @Test
    public void testGetAddresseesShouldCallFacadeAndReturnView(){
        //GIVEN
        SkyXpCharacter character = createCharacter();
        List<SkyXpCharacter> characterList = Arrays.asList(character);
        when(mailFacade.getAddressees(CHARACTER_ID, USER_ID, CHARACTER_NAME)).thenReturn(characterList);

        List<CharacterView> viewList = Arrays.asList(createCharacterView(character));
        when(characterViewConverter.convertDomain(characterList)).thenReturn(viewList);
        //WHEN
        List<CharacterView> result = underTest.getAddressees(new OneStringParamRequest(CHARACTER_NAME), CHARACTER_ID, USER_ID);
        //THEN
        verify(mailFacade).getAddressees(CHARACTER_ID, USER_ID, CHARACTER_NAME);
        verify(characterViewConverter).convertDomain(characterList);
        assertEquals(viewList, result);
    }

    @Test
    public void testGetArchivedMailsShouldCallFacadeAndReturnView(){
        //GIVEN
        Mail mail = createMail();
        List<Mail> mailList = Arrays.asList(mail);
        when(mailFacade.getArchivedMails(CHARACTER_ID)).thenReturn(mailList);

        MailView view = createMailView();
        List<MailView> viewList = Arrays.asList(view);
        when(mailViewConverter.convertDomain(mailList)).thenReturn(viewList);
        //WHEN
        List<MailView> result = underTest.getArchivedMails(CHARACTER_ID);
        //THEN
        verify(mailFacade).getArchivedMails(CHARACTER_ID);
        verify(mailViewConverter).convertDomain(mailList);
        assertEquals(viewList, result);
    }
}
