package skyxplore.domain.materials;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.domain.ConverterBase;

import java.io.IOException;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
//TODO unit test
public class MaterialsConverter extends ConverterBase<String, Materials> {
    private final ObjectMapper objectMapper;

    @Override
    public Materials convertEntity(String entity) {
        if(entity == null){
            return null;
        }
        try {
            return new Materials(objectMapper.readValue(entity, HashMap.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertDomain(Materials domain) {
        if(domain == null){
            return null;
        }
        try {
            return objectMapper.writeValueAsString(domain);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
