package skyxplore.controller.view;

import java.util.List;

public interface ViewConverter<S, T> {
    T convertDomain(S domain);
    List<T> convertDomain(List<S> domain);
}
