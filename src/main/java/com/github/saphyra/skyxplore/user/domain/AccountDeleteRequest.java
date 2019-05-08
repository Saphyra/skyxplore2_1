package com.github.saphyra.skyxplore.user.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDeleteRequest {
    @NotNull
    @Size(min = 1)
    private String password;
}
