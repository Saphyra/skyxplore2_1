package com.github.saphyra.skyxplore.community.mail;

import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.github.saphyra.skyxplore.common.OneStringParamRequest;
import com.github.saphyra.skyxplore.common.domain.character.CharacterView;
import com.github.saphyra.skyxplore.common.domain.character.CharacterViewConverter;
import com.github.saphyra.skyxplore.filter.CustomFilterHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.saphyra.skyxplore.community.mail.domain.MailView;
import com.github.saphyra.skyxplore.community.mail.domain.SendMailRequest;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
class MailController {
    private static final String ARCHIVE_MAILS_MAPPING = "mail/archive";
    private static final String DELETE_MAILS_MAPPING = "mail";
    private static final String GET_ADDRESSEES_MAPPING = "mail/addressee";
    private static final String GET_ARCHIVED_MAILS_MAPPING = "mail/archived";
    private static final String GET_MAILS_MAPPING = "mail";
    private static final String GET_SENT_MAILS_MAPPING = "mail/sent";
    private static final String MARK_MAILS_READ_MAPPING = "mail/mark/read";
    private static final String MARK_MAILS_UNREAD_MAPPING = "mail/mark/unread";
    private static final String SEND_MAIL_MAPPING = "mail";
    private static final String RESTORE_MAILS_MAPPING = "mail/restore";

    private final CharacterViewConverter characterViewConverter;
    private final CharacterQueryService characterQueryService;
    private final MailDeleteService mailDeleteService;
    private final MailSenderService mailSenderService;
    private final MailStatusUpdaterService mailStatusUpdaterService;
    private final MailQueryService mailQueryService;
    private final MailViewConverter mailViewConverter;

    @PostMapping(ARCHIVE_MAILS_MAPPING)
    void archiveMails(
        @RequestBody List<String> mailIds,
        @CookieValue(CustomFilterHelper.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to archive mails {}", characterId, mailIds);
        mailStatusUpdaterService.archiveMails(characterId, mailIds, true);
    }

    @DeleteMapping(DELETE_MAILS_MAPPING)
    void deleteMails(
        @RequestBody List<String> mailIds,
        @CookieValue(CustomFilterHelper.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to deleteById mails {}", characterId, mailIds);
        mailDeleteService.deleteMails(characterId, mailIds);
    }

    @PostMapping(GET_ADDRESSEES_MAPPING)
    List<CharacterView> getAddressees(
        @RequestBody OneStringParamRequest name,
        @CookieValue(CustomFilterHelper.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his possible addressees", characterId);
        return characterViewConverter.convertDomain(characterQueryService.getCharactersCanBeAddressee(characterId, name.getValue()));
    }

    @GetMapping(GET_ARCHIVED_MAILS_MAPPING)
    List<MailView> getArchivedMails(
        @CookieValue(CustomFilterHelper.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his archived mails.");
        return mailViewConverter.convertDomain(mailQueryService.getArchivedMails(characterId));
    }

    @GetMapping(GET_MAILS_MAPPING)
    List<MailView> getMails(
        @CookieValue(CustomFilterHelper.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his mails.", characterId);
        return mailViewConverter.convertDomain(mailQueryService.getMails(characterId));
    }

    @GetMapping(GET_SENT_MAILS_MAPPING)
    List<MailView> getSentMails(
        @CookieValue(CustomFilterHelper.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his sent mails.", characterId);
        return mailViewConverter.convertDomain(mailQueryService.getSentMails(characterId));
    }

    @PostMapping(MARK_MAILS_READ_MAPPING)
    void markMailsRead(
        @RequestBody List<String> mailIds,
        @CookieValue(CustomFilterHelper.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to mark mails {} as read.", characterId, mailIds);
        mailStatusUpdaterService.updateReadStatus(mailIds, characterId, true);
    }

    @PostMapping(MARK_MAILS_UNREAD_MAPPING)
    void markMailsUnread(
        @RequestBody List<String> mailIds,
        @CookieValue(CustomFilterHelper.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to mark mails {} as unread.", characterId, mailIds);
        mailStatusUpdaterService.updateReadStatus(mailIds, characterId, false);
    }

    @PutMapping(SEND_MAIL_MAPPING)
    void sendMail(
        @RequestBody @Valid SendMailRequest request,
        @CookieValue(CustomFilterHelper.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("Sending mail...");
        mailSenderService.sendMail(request, characterId);
    }

    @PostMapping(RESTORE_MAILS_MAPPING)
    void restoreMails(
        @RequestBody List<String> mailIds,
        @CookieValue(CustomFilterHelper.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to unarchive mails {}", characterId, mailIds);
        mailStatusUpdaterService.archiveMails(characterId, mailIds, false);
    }
}
