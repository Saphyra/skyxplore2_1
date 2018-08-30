package skyxplore.service.community;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.controller.request.community.DeleteMailRequest;
import skyxplore.dataaccess.db.MailDao;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.community.mail.Mail;
import skyxplore.exception.InvalidMailAccessException;
import skyxplore.exception.MailNotFoundException;
import skyxplore.service.character.CharacterQueryService;

@RequiredArgsConstructor
@Service
@Slf4j
//TODO unit test
public class MailDeleteService {
    private final CharacterQueryService characterQueryService;
    private final MailDao mailDao;
    private final MailQueryService mailQueryService;

    public void deleteMails(DeleteMailRequest request, String userId) {
        SkyXpCharacter character = characterQueryService.findCharacterByIdAuthorized(request.getCharacterId(), userId);
        request.getMailIds().forEach(mailId -> processDeletion(character, mailId));
    }

    private void processDeletion(SkyXpCharacter character, String mailId) {
        try {
            Mail mail = mailQueryService.findMailById(mailId);

            if (mail.getTo().equals(character.getCharacterId())) {
                mail.setDeletedByAddressee(true);
            } else if (mail.getFrom().equals(character.getCharacterId())) {
                mail.setDeletedBySender(true);
            }else{
                throw new InvalidMailAccessException(character.getCharacterId() + " has no access to mail " + mailId);
            }

            mailDao.save(mail);
        } catch (Exception e) {
            log.error("Error deleting mail {} by character.", mailId, character.getCharacterId(), e);
        }
    }
}
