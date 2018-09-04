package skyxplore.domain.community.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "mail")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailEntity {
    @Id
    @Column(name = "mail_id")
    private String mailId;

    @Column(name = "mail_from")
    private String from;

    @Column(name = "mail_to")
    private String to;

    @Column(name = "subject")
    @Type(type = "text")
    private String subject;

    @Column(name = "message")
    @Type(type = "text")
    private String message;

    @Column(name = "is_read")
    private Boolean read;

    @Column(name = "send_time")
    private Long sendTime;

    @Column(name = "is_archived")
    private Boolean archived;

    @Column(name = "is_deleted_by_sender")
    private Boolean deletedBySender;

    @Column(name = "is_deleted_by_addressee")
    private Boolean deletedByAddressee;
}
