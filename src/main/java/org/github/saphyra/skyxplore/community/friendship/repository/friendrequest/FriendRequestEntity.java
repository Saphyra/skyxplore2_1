package org.github.saphyra.skyxplore.community.friendship.repository.friendrequest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Table(name = "friend_request")
@Entity
@AllArgsConstructor
@NoArgsConstructor
 class FriendRequestEntity {
    @Id
    @Column(name = "friend_request_id", length = 50)
    private String friendRequestId;

    @Column(name = "character_id", length = 50)
    private String characterId;

    @Column(name = "friend_id", length = 50)
    private String friendId;
}
