package org.github.saphyra.selenium.logic.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeleniumAccount {
    private SeleniumUser user;
    private List<SeleniumCharacter> characters;

    public SeleniumCharacter getCharacter(int index) {
        return characters.get(index);
    }
}
