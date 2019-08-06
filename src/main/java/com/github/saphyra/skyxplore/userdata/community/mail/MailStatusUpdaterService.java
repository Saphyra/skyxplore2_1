package com.github.saphyra.skyxplore.userdata.community.mail;

import java.util.List;

import javax.transaction.Transactional;

import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.common.exception.InvalidMailAccessException;
import com.github.saphyra.skyxplore.userdata.community.mail.domain.Mail;
import com.github.saphyra.skyxplore.userdata.community.mail.repository.MailDao;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
class MailStatusUpdaterService {
    private final CharacterQueryService characterQueryService;
    private final MailDao mailDao;
    private final MailQueryService mailQueryService;

    @Transactional
    void archiveMails(String characterId, List<String> mailIds, Boolean archiveStatus) {
        SkyXpCharacter character = characterQueryService.findByCharacterId(characterId);
        mailIds.forEach(mailId -> setArchiveStatus(character, mailId, archiveStatus));
    }

    private void setArchiveStatus(SkyXpCharacter character, String mailId, Boolean archiveStatus) {
        Mail mail = mailQueryService.findMailById(mailId);

        if (!mail.getTo().equals(character.getCharacterId())) {
            throw new InvalidMailAccessException(character.getCharacterId() + " cannot change read status of mail " + mailId);
        }

        mail.setArchived(archiveStatus);
        mailDao.save(mail);
    }

    @Transactional
    void updateReadStatus(List<String> mailIds, String characterId, Boolean newStatus) {
        SkyXpCharacter character = characterQueryService.findByCharacterId(characterId);
        mailIds.forEach(mailId -> setMailReadStatus(mailId, character, newStatus));
    }

    private void setMailReadStatus(String mailId, SkyXpCharacter character, Boolean readStatus) {

        Mail mail = mailQueryService.findMailById(mailId);

        if (!mail.getTo().equals(character.getCharacterId())) {
            throw new InvalidMailAccessException(character.getCharacterId() + " cannot change read status of mail " + mailId);
        }

        mail.setRead(readStatus);
        mailDao.save(mail);

    }
}
