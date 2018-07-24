package skyxplore.controller.view.product;

import java.util.ArrayList;
import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductViewList extends ArrayList<ProductView> {
    public ProductViewList(List<ProductView> elements){
        super(elements);
    }
}
