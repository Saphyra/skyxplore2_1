package com.github.saphyra.skyxplore.userdata.community.mail;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.userdata.community.mail.domain.Mail;
import com.github.saphyra.skyxplore.userdata.community.mail.repository.MailDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailQueryService {
    private final MailDao mailDao;

    Mail findMailById(String mailId) {
        return mailDao.findById(mailId)
            .orElseThrow(() -> ExceptionFactory.mailNotFound(mailId));
    }

    public List<Mail> getArchivedMails(String characterId) {
        return mailDao.getArchivedMails(characterId);
    }

    List<Mail> getMails(String characterId) {
        return mailDao.getMails(characterId);
    }

    public Integer getNumberOfUnreadMails(String characterId) {
        return mailDao.getUnreadMails(characterId).size();
    }

    public List<Mail> getSentMails(String characterId) {
        return mailDao.getSentMails(characterId);
    }
}
