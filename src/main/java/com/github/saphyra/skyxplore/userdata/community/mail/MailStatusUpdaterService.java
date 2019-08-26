package com.github.saphyra.skyxplore.userdata.community.mail;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.community.mail.domain.Mail;
import com.github.saphyra.skyxplore.userdata.community.mail.repository.MailDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
class MailStatusUpdaterService {
    private final CharacterQueryService characterQueryService;
    private final MailDao mailDao;
    private final MailQueryService mailQueryService;

    @Transactional
    void archiveMails(String characterId, List<String> mailIds, Boolean archiveStatus) {
        SkyXpCharacter character = characterQueryService.findByCharacterIdValidated(characterId);
        mailIds.forEach(mailId -> setArchiveStatus(character, mailId, archiveStatus));
    }

    private void setArchiveStatus(SkyXpCharacter character, String mailId, Boolean archiveStatus) {
        Mail mail = mailQueryService.findMailById(mailId);

        if (!mail.getTo().equals(character.getCharacterId())) {
            throw ExceptionFactory.invalidMailAccess(character.getCharacterId(), mailId);
        }

        mail.setArchived(archiveStatus);
        mailDao.save(mail);
    }

    @Transactional
    void updateReadStatus(List<String> mailIds, String characterId, Boolean newStatus) {
        SkyXpCharacter character = characterQueryService.findByCharacterIdValidated(characterId);
        mailIds.forEach(mailId -> setMailReadStatus(mailId, character, newStatus));
    }

    private void setMailReadStatus(String mailId, SkyXpCharacter character, Boolean readStatus) {
        Mail mail = mailQueryService.findMailById(mailId);

        if (!mail.getTo().equals(character.getCharacterId())) {
            throw ExceptionFactory.invalidMailAccess(character.getCharacterId(), mailId);
        }

        mail.setRead(readStatus);
        mailDao.save(mail);
    }
}
