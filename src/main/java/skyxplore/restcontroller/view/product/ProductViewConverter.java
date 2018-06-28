package skyxplore.restcontroller.view.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.domain.product.Product;
import skyxplore.restcontroller.view.AbstractViewConverter;
import skyxplore.util.DateTimeConverter;

@Component
@RequiredArgsConstructor
public class ProductViewConverter extends AbstractViewConverter<Product, ProductView> {
    private final DateTimeConverter dateTimeConverter;

    @Override
    public ProductView convertDomain(Product domain) {
        if (domain == null) {
            return null;
        }
        ProductView view = new ProductView();
        view.setProductId(domain.getProductId());
        view.setFactoryId(domain.getFactoryId());
        view.setElementId(domain.getElementId());
        view.setAmount(domain.getAmount());
        view.setConstructionTime(domain.getConstructionTime());
        view.setAddedAt(domain.getAddedAt());
        view.setStartTime(dateTimeConverter.convertDomain(domain.getStartTime()));
        return view;
    }
}
