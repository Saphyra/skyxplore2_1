package skyxplore.service.community;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import skyxplore.dataaccess.db.MailDao;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import skyxplore.domain.community.mail.Mail;
import org.github.saphyra.skyxplore.common.exception.InvalidMailAccessException;
import org.github.saphyra.skyxplore.character.CharacterQueryService;

@RequiredArgsConstructor
@Service
@Slf4j
public class MailDeleteService {
    private final CharacterQueryService characterQueryService;
    private final MailDao mailDao;
    private final MailQueryService mailQueryService;

    @Transactional
    public void deleteMails(String characterId, List<String> mailIds) {
        SkyXpCharacter character = characterQueryService.findByCharacterId(characterId);
        mailIds.forEach(mailId -> processDeletion(character, mailId));
    }

    private void processDeletion(SkyXpCharacter character, String mailId) {
        log.info("deleting mail {} by charatcer {}", mailId, character.getCharacterId());
        Mail mail = mailQueryService.findMailById(mailId);

        if (mail.getTo().equals(character.getCharacterId())) {
            mail.setDeletedByAddressee(true);
        } else if (mail.getFrom().equals(character.getCharacterId())) {
            mail.setDeletedBySender(true);
        } else {
            throw new InvalidMailAccessException(character.getCharacterId() + " has no access to mail " + mailId);
        }

        mailDao.save(mail);
    }
}
