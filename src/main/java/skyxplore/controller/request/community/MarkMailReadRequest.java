package skyxplore.controller.request.community;

import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class MarkMailReadRequest {
    @NotNull
    private String characterId;

    @NotNull
    private List<String> mailIds;
}