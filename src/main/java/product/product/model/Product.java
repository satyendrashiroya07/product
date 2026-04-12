package product.product.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "product")
public class Product {

    // Getters & Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", unique = true)
    private String productId;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 100)
    private String description;

    @Column(precision = 8, scale = 3, nullable = false)
    private BigDecimal price;

    @Column(precision = 8, scale = 3, nullable = false)
    private Integer quantity;

    @Column(length = 200, nullable = false)
    private String email;

    public Product() {}

    public Product(String productId, String name, String description, BigDecimal price, Integer quantity, String email) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.email = email;
    }

}

