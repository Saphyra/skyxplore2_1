package skyxplore.controller.view;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractViewConverter<S, T> implements ViewConverter<S, T> {
    @Override
    public List<T> convertDomain(List<S> domain) {
        if (domain == null) {
            return null;
        }
        return domain.stream().map(this::convertDomain).collect(Collectors.toList());
    }
}
