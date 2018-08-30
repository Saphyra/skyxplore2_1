package skyxplore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import skyxplore.controller.request.community.SendMailRequest;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.service.community.MailService;

import java.util.List;

@Component
@RequiredArgsConstructor
//TODO unit test
public class MailFacade {
    private final CharacterQueryService characterQueryService;
    private final MailService mailService;

    public List<SkyXpCharacter> getAddressees(String characterId, String userId, String name) {
        return characterQueryService.getCharactersCanBeAddressee(name, characterId, userId);
    }

    public void sendMail(SendMailRequest request, String userId) {
        mailService.sendMail(request, userId);
    }
}
