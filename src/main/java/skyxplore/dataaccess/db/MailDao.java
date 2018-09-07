package skyxplore.dataaccess.db;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.MailRepository;
import skyxplore.domain.community.mail.Mail;
import skyxplore.domain.community.mail.MailConverter;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MailDao {
    private final MailConverter mailConverter;
    private final MailRepository mailRepository;

    //TODO unit test
    public void deleteBothSideDeleted() {
        mailRepository.deleteBothSideDeleted();
    }

    //TODO unit test
    public void deleteByCharacterId(String characterId) {
        mailRepository.deleteByCharacterId(characterId);
    }

    //TODO unit test
    public void deleteExpired(Long expiration) {
        mailRepository.deleteExpired(expiration);
    }

    //TODO unit test
    public Mail findById(String mailId) {
        return mailRepository.findById(mailId).map(mailConverter::convertEntity).orElse(null);
    }

    //TODO unit test
    public List<Mail> getArchivedMails(String characterId) {
        return mailConverter.convertEntity(mailRepository.getArchivedMails(characterId));
    }

    //TODO unit test
    public List<Mail> getMails(String characterId) {
        return mailConverter.convertEntity(mailRepository.getMails(characterId));
    }

    //TODO unit test
    public List<Mail> getSentMails(String characterId) {
        return mailConverter.convertEntity(mailRepository.getSentMails(characterId));
    }

    //TODO unit test
    public List<Mail> getUnreadMails(String characterId) {
        return mailConverter.convertEntity(mailRepository.getUnreadMails(characterId));
    }

    //TODO unit test
    public void save(Mail mail) {
        mailRepository.save(mailConverter.convertDomain(mail));
    }
}
