package com.github.saphyra.skyxplore.game.game.request;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRoomRequest {
    @NotNull
    private String roomName;

    private String initialMember;
}
