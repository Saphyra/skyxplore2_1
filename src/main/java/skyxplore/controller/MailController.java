package skyxplore.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import skyxplore.controller.request.OneStringParamRequest;
import skyxplore.controller.request.community.SendMailRequest;
import skyxplore.controller.view.character.CharacterView;
import skyxplore.controller.view.character.CharacterViewConverter;
import skyxplore.controller.view.community.mail.MailView;
import skyxplore.controller.view.community.mail.MailViewConverter;
import skyxplore.filter.AuthFilter;
import skyxplore.filter.CharacterAuthFilter;
import skyxplore.service.MailFacade;

import java.util.List;

import javax.validation.Valid;

@SuppressWarnings("unused")
@Slf4j
@RestController
@RequiredArgsConstructor
public class MailController {
    private static final String ARCHIVE_MAILS_MAPPING = "mail/archive";
    private static final String DELETE_MAILS_MAPPING = "mail/delete";
    private static final String GET_ADDRESSEES_MAPPING = "mail/addressee";
    private static final String GET_ARCHIVED_MAILS_MAPPING = "mail/archived";
    private static final String GET_MAILS_MAPPING = "mail";
    private static final String GET_NUMBER_OF_UNREAD_MAILS_MAPPING = "mail/unread";
    private static final String GET_SENT_MAILS_MAPPING = "mail/sent";
    private static final String MARK_MAILS_READ_MAPPING = "mail/mark/read";
    private static final String MARK_MAILS_UNREAD_MAPPING = "mail/mark/unread";
    private static final String SEND_MAIL_MAPPING = "mail/send";
    private static final String UNARCHIVE_MAILS_MAPPING = "mail/unarchive";

    private final CharacterViewConverter characterViewConverter;
    private final MailFacade mailFacade;
    private final MailViewConverter mailViewConverter;

    @PostMapping(ARCHIVE_MAILS_MAPPING)
    public void archiveMails(
        @RequestBody List<String> mailIds,
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to archive mails {}", characterId, mailIds);
        mailFacade.archiveMails(characterId, mailIds, true);
    }

    @DeleteMapping(DELETE_MAILS_MAPPING)
    public void deleteMails(
        @RequestBody List<String> mailIds,
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to delete mails {}", characterId, mailIds);
        mailFacade.deleteMails(characterId, mailIds);
    }

    @PostMapping(GET_ADDRESSEES_MAPPING)
    public List<CharacterView> getAddressees(
        @RequestBody OneStringParamRequest name,
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ) {
        log.info("{} wants to know his possible addressees", characterId);
        return characterViewConverter.convertDomain(mailFacade.getAddressees(characterId, userId, name.getValue()));
    }

    @GetMapping(GET_ARCHIVED_MAILS_MAPPING)
    public List<MailView> getArchivedMails(
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ){
        log.info("{} wants to know his archived mails.");
        return mailViewConverter.convertDomain(mailFacade.getArchivedMails(characterId));
    }

    @GetMapping(GET_MAILS_MAPPING)
    //TODO unit test
    public List<MailView> getMails(
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his mails.", characterId);
        return mailViewConverter.convertDomain(mailFacade.getMails(characterId));
    }

    @GetMapping(GET_NUMBER_OF_UNREAD_MAILS_MAPPING)
    //TODO unit test
    public Integer getNumberOfUnreadMails(
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know the number of his unread mails.", characterId);
        return mailFacade.getNumberOfUnreadMails(characterId);
    }

    @GetMapping(GET_SENT_MAILS_MAPPING)
    //TODO unit test
    public List<MailView> getSentMails(
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his sent mails.", characterId);
        return mailViewConverter.convertDomain(mailFacade.getSentMails(characterId));
    }

    @PostMapping(MARK_MAILS_READ_MAPPING)
    //TODO unit test
    public void markMailsRead(
        @RequestBody List<String> mailIds,
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to mark mails {} as read.", characterId, mailIds);
        mailFacade.setMailReadStatus(mailIds, characterId, true);
    }

    @PostMapping(MARK_MAILS_UNREAD_MAPPING)
    //TODO unit test
    public void markMailsUnRead(
        @RequestBody List<String> mailIds,
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to mark mails {} as unread.", characterId, mailIds);
        mailFacade.setMailReadStatus(mailIds, characterId, false);
    }

    @PutMapping(SEND_MAIL_MAPPING)
    //TODO unit test
    public void sendMail(
        @RequestBody @Valid SendMailRequest request,
        @CookieValue(AuthFilter.COOKIE_USER_ID) String userId
    ) {
        log.info("Sending mail...");
        mailFacade.sendMail(request, userId);
    }

    @PostMapping(UNARCHIVE_MAILS_MAPPING)
    //TODO unit test
    public void unarchiveMails(
        @RequestBody List<String> mailIds,
        @CookieValue(CharacterAuthFilter.COOKIE_CHARACTER_ID) String characterId
    ){
        log.info("{} wants to unarchive mails {}", characterId, mailIds);
        mailFacade.archiveMails(characterId, mailIds, false);
    }
}
