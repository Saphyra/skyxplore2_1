package selenium.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import selenium.domain.SeleniumCharacter;
import selenium.domain.SeleniumUser;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeleniumAccount {
    private SeleniumUser user;
    private SeleniumCharacter character1;
    private SeleniumCharacter character2;

    public List<SeleniumCharacter> getCharacters() {
        return Arrays.asList(character1, character2);
    }
}
