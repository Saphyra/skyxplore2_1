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

@Service
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class MailStatusUpdaterService {
    private final CharacterQueryService characterQueryService;
    private final MailDao mailDao;
    private final MailQueryService mailQueryService;

    public void archiveMails(String characterId, List<String> mailIds, Boolean archiveStatus) {
        SkyXpCharacter character = characterQueryService.findByCharacterId(characterId);
        mailIds.forEach(mailId -> setArchiveStatus(character, mailId, archiveStatus));
    }

    private void setArchiveStatus(SkyXpCharacter character, String mailId, Boolean archiveStatus) {
        try {
            Mail mail = mailQueryService.findMailById(mailId);

            if (!mail.getTo().equals(character.getCharacterId())) {
                throw new InvalidMailAccessException(character.getCharacterId() + " cannot change read status of mail " + mailId);
            }

            mail.setArchived(archiveStatus);
            mailDao.save(mail);
        } catch (Exception e) {
            log.error("Error updating archive status of  mail {} by character.", mailId, character.getCharacterId(), e);
        }
    }

    public void updateReadStatus(List<String> mailIds, String characterId, Boolean newStatus) {
        SkyXpCharacter character = characterQueryService.findByCharacterId(characterId);
        mailIds.forEach(mailId -> setMailReadStatus(mailId, character, newStatus));
    }

    private void setMailReadStatus(String mailId, SkyXpCharacter character, Boolean readStatus) {
        try {
            Mail mail = mailQueryService.findMailById(mailId);

            if (!mail.getTo().equals(character.getCharacterId())) {
                throw new InvalidMailAccessException(character.getCharacterId() + " cannot change read status of mail " + mailId);
            }

            mail.setRead(readStatus);
            mailDao.save(mail);
        } catch (Exception e) {
            log.error("Error updating read status of  mail {} by character.", mailId, character.getCharacterId(), e);
        }
    }
}
