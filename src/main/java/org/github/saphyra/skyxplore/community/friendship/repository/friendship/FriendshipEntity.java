package org.github.saphyra.skyxplore.community.friendship.repository.friendship;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "friendship")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class FriendshipEntity {
    @Id
    @Column(name = "friendship_id", length = 50)
    private String friendshipId;

    @Column(name = "character_id", length = 50)
    private String characterId;

    @Column(name = "friend_id", length = 50)
    private String friendId;
}
