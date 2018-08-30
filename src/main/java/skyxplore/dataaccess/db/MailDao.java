package skyxplore.dataaccess.db;

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
}
