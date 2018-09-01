package skyxplore.controller.view.community.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailView {
    private String mailId;
    private String from;
    private String fromName;
    private String subject;
    private String message;
    private Boolean read;
    private Long sendTime;
}
