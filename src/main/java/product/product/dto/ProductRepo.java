package product.product.dto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import product.product.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long>{

    Product findByProductId(String productId);
}
