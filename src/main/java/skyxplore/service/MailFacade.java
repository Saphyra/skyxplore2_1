package skyxplore.service;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import skyxplore.controller.request.community.DeleteMailRequest;
import skyxplore.controller.request.community.MarkMailReadRequest;
import skyxplore.controller.request.community.SendMailRequest;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.service.community.MailDeleteService;
import skyxplore.service.community.MailQueryService;
import skyxplore.service.community.MailSenderService;
import skyxplore.service.community.MailStatusUpdaterService;

@Component
@RequiredArgsConstructor
//TODO unit test
public class MailFacade {
    private final CharacterQueryService characterQueryService;
    private final MailDeleteService mailDeleteService;
    private final MailSenderService mailSenderService;
    private final MailStatusUpdaterService mailStatusUpdaterService;
    private final MailQueryService mailQueryService;

    public void deleteMails(DeleteMailRequest request, String userId) {
        mailDeleteService.deleteMails(request, userId);
    }

    public List<SkyXpCharacter> getAddressees(String characterId, String userId, String name) {
        return characterQueryService.getCharactersCanBeAddressee(name, characterId, userId);
    }

    public Integer getNumberOfUnreadMails(String characterId) {
        return mailQueryService.getNumberOfUnreadMails(characterId);
    }

    public void sendMail(SendMailRequest request, String userId) {
        mailSenderService.sendMail(request, userId);
    }

    public void setMailReadStatus(MarkMailReadRequest request, String userId, Boolean newStatus) {
        mailStatusUpdaterService.updateReadStatus(request, userId, newStatus);
    }
}
