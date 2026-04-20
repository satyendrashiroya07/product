package product.product.productController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import product.product.dto.ProductRepo;
import product.product.exception.ProductNotFoundException;
import product.product.model.Product;
import product.product.productService.ProductService;
import shiroya.orderEvent.OrderEvent;
import shiroya.orderEvent.OrderRequest;
import shiroya.productEvent.ProductResponse;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ProductRepo productRepo;

    public ProductController(ProductService productService,  ProductRepo productRepo)
    {
        this.productService = productService;
        this.productRepo = productRepo;
    }

    @GetMapping("/{productId}")
    public Product getProducts(@PathVariable String productId)
    {
        return productService.getProductsForProductId(productId);
    }

    @PostMapping()
    public Product create(@RequestBody Product product)
    {
        return productService.createProduct(product);
    }

    @PostMapping("/validateAndReduce")
    public ProductResponse validateAndReduceProduct(@RequestBody OrderRequest request) {

        try {
            Product product = productRepo.findByProductId(request.getProductId());

            ProductResponse response = new ProductResponse();
            response.setProductId(product.getProductId());
            response.setName(product.getName());
            response.setQuantity(product.getQuantity());
            response.setPrice(product.getPrice());

            return response;
        }
        catch(RuntimeException e){
             throw new ProductNotFoundException("Product Not Found");
        }
    }
}
