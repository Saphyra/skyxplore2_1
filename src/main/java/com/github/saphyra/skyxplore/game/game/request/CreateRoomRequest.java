package com.github.saphyra.skyxplore.game.game.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateRoomRequest {
    @NotNull
    @Size(min = 1, max = 16)
    private String roomName;

    private String initialMember;
}
