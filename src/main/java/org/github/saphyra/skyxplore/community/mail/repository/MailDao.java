package org.github.saphyra.skyxplore.community.mail.repository;

import java.util.List;

import org.github.saphyra.skyxplore.community.mail.domain.Mail;
import org.springframework.stereotype.Component;

import com.github.saphyra.dao.AbstractDao;

@Component
public class MailDao extends AbstractDao<MailEntity, Mail, String, MailRepository> {

    public MailDao(MailConverter converter, MailRepository repository) {
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
