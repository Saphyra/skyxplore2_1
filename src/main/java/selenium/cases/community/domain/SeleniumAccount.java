package selenium.cases.community.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import selenium.domain.SeleniumCharacter;
import selenium.domain.SeleniumUser;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeleniumAccount {
    private SeleniumUser user;
    private SeleniumCharacter character1;
    private SeleniumCharacter character2;
}
