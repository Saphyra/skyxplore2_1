package skyxplore.service.community;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import skyxplore.dataaccess.db.MailDao;
import skyxplore.domain.community.mail.Mail;

@Service
@RequiredArgsConstructor
public class MailQueryService {
    private MailDao mailDao;

    public Mail findMailById(String mailId) {
        return mailDao.findById(mailId);
    }
}
