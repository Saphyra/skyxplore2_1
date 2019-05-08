package com.github.saphyra.skyxplore.user.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeUserNameRequest {
    @NotNull
    @Size(min = 3, max = 30)
    private String newUserName;

    @NotNull
    @Size(min = 1)
    private String password;
}
