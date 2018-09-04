package skyxplore.domain.credentials;

import org.springframework.stereotype.Component;

import skyxplore.domain.ConverterBase;

@Component
//TODO unit test
public class CredentialsConverter extends ConverterBase<CredentialsEntity, Credentials> {
    @Override
    public Credentials convertEntity(CredentialsEntity entity) {
        if(entity == null){
            return null;
        }
        return new Credentials(entity.getUserId(), entity.getUserName(), entity.getPassword());
    }

    @Override
    public CredentialsEntity convertDomain(Credentials domain) {
        if(domain == null){
            return null;
        }
        return new CredentialsEntity(domain.getUserId(), domain.getUserName(), domain.getPassword());
    }
}
