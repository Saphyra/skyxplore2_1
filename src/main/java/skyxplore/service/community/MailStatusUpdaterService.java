package skyxplore.service.community;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.db.MailDao;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import skyxplore.domain.community.mail.Mail;
import skyxplore.exception.InvalidMailAccessException;
import org.github.saphyra.skyxplore.character.CharacterQueryService;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailStatusUpdaterService {
    private final CharacterQueryService characterQueryService;
    private final MailDao mailDao;
    private final MailQueryService mailQueryService;

    @Transactional
    public void archiveMails(String characterId, List<String> mailIds, Boolean archiveStatus) {
        SkyXpCharacter character = characterQueryService.findByCharacterId(characterId);
        mailIds.forEach(mailId -> setArchiveStatus(character, mailId, archiveStatus));
    }

    private void setArchiveStatus(SkyXpCharacter character, String mailId, Boolean archiveStatus) {
        Mail mail = mailQueryService.findMailById(mailId);

        if (!mail.getTo().equals(character.getCharacterId())) {
            throw new InvalidMailAccessException(character.getCharacterId() + " cannot change read status of mail " + mailId);
        }

        mail.setArchived(archiveStatus);
        mailDao.save(mail);
    }

    @Transactional
    public void updateReadStatus(List<String> mailIds, String characterId, Boolean newStatus) {
        SkyXpCharacter character = characterQueryService.findByCharacterId(characterId);
        mailIds.forEach(mailId -> setMailReadStatus(mailId, character, newStatus));
    }

    private void setMailReadStatus(String mailId, SkyXpCharacter character, Boolean readStatus) {

        Mail mail = mailQueryService.findMailById(mailId);

        if (!mail.getTo().equals(character.getCharacterId())) {
            throw new InvalidMailAccessException(character.getCharacterId() + " cannot change read status of mail " + mailId);
        }

        mail.setRead(readStatus);
        mailDao.save(mail);

    }
}
