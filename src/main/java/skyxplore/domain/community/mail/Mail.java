package skyxplore.domain.community.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mail {
    private String mailId;
    private String from;
    private String to;
    private String subject;
    private String message;
    private Boolean read;
    private OffsetDateTime sendTime;
    private Boolean archived;
    private Boolean deletedBySender;
    private Boolean deletedByAddressee;
}
