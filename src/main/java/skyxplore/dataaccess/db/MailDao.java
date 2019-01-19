package skyxplore.dataaccess.db;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import org.springframework.stereotype.Component;
import skyxplore.dataaccess.db.repository.MailRepository;
import skyxplore.domain.community.mail.Mail;
import skyxplore.domain.community.mail.MailEntity;

import java.util.List;

@Component
public class MailDao extends AbstractDao<MailEntity, Mail, String, MailRepository> {

    public MailDao(Converter<MailEntity, Mail> converter, MailRepository repository) {
        super(converter, repository);
    }

    public void deleteBothSideDeleted() {
        repository.deleteBothSideDeleted();
    }

    public void deleteByCharacterId(String characterId) {
        repository.deleteByCharacterId(characterId);
    }

    public void deleteExpired(Long expiration) {
        repository.deleteExpired(expiration);
    }

    public List<Mail> getArchivedMails(String characterId) {
        return converter.convertEntity(repository.getArchivedMails(characterId));
    }

    public List<Mail> getMails(String characterId) {
        return converter.convertEntity(repository.getMails(characterId));
    }

    public List<Mail> getSentMails(String characterId) {
        return converter.convertEntity(repository.getSentMails(characterId));
    }

    public List<Mail> getUnreadMails(String characterId) {
        return converter.convertEntity(repository.getUnreadMails(characterId));
    }
}
