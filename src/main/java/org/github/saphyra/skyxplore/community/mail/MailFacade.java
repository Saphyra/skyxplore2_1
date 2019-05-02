package org.github.saphyra.skyxplore.community.mail;

import java.util.List;

import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.community.mail.domain.SendMailRequest;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import skyxplore.domain.community.mail.Mail;
import skyxplore.service.community.MailQueryService;
import skyxplore.service.community.MailSenderService;
import skyxplore.service.community.MailStatusUpdaterService;

@Component
@RequiredArgsConstructor
public class MailFacade {
    private final CharacterQueryService characterQueryService;
    private final MailDeleteService mailDeleteService;
    private final MailSenderService mailSenderService;
    private final MailStatusUpdaterService mailStatusUpdaterService;
    private final MailQueryService mailQueryService;

    void archiveMails(String characterId, List<String> mailIds, Boolean archiveStatus) {
        mailStatusUpdaterService.archiveMails(characterId, mailIds, archiveStatus);
    }

    void deleteMails(String characterId, List<String> mailIds) {
        mailDeleteService.deleteMails(characterId, mailIds);
    }

    List<SkyXpCharacter> getAddressees(String characterId, String name) {
        return characterQueryService.getCharactersCanBeAddressee(name, characterId);
    }

    public List<Mail> getArchivedMails(String characterId) {
        return mailQueryService.getArchivedMails(characterId);
    }

    List<Mail> getMails(String characterId) {
        return mailQueryService.getMails(characterId);
    }

    public Integer getNumberOfUnreadMails(String characterId) {
        return mailQueryService.getNumberOfUnreadMails(characterId);
    }

    public List<Mail> getSentMails(String characterId) {
        return mailQueryService.getSentMails(characterId);
    }

    public void sendMail(SendMailRequest request, String characterId) {
        mailSenderService.sendMail(request, characterId);
    }

    void setMailReadStatus(List<String> mailIds, String characterId, Boolean newStatus) {
        mailStatusUpdaterService.updateReadStatus(mailIds, characterId, newStatus);
    }
}
