package skyxplore.dataaccess.accesstoken.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;

@Entity
@Table(name = "access_token")
@Data
public class AccessTokenEntity {
    @Id
    @Column(name = "access_token_id", length = 100)
    private String accessTokenId;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "last_access", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar lastAccess;
}
