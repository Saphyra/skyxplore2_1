package com.github.saphyra.skyxplore.userdata.user.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeEmailRequest {
    @Email
    @NotNull
    private String newEmail;

    @NotNull
    @Size(min = 1)
    private String password;
}
