package skyxplore.controller.view.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import skyxplore.domain.product.Product;
import skyxplore.controller.view.AbstractViewConverter;
import skyxplore.util.DateTimeUtil;

@Component
@RequiredArgsConstructor
public class ProductViewConverter extends AbstractViewConverter<Product, ProductView> {
    private final DateTimeUtil dateTimeUtil;

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
        view.setStartTime(dateTimeUtil.convertDomain(domain.getStartTime()));
        view.setEndTime((dateTimeUtil.convertDomain(domain.getEndTime())));
        return view;
    }
}
