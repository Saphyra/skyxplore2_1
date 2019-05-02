package org.github.saphyra.skyxplore.community.mail;

import java.util.List;

import javax.transaction.Transactional;

import org.github.saphyra.skyxplore.character.CharacterQueryService;
import org.github.saphyra.skyxplore.character.domain.SkyXpCharacter;
import org.github.saphyra.skyxplore.common.exception.InvalidMailAccessException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.github.saphyra.skyxplore.community.mail.repository.MailDao;
import org.github.saphyra.skyxplore.community.mail.domain.Mail;
import skyxplore.service.community.MailQueryService;

@RequiredArgsConstructor
@Service
@Slf4j
class MailDeleteService {
    private final CharacterQueryService characterQueryService;
    private final MailDao mailDao;
    private final MailQueryService mailQueryService;

    @Transactional
    void deleteMails(String characterId, List<String> mailIds) {
        SkyXpCharacter character = characterQueryService.findByCharacterId(characterId);
        mailIds.forEach(mailId -> processDeletion(character, mailId));
    }

    private void processDeletion(SkyXpCharacter character, String mailId) {
        log.info("deleting mail {} by charatcer {}", mailId, character.getCharacterId());
        Mail mail = mailQueryService.findMailById(mailId);

        if (mail.getTo().equals(character.getCharacterId())) {
            mail.setDeletedByAddressee(true);
        } else if (mail.getFrom().equals(character.getCharacterId())) {
            mail.setDeletedBySender(true);
        } else {
            throw new InvalidMailAccessException(character.getCharacterId() + " has no access to mail " + mailId);
        }

        mailDao.save(mail);
    }
}
