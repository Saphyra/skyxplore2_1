package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.MailRepository;
import skyxplore.domain.community.mail.Mail;
import skyxplore.domain.community.mail.MailConverter;

import java.util.List;

@RequiredArgsConstructor
@Component
//TODO unit test
public class MailDao {
    private final MailConverter mailConverter;
    private final MailRepository mailRepository;

    public void save(Mail mail) {
        mailRepository.save(mailConverter.convertDomain(mail));
    }

    public Mail findById(String mailId) {
        return mailRepository.findById(mailId).map(mailConverter::convertEntity).orElse(null);
    }

    public List<Mail> getArchivedMails(String characterId) {
        return mailConverter.convertEntity(mailRepository.getArchivedMails(characterId));
    }

    public List<Mail> getMails(String characterId) {
        return mailConverter.convertEntity(mailRepository.getMails(characterId));
    }

    public List<Mail> getSentMails(String characterId) {
        return mailConverter.convertEntity(mailRepository.getSentMails(characterId));
    }

    public List<Mail> getUnreadMails(String characterId) {
        return mailConverter.convertEntity(mailRepository.getUnreadMails(characterId));
    }
}
