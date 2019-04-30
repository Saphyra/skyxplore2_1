package org.github.saphyra.skyxplore.user.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {
    @NotNull
    @Size(min = 6, max = 30)
    private String newPassword;

    @NotNull
    @Size(min = 1)
    private String oldPassword;
}
