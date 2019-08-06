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
public class UserRegistrationRequest {
    public static final int USER_NAME_MAX_LENGTH = 30;
    public static final int USER_NAME_MIN_LENGTH = 3;
    public static final int PASSWORD_MAX_LENGTH = 30;
    public static final int PASSWORD_MIN_LENGTH = 6;

    @NotNull
    @Size(min = USER_NAME_MIN_LENGTH, max = USER_NAME_MAX_LENGTH)
    private String username;

    @NotNull
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    @Email
    @NotNull
    @Size(min = 1)
    private String email;

    @Override
    public String toString() {
        return "Username: " + username + ", Email: " + email;
    }
}
