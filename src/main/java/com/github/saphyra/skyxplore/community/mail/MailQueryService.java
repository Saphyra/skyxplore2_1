package com.github.saphyra.skyxplore.community.mail;

import java.util.List;

import com.github.saphyra.skyxplore.common.exception.MailNotFoundException;
import com.github.saphyra.skyxplore.community.mail.domain.Mail;
import com.github.saphyra.skyxplore.community.mail.repository.MailDao;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

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
