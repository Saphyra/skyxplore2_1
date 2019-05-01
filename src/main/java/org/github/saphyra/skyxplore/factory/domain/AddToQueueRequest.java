package org.github.saphyra.skyxplore.factory.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddToQueueRequest {
    @NotNull
    private String elementId;

    @NotNull
    @Min(1)
    private Integer amount;
}
