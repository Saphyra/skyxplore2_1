package skyxplore.domain.accesstoken;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "access_token")
@Data
public class AccessTokenEntity {
    @Id
    @Column(name = "access_token_id", length = 50)
    private String accessTokenId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "last_access", nullable = false)
    private Long lastAccess;
}
