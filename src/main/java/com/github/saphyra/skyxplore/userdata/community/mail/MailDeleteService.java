package com.github.saphyra.skyxplore.userdata.community.mail;

import java.util.List;

import javax.transaction.Transactional;

import com.github.saphyra.skyxplore.userdata.character.CharacterQueryService;
import com.github.saphyra.skyxplore.userdata.character.domain.SkyXpCharacter;
import com.github.saphyra.skyxplore.userdata.community.mail.domain.Mail;
import com.github.saphyra.skyxplore.userdata.community.mail.repository.MailDao;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
