package skyxplore.domain.product;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Product {
    private String productId;
    private String factoryId;
    private String elementId;
    private Long order;
    private Integer constructionTime;
    private LocalDateTime startTime;
}
