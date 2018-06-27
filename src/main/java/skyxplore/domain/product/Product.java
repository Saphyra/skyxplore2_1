package skyxplore.domain.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String productId;
    private String factoryId;
    private String elementId;
    private Integer amount;
    private Long addedAt;
    private Integer constructionTime;
    private LocalDateTime startTime;
}
