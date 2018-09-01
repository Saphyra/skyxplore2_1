package skyxplore.service.community;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.MailDao;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.community.mail.Mail;
import skyxplore.exception.InvalidMailAccessException;
import skyxplore.service.character.CharacterQueryService;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
//TODO unit test
public class MailDeleteService {
    private final CharacterQueryService characterQueryService;
    private final MailDao mailDao;
    private final MailQueryService mailQueryService;

    public void deleteMails(String characterId, List<String> mailIds) {
        SkyXpCharacter character = characterQueryService.findByCharacterId(characterId);
        mailIds.forEach(mailId -> processDeletion(character, mailId));
    }

    private void processDeletion(SkyXpCharacter character, String mailId) {
        try {
            log.info("deleting mail {} by charatcer {}", mailId, character.getCharacterId());
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
