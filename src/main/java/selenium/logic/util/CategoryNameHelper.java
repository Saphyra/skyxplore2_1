package selenium.logic.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import selenium.logic.domain.Category;
import selenium.logic.domain.MessageCodes;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;

@Slf4j
public class CategoryNameHelper {
    private final Map<String, String> categories;

    public CategoryNameHelper(ObjectMapper objectMapper, String locale) {
        URL messageCodes = getClass().getClassLoader().getResource("public/i18n/" + locale + "/categories.json");

        if (isNull(messageCodes)) {
            log.info("Localization not found for locale {}. Using default locale...", locale);
            messageCodes = getClass().getClassLoader().getResource("public/i18n/hu/categories.json");
        }


        TypeReference<HashMap<String, String>> typeRef = new TypeReference<HashMap<String, String>>() {
        };

        try {
            this.categories = objectMapper.readValue(messageCodes, MessageCodes.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCategoryName(Category category) {
        return Optional.ofNullable(categories.get(category.getCategoryId()))
            .orElseThrow(() -> new RuntimeException("No categoryName found with categoryId " + category));
    }
}
