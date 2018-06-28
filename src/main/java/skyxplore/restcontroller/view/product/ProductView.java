package skyxplore.restcontroller.view.product;

import lombok.Data;

@Data
public class ProductView {
    private String productId;
    private String factoryId;
    private String elementId;
    private Integer amount;
    private Long addedAt;
    private Integer constructionTime;
    private Long startTime;
}
