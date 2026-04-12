package product.product.productService;

import org.springframework.stereotype.Service;
import product.product.dto.ProductRepo;
import product.product.model.Product;

@Service
public class ProductService {

    private ProductRepo productRepo;

    public ProductService(ProductRepo productRepo)
    {
        this.productRepo = productRepo;
    }

    public Product getProductsForProductId(String productId)
    {
        return productRepo.findByProductId(productId);
    }

    public Product createProduct(Product product)
    {
        return productRepo.save(product);
    }

}
