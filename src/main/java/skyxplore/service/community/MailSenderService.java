package skyxplore.service.community;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import skyxplore.controller.request.community.SendMailRequest;
import skyxplore.dataaccess.db.MailDao;
import skyxplore.domain.character.SkyXpCharacter;
import skyxplore.domain.community.mail.Mail;
import skyxplore.service.character.CharacterQueryService;
import skyxplore.util.DateTimeUtil;
import skyxplore.util.IdGenerator;

@RequiredArgsConstructor
@Service
//TODO unit test
public class MailSenderService {
    private final CharacterQueryService characterQueryService;
    private final DateTimeUtil dateTimeUtil;
    private final IdGenerator idGenerator;
    private final MailDao mailDao;

    //TODO check user block status
    public void sendMail(SendMailRequest request, String characterId) {
        characterQueryService.findByCharacterId(request.getAddresseeId());
        Mail mail = createMail(request, characterId);
        mailDao.save(mail);
    }

    private Mail createMail(SendMailRequest request, String characterId) {
        Mail mail = new Mail();
        mail.setMailId(idGenerator.getRandomId());
        mail.setFrom(characterId);
        mail.setTo(request.getAddresseeId());
        mail.setSubject(request.getSubject());
        mail.setMessage(request.getMessage());
        mail.setRead(false);
        mail.setSendTime(dateTimeUtil.now());
        mail.setArchived(false);
        mail.setDeletedBySender(false);
        mail.setDeletedByAddressee(false);
        return mail;
    }
}
