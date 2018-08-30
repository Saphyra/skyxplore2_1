package skyxplore.service.community;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.community.MarkMailReadRequest;
import skyxplore.dataaccess.db.MailDao;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.community.mail.Mail;
import skyxplore.exception.InvalidMailAccessException;
import skyxplore.service.character.CharacterQueryService;

@Service
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class MailStatusUpdaterService {
    private final CharacterQueryService characterQueryService;
    private final MailDao mailDao;
    private final MailQueryService mailQueryService;

    public void updateReadStatus(MarkMailReadRequest request, String userId, Boolean newStatus) {
        SkyXpCharacter character = characterQueryService.findCharacterByIdAuthorized(request.getCharacterId(), userId);
        request.getMailIds().forEach(mailId ->setMailReadStatus(mailId, character, newStatus));
    }

    private void setMailReadStatus(String mailId, SkyXpCharacter character, Boolean readStatus){
        try{
            Mail mail = mailQueryService.findMailById(mailId);

            if(!mail.getTo().equals(character.getCharacterId())){
                throw new InvalidMailAccessException(character.getCharacterId() + " cannot change read status of mail " + mailId);
            }

            mail.setRead(readStatus);
            mailDao.save(mail);
        } catch (Exception e) {
            log.error("Error updating read status of  mail {} by character.", mailId, character.getCharacterId(), e);
        }
    }
}
