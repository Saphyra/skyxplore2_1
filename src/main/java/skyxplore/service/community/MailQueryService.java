package skyxplore.service.community;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import skyxplore.dataaccess.db.MailDao;
import skyxplore.domain.community.mail.Mail;
import skyxplore.exception.MailNotFoundException;
import skyxplore.service.character.CharacterQueryService;

@Service
@RequiredArgsConstructor
public class MailQueryService {
    private final CharacterQueryService characterQueryService;
    private final MailDao mailDao;

    public Mail findMailById(String mailId) {
        Mail mail = mailDao.findById(mailId);
        if (mail == null) {
            throw new MailNotFoundException("Mail not found with id " + mailId);
        }
        return mail;
    }

    public Integer getNumberOfUnreadMails(String characterId, String userId) {
        characterQueryService.findCharacterByIdAuthorized(characterId, userId);
        return mailDao.getUnreadMails(characterId).size();
    }
}
