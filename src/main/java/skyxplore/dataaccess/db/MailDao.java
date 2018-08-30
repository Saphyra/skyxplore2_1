package skyxplore.dataaccess.db;

import java.util.Collection;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.MailRepository;
import skyxplore.domain.community.mail.Mail;
import skyxplore.domain.community.mail.MailConverter;

@RequiredArgsConstructor
@Component
//TODO unit test
public class MailDao {
    private final MailConverter mailConverter;
    private final MailRepository mailRepository;

    public void save(Mail mail){
        mailRepository.save(mailConverter.convertDomain(mail));
    }

    public Mail findById(String mailId) {
        return mailRepository.findById(mailId).map(mailConverter::convertEntity).orElse(null);
    }

    public List<Mail> getUnreadMails(String characterId) {
        return mailConverter.convertEntity(mailRepository.getUnreadMails(characterId));
    }
}
