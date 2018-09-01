package skyxplore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import skyxplore.controller.request.community.DeleteMailRequest;
import skyxplore.controller.request.community.MarkMailReadRequest;
import skyxplore.controller.request.community.SendMailRequest;
import skyxplore.controller.view.character.CharacterView;
import skyxplore.controller.view.character.CharacterViewConverter;
import skyxplore.controller.view.community.mail.MailView;
import skyxplore.controller.view.community.mail.MailViewConverter;
import skyxplore.filter.AuthFilter;
import skyxplore.filter.CharacterAuthFilter;
import skyxplore.service.MailFacade;

import java.util.List;

@SuppressWarnings("unused")
@Slf4j
@RestController
@RequiredArgsConstructor
//TODO unit test
//TODO eliminate characterIds in path
public class MailController {
    private static final String DELETE_MAILS_MAPPING = "mail/delete/";
    private static final String GET_ADDRESSEES_MAPPING = "mail/addressee/{characterId}/{name}";
    private static final String GET_MAILS_MAPPING = "mail";
    private static final String GET_NUMBER_OF_UNREAD_MAILS_MAPPING = "mail/unread";
    private static final String MARK_MAILS_READ_MAPPING = "mail/markread/";
    private static final String MARK_MAILS_UNREAD_MAPPING = "mail/markunread/";
    private static final String SEND_MAIL_MAPPING = "mail/send";

    private final CharacterViewConverter characterViewConverter;
    private final MailFacade mailFacade;
    private final MailViewConverter mailViewConverter;

    @DeleteMapping(DELETE_MAILS_MAPPING)
    public void deleteMails(
        @RequestBody DeleteMailRequest request,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to delete mails {}", request.getCharacterId(), request.getMailIds());
        mailFacade.deleteMails(request, userId);
    }

    @GetMapping(GET_ADDRESSEES_MAPPING)
    public List<CharacterView> getAddressees(
        @PathVariable("characterId") String characterId,
        @PathVariable("name") String name,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to know his possible addressees", characterId);
        return characterViewConverter.convertDomain(mailFacade.getAddressees(characterId, userId, name));
    }

    @GetMapping(GET_MAILS_MAPPING)
    public List<MailView> getMails(
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ){
        log.info("{} wants to know his mails.");
        return mailViewConverter.convertDomain(mailFacade.getMails(characterId));
    }

    @GetMapping(GET_NUMBER_OF_UNREAD_MAILS_MAPPING)
    public Integer getNumberOfUnreadMails(
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ){
        log.info("{} wants to know the number of his unread mails.");
        return mailFacade.getNumberOfUnreadMails(characterId);
    }

    @PostMapping(MARK_MAILS_READ_MAPPING)
    public void markMailsRead(
        @RequestBody MarkMailReadRequest request,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to mark mails {} as read.", request.getCharacterId(), request.getMailIds());
        mailFacade.setMailReadStatus(request, userId, true);
    }

    @PostMapping(MARK_MAILS_UNREAD_MAPPING)
    public void markMailsUnRead(
        @RequestBody MarkMailReadRequest request,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ){
        log.info("{} wants to mark mails {} as unread.", request.getCharacterId(), request.getMailIds());
        mailFacade.setMailReadStatus(request, userId, false);
    }

    @PutMapping(SEND_MAIL_MAPPING)
    public void sendMail(
        @RequestBody SendMailRequest request,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ){
        log.info("Sending mail...");
        mailFacade.sendMail(request, userId);
    }
}
