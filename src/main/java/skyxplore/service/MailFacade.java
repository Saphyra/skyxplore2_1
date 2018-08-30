package skyxplore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import skyxplore.controller.request.community.DeleteMailRequest;
import skyxplore.controller.request.community.SendMailRequest;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.service.community.MailDeleteService;
import skyxplore.service.community.MailSenderService;

import java.util.List;

@Component
@RequiredArgsConstructor
//TODO unit test
public class MailFacade {
    private final CharacterQueryService characterQueryService;
    private final MailDeleteService mailDeleteService;
    private final MailSenderService mailSenderService;

    public void deleteMails(DeleteMailRequest request, String userId) {
        mailDeleteService.deleteMails(request, userId);
    }

    public List<SkyXpCharacter> getAddressees(String characterId, String userId, String name) {
        return characterQueryService.getCharactersCanBeAddressee(name, characterId, userId);
    }

    public void sendMail(SendMailRequest request, String userId) {
        mailSenderService.sendMail(request, userId);
    }
}
