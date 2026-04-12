package product.product.productController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import product.product.dto.ProductRepo;
import product.product.exception.ProductNotFoundException;
import product.product.model.Product;
import product.product.productService.ProductService;
import shiroya.orderEvent.OrderEvent;

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
    public ResponseEntity<Boolean> validateAndReduceProduct(@RequestBody OrderEvent request) {

        try {
            Product product = productRepo.findByProductId(request.getProductId());

            if (product == null) {
                return ResponseEntity.status(404).body(false);
            }

            if (product.getQuantity() < request.getQuantity()) {
                return ResponseEntity.status(409).body(false);
            }

            product.setQuantity(product.getQuantity() - request.getQuantity());
            productRepo.save(product);

            return ResponseEntity.ok(true);
        }
        catch(RuntimeException e){
             throw new ProductNotFoundException("Product Not Found");
        }
    }
}
