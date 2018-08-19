package skyxplore.domain.community.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mail {
    private String mailId;
    private String from;
    private String to;
    private String message;
    private Boolean read;
    private LocalDateTime sendTime;
    private Boolean archived;
    private Boolean deletedBySender;
    private Boolean deletedByAddressee;
}
