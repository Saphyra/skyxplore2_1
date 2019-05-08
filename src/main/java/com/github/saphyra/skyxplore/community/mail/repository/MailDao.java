package com.github.saphyra.skyxplore.community.mail.repository;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.community.mail.domain.Mail;
import com.github.saphyra.skyxplore.event.CharacterDeletedEvent;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MailDao extends AbstractDao<MailEntity, Mail, String, MailRepository> {

    public MailDao(MailConverter converter, MailRepository repository) {
        super(converter, repository);
    }

    public void deleteBothSideDeleted() {
        repository.deleteBothSideDeleted();
    }

    @EventListener
    void characterDeletedEventListener(CharacterDeletedEvent event) {
        repository.deleteByCharacterId(event.getCharacterId());
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
