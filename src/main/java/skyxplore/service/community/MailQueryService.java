package skyxplore.service.community;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import skyxplore.dataaccess.db.MailDao;
import skyxplore.domain.community.mail.Mail;
import skyxplore.exception.MailNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
//TODO unit test
public class MailQueryService {
    private final MailDao mailDao;

    public Mail findMailById(String mailId) {
        Mail mail = mailDao.findById(mailId);
        if (mail == null) {
            throw new MailNotFoundException("Mail not found with id " + mailId);
        }
        return mail;
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
