package skyxplore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.controller.request.community.SendMailRequest;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import skyxplore.domain.community.mail.Mail;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.service.community.MailDeleteService;
import skyxplore.service.community.MailQueryService;
import skyxplore.service.community.MailSenderService;
import skyxplore.service.community.MailStatusUpdaterService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MailFacade {
    private final CharacterQueryService characterQueryService;
    private final MailDeleteService mailDeleteService;
    private final MailSenderService mailSenderService;
    private final MailStatusUpdaterService mailStatusUpdaterService;
    private final MailQueryService mailQueryService;

    public void archiveMails(String characterId, List<String> mailIds, Boolean archiveStatus) {
        mailStatusUpdaterService.archiveMails(characterId, mailIds, archiveStatus);
    }

    public void deleteMails(String characterId, List<String> mailIds) {
        mailDeleteService.deleteMails(characterId, mailIds);
    }

    public List<SkyXpCharacter> getAddressees(String characterId, String name) {
        return characterQueryService.getCharactersCanBeAddressee(name, characterId);
    }

    public List<Mail> getArchivedMails(String characterId) {
        return mailQueryService.getArchivedMails(characterId);
    }

    public List<Mail> getMails(String characterId) {
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

    public void setMailReadStatus(List<String> mailIds, String characterId, Boolean newStatus) {
        mailStatusUpdaterService.updateReadStatus(mailIds, characterId, newStatus);
    }
}
