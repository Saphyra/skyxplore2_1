package skyxplore.service.community;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.github.saphyra.skyxplore.community.mail.repository.MailDao;
import org.github.saphyra.skyxplore.community.mail.domain.Mail;
import org.github.saphyra.skyxplore.common.exception.MailNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailQueryService {
    private final MailDao mailDao;

    public Mail findMailById(String mailId) {
        return mailDao.findById(mailId)
            .orElseThrow(() -> new MailNotFoundException("Mail not found with id " + mailId));
    }

    public List<Mail> getArchivedMails(String characterId) {
        return mailDao.getArchivedMails(characterId);
    }

    public List<Mail> getMails(String characterId) {
        return mailDao.getMails(characterId);
    }

    public Integer getNumberOfUnreadMails(String characterId) {
        return mailDao.getUnreadMails(characterId).size();
    }

    public List<Mail> getSentMails(String characterId) {
        return mailDao.getSentMails(characterId);
    }
}
